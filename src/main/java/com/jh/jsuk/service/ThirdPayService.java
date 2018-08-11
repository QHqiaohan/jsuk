package com.jh.jsuk.service;

import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import com.jh.jsuk.exception.MessageException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.model.Charge;

import java.io.UnsupportedEncodingException;

/**
 * Author:xyl
 * Date:2018/8/11 11:22
 * Description:第三方支付服务接口
 */
public interface ThirdPayService {
    /**
     * @param payVo
     * @return 发起支付的Charge对象
     */
    Charge thirdPay(ThirdPayVo payVo) throws UnsupportedEncodingException, ChannelException, MessageException;

    /**
     * 支付成功回调
     */
    void chargeBack(ThirdPayVoChild payVoChild) throws MessageException;
}
