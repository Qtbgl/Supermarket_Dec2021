package com.weidong.exception;

public class IdNotFoundException extends Exception{
    final public static int CUSTOMER = 1;
    final public static int SALE = 2;
    final public static int GOODS = 3;
    int sign;

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public IdNotFoundException() {
    }

    public IdNotFoundException(int sign) {
        this.sign = sign;
    }
}
