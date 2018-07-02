package com.jh.jsuk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class ParentUserEx{

    private Integer userId;

    private String password;

    private Integer canUse;

    private String phone;

    private String nickName;

    private Date lastLogin;

    private Integer shopId;

    public boolean canUse(){
        return canUse != null && canUse.equals(1);
    }


}
