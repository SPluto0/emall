package cn.e3mall.cart.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

public interface CartService {
    E3Result addCart(long userId,long itemId,int num);

    //合并购物车
    E3Result mergeCart(long userId, List<TbItem> itemList);

    //取购物车列表
    List<TbItem> getCartList(long userId);
    List<TbItem> getCartLists(long userId);

    E3Result updateCartNum(long userId,long itemId,int num);

    E3Result deleteCartItem(long userId,long itemId);

    E3Result clearCartItem(long userId);
}
