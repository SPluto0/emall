package cn.e3mall.sso.service.Impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.e3mall.pojo.TbUserExample.Criteria;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**用户注册处理Service
 * @Date 2019/8/27 9:22
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TbUserMapper userMapper;
    @Override
    public E3Result checkData(String param, int type) {
        //根据不同的type生成不同的查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        //1:用户名 2：手机号 3：邮箱
        if(type == 1){
            criteria.andUsernameEqualTo(param);
        }else if(type == 2){
            criteria.andPhoneEqualTo(param);
        }else if(type == 3){
            criteria.andEmailEqualTo(param);
        }else {
            return E3Result.build(400,"数据类型错误");
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        //判断结果中是否包含数据
        if(list !=null&&list.size()>0){
            //如果有数据返回false
            return E3Result.ok(false);
        }
        //如果没有数据返回ture
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser user) {
        // 1、使用TbUser接收提交的请求。
        if (StringUtils.isBlank(user.getUsername())) {
            return E3Result.build(400, "用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return E3Result.build(400, "密码不能为空");
        }
        //校验数据是否可用
        E3Result result = checkData(user.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return E3Result.build(400, "此用户名已经被使用");
        }
        //校验电话是否可以
        if (StringUtils.isNotBlank(user.getPhone())) {
            result = checkData(user.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "此手机号已经被使用");
            }
        }
        //校验email是否可用
        if (StringUtils.isNotBlank(user.getEmail())) {
            result = checkData(user.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "此邮件地址已经被使用");
            }
        }
        // 2、补全TbUser其他属性。
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //把用户数据插入到数据库中
        userMapper.insert(user);
        //返回添加成功
        return E3Result.ok();
    }
}
