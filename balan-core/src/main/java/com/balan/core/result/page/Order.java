package com.balan.core.result.page;


import com.balan.core.enumeration.GenericEnum;

public enum Order implements GenericEnum<Order, String> {
    ASC("asc"),
    DESC("desc");

    private String value;
    Order(String value) {
        this.value = value;
    }


    @Override
    public String value() {
        return value;
    }
}
