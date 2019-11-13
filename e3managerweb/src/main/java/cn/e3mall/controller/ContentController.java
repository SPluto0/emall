package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 内容管理Controller
 * @Date 2019/8/12 14:02
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent content){
        //调用服务把内容数据保存到数据库
        E3Result e3Result = contentService.addContent(content);
        return e3Result;
    }

    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(long categoryId,Integer page, Integer rows){
        //调用服务查询商品列表
        EasyUIDataGridResult result = contentService.getItemList(categoryId,page, rows);
        return result;
    }
    @RequestMapping(value = "/content/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result delContent(long ids){
        E3Result result = contentService.delContent(ids);
        return result;
    }

    @RequestMapping(value = "/rest/content/edit")
    @ResponseBody
    public E3Result updContent(TbContent tbContent){
        tbContent.setCategoryId(89L);
        E3Result result = contentService.updContent(tbContent);
        return result;
    }
}
