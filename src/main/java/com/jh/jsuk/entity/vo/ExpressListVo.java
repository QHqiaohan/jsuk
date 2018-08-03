package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.User;
import lombok.Data;


/**
 * Author:xyl
 * Date:2018/8/2 15:45
 * Description:后台-订单详情
 */
@Data
public class ExpressListVo extends Express {
    private User user;
}
