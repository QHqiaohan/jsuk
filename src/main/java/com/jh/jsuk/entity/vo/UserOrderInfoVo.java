package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/27 15:09
 */

@Setter
@Getter
@ToString
public class UserOrderInfoVo extends UserOrder {

//    private Integer num;
//
//    private BigDecimal buyPrice;
//
//    private String sizeName;
//
//    private String goodsName;
//
//    //private String goodsHeadImg;
//    private String shopName;
//
//    private String salesPrice;
//
//    private String originalPrice;
//
//    private Integer shopId;
//
//    private Integer orderType;
//
//    private String refundReason;
//
//    private Integer goodsSizeId;
//
//    private BigDecimal discount;
//
//    private Integer id;
//
//    private Integer status;
//
//    private Date creatTime;
//
//    private Integer isClosed;
//
//    private Date sendTime;

    private List<UserOrderDetailVo> shopGoodsList;

}
