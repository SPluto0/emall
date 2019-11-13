package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemCatService;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理Controller
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody         //前段发送请求传过来的值是id 所以需要设置参数   且第一次是没有值得所以需要给一个默认值
    public List<EasyUITreeNode> getItemById(@RequestParam(name = "id",defaultValue = "0") Long itemId){
        List<EasyUITreeNode> list = itemCatService.getItemCatlist(itemId);
        return list;
    }



}
