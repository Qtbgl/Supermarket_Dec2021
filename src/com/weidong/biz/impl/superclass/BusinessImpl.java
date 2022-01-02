package com.weidong.biz.impl.superclass;

import com.weidong.datebase.CustomerSQL;
import com.weidong.datebase.GoodsSQL;
import com.weidong.datebase.SaleSQL;

abstract public class BusinessImpl {
    protected CustomerSQL customerSQL;
    protected GoodsSQL goodsSQL;
    protected SaleSQL saleSQL;

    public BusinessImpl(CustomerSQL customerSQL, GoodsSQL goodsSQL, SaleSQL saleSQL) {
        this.customerSQL = customerSQL;
        this.goodsSQL = goodsSQL;
        this.saleSQL = saleSQL;
    }
}
