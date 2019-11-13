package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 商品管理Controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    /**
     * 商品添加功能
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item,String desc){
        E3Result result = itemService.addItem(item,desc);
        return result;
    }

    /**
     * 商品删除功能
     */
    @RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result delItem(long ids){
        E3Result result = itemService.delItem(ids);
        return result;
    }

    /**
     * 商品下架功能
     */
    @RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
    @ResponseBody
    public E3Result instock(long ids){
        E3Result result = itemService.downStart(ids);
        return result;
    }

    /**
     * 商品上架功能
     */
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public E3Result reshelf(long ids){
        E3Result result = itemService.upStart(ids);
        return result;
    }

    /**
     * 加载商品描述
     */
    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    public TbItem desc(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }



    /**   //用不了
     * 加载商品描述
     */
    @RequestMapping("/rest/page/item-edit/{itemId}")
    @ResponseBody
    public TbItem item_edit(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
    /**
     * 加载商品规格
     */
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    @ResponseBody
    public TbItem query(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
    /**
     * 修改商品
     */
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public E3Result update(TbItem tbItem){
        Long id = tbItem.getId();
        TbItem itemById = itemService.getItemById(id);
        tbItem.setCreated(itemById.getCreated());
        tbItem.setStatus((byte)1);
        tbItem.setUpdated(new Date());
        return  itemService.updItem(tbItem);
    }
}
