package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Member;
import com.jh.jsuk.service.MemberService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员价格配置 前端控制器
 * </p>
 *
 * @author tj
 * @since 2018-08-10
 */
@Api(tags = "会员类型列表")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @ApiOperation("用户端-获取所有会员类型")
    @GetMapping("/list")
    public R list() {
        return R.succ(memberService.selectList(null));
    }

    @GetMapping
    public R page(Page page, String kw) {
        EntityWrapper<Member> wrapper = new EntityWrapper<>();
        if (StrUtil.isNotBlank(kw)) {
            wrapper.like(Member.MEMBER_NAME, "%" + kw + "%");
        }
        return R.succ(memberService.selectPage(page, wrapper));
    }

    @PutMapping
    public R add(Member member) {
        member.insert();
        return R.succ();
    }

    @PatchMapping
    public R edit(Member member) {
        member.updateById();
        return R.succ();
    }

    @GetMapping("/get")
    public R get(Integer id) {
        return R.succ(memberService.selectById(id));
    }

}

