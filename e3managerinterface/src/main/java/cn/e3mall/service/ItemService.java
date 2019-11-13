package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
    //用来查询单个信息(测试方法)
    TbItem getItemById(Long itemId);
    //分页查询 全部商品信息
    EasyUIDataGridResult getItemList(int page,int rows);
    //后台进行商品信息的添加
    E3Result addItem(TbItem item,String desc);
    //根据id删除商品信息
    E3Result delItem(long ids);
    //根据id修改商品信息
    E3Result updTtem(long ids);
    //根据id修改商品的状态为上架
    E3Result upStart(long ids);
    //根据id修改商品的状态为下架
    E3Result downStart(long ids);

    TbItemDesc getItemDestById(long itemId);

    E3Result updItem(TbItem tbItem);
}
