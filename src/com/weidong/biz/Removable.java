package com.weidong.biz;

import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;

public interface Removable {
    public void remove(Supermarket_Member member);
}
