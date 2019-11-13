package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @Date 2019/8/8 10:09
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatlist(long parentId);
}
