package com.jh.jsuk.conf;

import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.exception.NeedLoginException;
import com.jh.jsuk.exception.RuntimeMessageException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;

@Getter
@Setter
@ToString(exclude = "cityId")
@Component
@SessionScope
public class Session {

    private Integer userId;

    private UserType userType;

    private Date lastLogin;

    private Integer canUse;

    private String nickName;

    private String headImage;

    private String phone;

    private Integer shopId;

    private String shopName;

    private Integer cityId;

    private Integer provinceId;

    public Integer getCityId() {
        if (cityId == null) {
            throw new RuntimeMessageException("请选择城市");
        }
        return this.cityId;
    }

    public boolean isValid() {
        return userId != null && userType != null;
    }

    public boolean canUse() {
        return canUse != null && canUse.equals(1);
    }

    /**
     * 获取到数据就登录了
     *
     * @return
     * @throws NeedLoginException
     */
    public Integer lUserId() throws NeedLoginException {
        if (userId == null)
            throw new NeedLoginException();
        return userId;
    }

    /**
     * 获取到数据就登录了
     *
     * @return
     * @throws NeedLoginException
     */
    public UserType lUserType() throws NeedLoginException {
        if (userType == null)
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

    public boolean isShop() {
        return UserType.SHOP.equals(this.userType);
    }

    public boolean isAdmin() {
        return UserType.ADMIN.equals(this.userType);
    }

    public Integer confirmShopId() throws MessageException {
        if (!UserType.SHOP.equals(userType)) {
            throw new MessageException("非店铺没有shopId");
        }
        return shopId;
    }
//
//    public ParentUser toParentUser() {
//        ParentUser user = new ParentUser<>();
//        user.setId(userId);
//        user.setCanUse(canUse);
//        user.setLastLoginTime(lastLogin);
//        return user;
//    }
//
//    @Autowired
//    ShopService shopService;
//
//    public void fromParentUserEx(ParentUserEx userEx) {
//        userId = userEx.getUserId();
//        lastLogin = userEx.getLastLogin();
//        canUse = userEx.getCanUse();
//        nickName = userEx.getNickName();
//        phone = userEx.getPhone();
//        shopId = userEx.getShopId();
//        if (shopId == null)
//            return;
//        Shop shop = shopService.selectById(shopId);
//        if (shop == null)
//            return;
//        shopName = shop.getShopName();
//
//    }
//
//    /**
//     * 注意没有userType
//     *
//     * @param user
//     */
//    public void fromParentUser(ParentUser user) {
//        this.userId = user.getId();
//        this.lastLogin = user.getLastLoginTime();
//        this.canUse = user.getCanUse();
//    }

}
