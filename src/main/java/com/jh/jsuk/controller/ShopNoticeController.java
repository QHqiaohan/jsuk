package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ShopNotice;
import com.jh.jsuk.service.ShopNoticeService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商家端-提醒通知 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Api(tags = {"商家端-接收消息/通知"})
@RestController
@RequestMapping("/shopNotice")
public class ShopNoticeController {

    @Autowired
    private ShopNoticeService shopNoticeService;

    @ApiOperation(value = "商家端-接收提醒/通知列表", notes = "1=接收,0=否")
    @RequestMapping(value = "/noticeList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result noticeList(Integer userId) {
        ShopNotice shopNotice = shopNoticeService.selectOne(new EntityWrapper()
                .eq(ShopNotice.MANAGER_ID, userId));
        if (shopNotice == null) {
            ShopNotice notice = new ShopNotice();
            notice.setIsNewNotice(1);
            notice.setIsNotice(1);
            notice.setManagerId(userId);
            notice.insert();
            return new Result().success(notice);
        } else {
            return new Result().success(shopNotice);
        }
    }

    @ApiOperation("商家端-接收提醒/通知")
    @RequestMapping(value = "/notice", method = {RequestMethod.POST, RequestMethod.GET})
    public Result notice(Integer userId,
                         @ApiParam(value = "1=接收提醒,0=不接收", required = true) Integer notice,
                         @ApiParam(value = "1=新消息通知,0=不接收", required = true) Integer newNotice) {

        ShopNotice shopNotice = shopNoticeService.selectOne(new EntityWrapper()
                .eq(ShopNotice.MANAGER_ID, userId));
        if (shopNotice == null) {
            ShopNotice sn = new ShopNotice();
            sn.setManagerId(userId);
            sn.setIsNotice(notice);
            sn.setIsNewNotice(newNotice);
            sn.insert();
        } else {
            shopNotice.setManagerId(userId);
            if (notice != null) {
                shopNotice.setIsNotice(notice);
            }
            if (newNotice != null) {
                shopNotice.setIsNewNotice(newNotice);
            }
            shopNotice.updateById();
        }
        return new Result().success();
    }

}

