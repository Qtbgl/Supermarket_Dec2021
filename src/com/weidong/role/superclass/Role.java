package com.weidong.role.superclass;

import com.weidong.biz.CustomerBiz;
import com.weidong.biz.GoodsBiz;
import com.weidong.biz.SaleBiz;

abstract public class Role {
    protected CustomerBiz customerBiz;
    protected GoodsBiz goodsBiz;
    protected SaleBiz saleBiz;
}
