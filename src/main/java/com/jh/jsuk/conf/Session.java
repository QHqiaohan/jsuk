package com.jh.jsuk.conf;

import com.jh.jsuk.entity.ParentUser;
import com.jh.jsuk.entity.ParentUserEx;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.exception.NeedLoginException;
import com.jh.jsuk.service.ShopService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String nickName;

    private String phone;

    private Integer shopId;

    private String shopName;

    /**
     * 获取到数据就登录了
     * @return
     * @throws NeedLoginException
     */
    public Integer lUserId() throws NeedLoginException {
        if(userId == null)
            throw new NeedLoginException();
        return userId;
    }

    /**
     * 获取到数据就登录了
     * @return
     * @throws NeedLoginException
     */
    public UserType lUserType () throws NeedLoginException {
        if(userType == null)
            throw new NeedLoginException();
        return userType;
    }

    /**
     * 获取用户位移识别码
     *
     * @return
     * @throws Exception
     */
    public String userUid() throws Exception {
        if (userId == null || userType == null)
            throw new NeedLoginException();
        return String.valueOf(userType) + userId;
    }

    public boolean isShop(){
        return UserType.SHOP.equals(this.userType);
    }

    public boolean isAdmin(){
        return UserType.ADMIN.equals(this.userType);
    }

    public Integer confirmShopId() throws MessageException {
        if (!UserType.SHOP.equals(userType)) {
            throw new MessageException("非店铺没有shopId");
        }
        return shopId;
    }

    public ParentUser toParentUser() {
        ParentUser user = new ParentUser<>();
        user.setId(userId);
        user.setCanUse(canUse);
        user.setLastLoginTime(lastLogin);
        return user;
    }

    @Autowired
    ShopService shopService;

    public void fromParentUserEx(ParentUserEx userEx) {
        userId = userEx.getUserId();
        lastLogin = userEx.getLastLogin();
        canUse = userEx.getCanUse();
        nickName = userEx.getNickName();
        phone = userEx.getPhone();
        shopId = userEx.getShopId();
        if (shopId == null)
            return;
        Shop shop = shopService.selectById(shopId);
        if (shop == null)
            return;
        shopName = shop.getShopName();

    }

    /**
     * 注意没有userType
     *
     * @param user
     */
    public void fromParentUser(ParentUser user) {
        this.userId = user.getId();
        this.lastLogin = user.getLastLoginTime();
        this.canUse = user.getCanUse();
    }

}
