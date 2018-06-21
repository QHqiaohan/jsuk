package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Feedback;
import com.jh.jsuk.service.FeedbackService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2017-11-15
 */
@Api(tags = {"意见反馈:"})
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation("新增意见反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", required = true, value = "反馈内容", paramType = "query", dataType = "string"),
    })
    @PostMapping("/add")
    public Result add(@ModelAttribute Feedback feedback) {
        feedback.insert();
        return new Result().success();
    }

    @ApiOperation("获取意见反馈")
    @GetMapping("/ui/list")
    public Result uiList(Page page) {
        Page feedbackPage = feedbackService.selectPage(page, new EntityWrapper<Feedback>()
                .orderBy(Feedback.PUBLISH_TIME, false));
        return new Result().success(feedbackPage);
    }

}
