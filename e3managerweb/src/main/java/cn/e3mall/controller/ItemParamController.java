package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItemParam;
import cn.e3mall.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date 2019/10/29 16:59
 */
@Controller
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    @RequestMapping("/item/param/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParam(Integer page, Integer rows){
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemParamService.getItemParam(page, rows);
        return result;
    }


    @RequestMapping(value = "/item/param/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result delete(long params){
        return itemParamService.delParam(params);
    }

    @RequestMapping(value = "/item/param/query/itemcatid/{id}")
    @ResponseBody
    public E3Result itemcatid(@PathVariable long id){
        return itemParamService.findId(id);
    }


    @RequestMapping(value = "/item/param/save")
    @ResponseBody
    public E3Result save(@RequestBody TbItemParam tbItemParam){
        return itemParamService.save(tbItemParam);
    }
}
