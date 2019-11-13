package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {

    E3Result addContent(TbContent content);

    List<TbContent> getContentListByCid(long cid);

    EasyUIDataGridResult getItemList(long categoryId, Integer page, Integer rows);

    E3Result delContent(long ids);

    E3Result updContent(TbContent tbContent);
}
