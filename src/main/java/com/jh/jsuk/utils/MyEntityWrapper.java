package com.jh.jsuk.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

public class MyEntityWrapper<T> extends EntityWrapper<T> {
    @Override
    public Wrapper<T> eq(String column, Object params) {
        if (params != null && !"".equals(params)) {
            this.sql.WHERE(this.formatSql(String.format("%s = {0}", column), params));
        }
        return this;
    }

    @Override
    public Wrapper<T> like(String column, String value) {
        if (value != null && !"".equals(value)) {
            return this.like(true, column, value);
        }
        return this;
    }

    @Override
    public Wrapper<T> orderBy(String columns, boolean isAsc) {
        if (this.getSqlSegment() == null) {
            this.eq("1", 1);
        }
        return this.orderBy(true, columns, isAsc);
    }
}
