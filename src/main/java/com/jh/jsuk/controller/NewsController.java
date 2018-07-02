package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.service.NewsService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户消息表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "消息相关API:")
@RestController
@RequestMapping(value = "/news", method = {RequestMethod.POST, RequestMethod.GET})
public class NewsController {

    @Autowired
    NewsService newsService;

    @ApiOperation("用户-获取用户的消息列表索引")
    @RequestMapping("/list")
    public Result list(Integer userId) throws Exception {
        return new Result().success(newsService.listIndex(userId));
    }

    @ApiOperation("用户-获取用户系统消息详情")
    @RequestMapping("/sys")
    public Result listSys(Integer userId, Page page) throws Exception {
        return new Result().success(newsService.listSys(userId, page));
    }

    @ApiOperation("用户-获取用户二手市场消息详情")
    @RequestMapping("/shmkt")
    public Result listSecondHandMarket(Integer userId, Page page) throws Exception {
        return new Result().success(newsService.listSecondHandMarket(userId, page));
    }

}

