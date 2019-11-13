package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @Date 2019/8/19 17:12
 */
public interface SearchService {
    SearchResult search(String keyword,int page,int rows)throws Exception;
}
