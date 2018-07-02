package com.jh.jsuk.conf;

import com.jh.jsuk.entity.ParentUser;
import com.jh.jsuk.envm.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;

@Getter
@Setter
@ToString
@Component
@SessionScope
public class Session {

    private boolean login = false;

    private Integer userId;

    private UserType userType;

    private Date lastLogin;

    private Integer canUse;

    public ParentUser toParentUser(){
        ParentUser user = new ParentUser<>();
        user.setId(userId);
        user.setCanUse(canUse);
        user.setLastLoginTime(lastLogin);
        return user;
    }

    /**
     * 注意没有userType
     * @param user
     */
    public void fromParentUser(ParentUser user){
        this.userId = user.getId();
        this.lastLogin = user.getLastLoginTime();
        this.canUse = user.getCanUse();
    }

}
