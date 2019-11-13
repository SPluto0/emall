package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItemParam;

public interface ItemParamService {
    public EasyUIDataGridResult getItemParam(int page, int rows);

    E3Result delParam(long params);

    E3Result findId(long id);

    E3Result save(TbItemParam tbItemParam);
}
