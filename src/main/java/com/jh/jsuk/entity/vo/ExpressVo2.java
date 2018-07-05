package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.UserAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/13 15:21
 */
@Getter
@Setter
@ToString
public class ExpressVo2 extends Express {

    private UserAddress senderAddressInfo;
    private UserAddress getAddressInfo;

    private UserVo userInfo;
    private DistributionUser distributionUserInfo;

    private Double senderDistance;

    private Double getDistance;

//    public UserAddress getSenderAddressInfo() {
//        return senderAddressInfo;
//    }
//
//    public void setSenderAddressInfo(UserAddress senderAddressInfo) {
//        this.senderAddressInfo = senderAddressInfo;
//    }
//
//    public UserAddress getGetAddressInfo() {
//        return getAddressInfo;
//    }
//
//    public void setGetAddressInfo(UserAddress getAddressInfo) {
//        this.getAddressInfo = getAddressInfo;
//    }
//
//    public Double getGetDistance() {
//        return getDistance;
//    }
//
//    public void setGetDistance(Double getDistance) {
//        this.getDistance = getDistance;
//    }
//
//    public Double getSenderDistance() {
//        return senderDistance;
//    }
//
//    public void setSenderDistance(Double senderDistance) {
//        this.senderDistance = senderDistance;
//    }

    /**
     * 可以计算位置信息
     * @return
     */
    public boolean canCalcSenderDistance() {
        return senderAddressInfo != null && senderAddressInfo.hasValidLocation();
    }

    public boolean canCalcGetDistance() {
        return getAddressInfo != null && getAddressInfo.hasValidLocation();
    }
}
