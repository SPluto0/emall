package cn.e3mall.cart.service.Impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 *
 * @Date 2019/8/28 12:08
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    @Transactional
    public E3Result addCart(long userId, long itemId, int num) {


        //向redis中添加购物车
        //数据类型是hash key:用户id  field:商品id  value:商品信息
        //判断商品是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在数量相加
        if (hexists) {
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            //把json转换成TbItem
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum() + num);
            //写回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //判断商品在数据库表中是否存在
        if (item == null) {
            return E3Result.build(400, "商品已经下架");
        }

        //设置购物车数量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)) {
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        //返回成功
        return E3Result.ok();
    }

    //合并购物车
    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车
        //判断购物车是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品

        //和addCart逻辑是一样就循环添加就ok了
        for (TbItem tbItem : itemList) {
            addCart(userId, tbItem.getId(), tbItem.getNum());
        }

        //返回成功
        return E3Result.ok();
    }

    //取购物车列表
    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户Id查询购物车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string : jsonList) {
            //创建一个TbItem对象
            TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);

           /* //判断商品是否存在数据库     并且查看库存是否足够
            TbItem tbItem = itemMapper.selectByPrimaryKey(item.getId());
            if (tbItem == null) {
                return itemList ;
            }
            if (tbItem.getNum() - item.getNum() < 0) {
                return itemList;
            }*/
            //
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }

    //取购物车列表
    @Override
    public List<TbItem> getCartLists(long userId) {
        //根据用户Id查询购物车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string : jsonList) {
            //创建一个TbItem对象
            TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);

            //判断商品是否存在数据库     并且查看库存是否足够
            TbItem tbItem = itemMapper.selectByPrimaryKey(item.getId());
            if (tbItem == null) {
                return null;
            }
            if (tbItem.getNum() - item.getNum() < 0) {
                return null;
            }
            //
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }
    //更新购物车数量
    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
        //从redis中取商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //更新商品信息
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写入redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    //删除购物车
    @Override
    public E3Result deleteCartItem(long userId, long itemId) {
        //删除购物车商品
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }


    @Override
    public E3Result clearCartItem(long userId) {
        //删除购物车信息
        jedisClient.del(REDIS_CART_PRE + ":" + userId);
        return E3Result.ok();
    }
}
