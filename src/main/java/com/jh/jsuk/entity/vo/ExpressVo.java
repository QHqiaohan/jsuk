package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.UserAddress;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/13 15:21
 */
public class ExpressVo extends Express {

    private UserAddress senderAddressInfo;
    private UserAddress getAddressInfo;

    public UserAddress getSenderAddressInfo() {
        return senderAddressInfo;
    }

    public void setSenderAddressInfo(UserAddress senderAddressInfo) {
        this.senderAddressInfo = senderAddressInfo;
    }

    public UserAddress getGetAddressInfo() {
        return getAddressInfo;
    }

    public void setGetAddressInfo(UserAddress getAddressInfo) {
        this.getAddressInfo = getAddressInfo;
    }
}
