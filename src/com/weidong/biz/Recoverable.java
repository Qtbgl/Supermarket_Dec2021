package com.weidong.biz;

import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.IdNotFoundException;

public interface Recoverable {
    public void recover(Supermarket_Member member);
}
