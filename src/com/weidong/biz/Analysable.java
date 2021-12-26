package com.weidong.biz;

import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.entity.superclass.Supermarket_Member;

import java.util.List;

public interface Analysable {
    public List<Sale> analyseSales(Supermarket_Member member);
    public List<Purchase> analysePurchases(Supermarket_Member member);
}
