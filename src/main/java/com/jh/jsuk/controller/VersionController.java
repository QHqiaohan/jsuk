package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Version;
import com.jh.jsuk.service.VersionService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 版本信息 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"版本相关:"})
@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @ApiIgnore
    @RequestMapping("/ui/list")
    public Result uiList(Page page) {
        Page versionPage = versionService.selectPage(page);
        return new Result().success(versionPage);
    }

    @ApiIgnore
    @PostMapping("/ui/edit")
    public Result uiEdit(Version version) {
        version.updateById();
        return new Result().success();
    }
}
