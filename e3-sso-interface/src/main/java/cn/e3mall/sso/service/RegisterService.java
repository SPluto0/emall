package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @Date 2019/8/27 9:21
 */
public interface RegisterService {
    E3Result checkData(String param,int type);

    E3Result register(TbUser user);
}
