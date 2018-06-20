package com.jh.jsuk.conf;

import java.util.List;

/**
 * luopa 在 2018/4/16 创建.
 */
public class Tree {
    private String name, value;
    private Boolean checked;
    private List list;

    public Tree() {
    }

    public Tree(String name, String value, Boolean checked) {

        this.name = name;
        this.value = value;
        this.checked = checked;
    }

    public Tree(String name, String value, Boolean checked, List list) {
        this.name = name;
        this.value = value;
        this.checked = checked;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
