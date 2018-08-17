package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.entity.ShopUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Author: xyl
 * Date:2018/7/31 15:27
 * Description:商户信息
 */
@Setter
@Getter
@ToString
public class ShopUserVo extends ShopUser {

    private String shopName;

    private String userName;

    private String phone;

    private String headImg;

    private List<ModularPortal> modularPortals;

    private String modularNames;

    private String license;

    private Boolean isRecommend;

}
