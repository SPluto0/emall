package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbItemParamMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemParam;
import cn.e3mall.pojo.TbItemParamExample;
import cn.e3mall.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  规格管理service
 * @Date 2019/10/29 17:05
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    TbItemParamMapper itemParamMapper;
    @Override
    public EasyUIDataGridResult getItemParam(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemParamExample example = new TbItemParamExample();
        List<TbItemParam> list = itemParamMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public E3Result delParam(long params) {
        itemParamMapper.deleteByPrimaryKey(params);
        return E3Result.ok();
    }

    @Override
    public E3Result findId(long id) {
        TbItemParam tbItemParam = itemParamMapper.selectByPrimaryKey(id);
        if(tbItemParam == null){
            return E3Result.ok(true);
        }
        return E3Result.ok(false);
    }

    @Override
    public E3Result save(TbItemParam tbItemParam) {
        itemParamMapper.insert(tbItemParam);
        return E3Result.ok();
    }
}
