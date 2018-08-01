package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserAddress;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/1 16:31
 * Description:
 */
@Data
public class AfterSaleVo implements Serializable {

    private UserAddress address;

    private String shopPhone;
}
