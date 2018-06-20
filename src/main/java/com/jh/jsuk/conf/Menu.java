package com.jh.jsuk.conf;

import java.util.List;

public class Menu {
    // 菜单id
    private String id;
    // 菜单名称
    private String name;
    // 父菜单id
    private String parentId;
    // 菜单url
    //private String url;
    // 菜单图标
    private String icon;
    // 菜单顺序
    private int order;
    // 子菜单
    private List<Menu> childMenus;

    public Menu() {
    }

    public Menu(String id, String name, String parentId, String icon, int order) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.icon = icon;
        this.order = order;
    }

    public Menu(String id, String name, String parentId, String icon, int order, List<Menu> childMenus) {

        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.icon = icon;
        this.order = order;
        this.childMenus = childMenus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", icon='" + icon + '\'' +
                ", order=" + order +
                ", childMenus=" + childMenus +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }
}