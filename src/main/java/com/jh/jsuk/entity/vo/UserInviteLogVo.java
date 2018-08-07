package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserInviteLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserInviteLogVo extends UserInviteLog {

    private UserVo2 userInfo;

}
