package com.weidong.biz;

import com.weidong.entity.Purchase;

public interface Buyable {
    //需要purchase的 customer.id 和 sale.id
    public void buy(Purchase purchase);
}
