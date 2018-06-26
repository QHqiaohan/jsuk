package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Version;
import com.jh.jsuk.service.VersionService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation("查看版本信息")
    @PostMapping("/show")
    public Result show(@ApiParam(value = "0=安卓,1=ios", required = true)
                       @RequestParam Integer osType,
                       @ApiParam(value = "1=商家端,2=骑手端,3=用户端", required = true)
                       @RequestParam Integer appType) {
        Version version = versionService.selectOne(new EntityWrapper<Version>()
                .eq(Version.OS_TYPE, osType)
                .eq(Version.APP_TYPE, appType));
        return new Result().success(version);
    }

    @ApiOperation("版本更新")
    @PostMapping("/update")
    public Result update(@ApiParam(value = "0=安卓,1=ios", required = true)
                         @RequestParam Integer osType,
                         @ApiParam(value = "1=商家端,2=骑手端,3=用户端", required = true)
                         @RequestParam Integer appType) {
        Version version = versionService.selectOne(new EntityWrapper<Version>()
                .eq(Version.OS_TYPE, osType)
                .eq(Version.APP_TYPE, appType));
        // 获取当前版本
        String v = version.getVersion();
        // 获取最新版本
        Version newVersion = versionService.getNewVersion();
        String newV = newVersion.getVersion();
        if (v.equals(newV)) {
            return new Result().success(version);
        } else {
            return new Result().success(newV);
        }
    }

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
