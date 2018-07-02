package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.rules.AccountRule;
import com.jh.jsuk.entity.vo.UserApplyVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.DisJPushUtils;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 配送端提现申请
 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/distributionApply")
public class DistributionApplyController {

    public static final Double Percent = 0.01;

    @Autowired
    private DistributionUserService userService;
    @Autowired
    private DistributionApplyService applyService;
    @Autowired
    private RuleEngineService<AccountRule> ruleEngineService;
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    UserBankService userBankService;

    @ApiOperation("骑手-申请提现")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "bankId", value = "银行卡id",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "money", value = "提现金额",
                    required = false, paramType = "query", dataType = "double"),
    })
    @PostMapping("/add")
    public Result add(DistributionApply apply) {
        Integer userId = apply.getUserId();
        DistributionUser user = userService.selectById(userId);
        BigDecimal account = user.getAccount();
        if (account.compareTo(apply.getMoney()) == -1) {
            throw new RuntimeException("申请失败,您的提款金额大于了账户金额！");
        }
        UserBank userBank = userBankService.selectById(apply.getBankId());
        if(userBank == null)
            throw new RuntimeException("银行卡不存在！");
        DistributionDetail detail = new DistributionDetail();
        detail.setDetail("提现");
        detail.setMoney(apply.getMoney().negate());
        AccountRule accountRule = new AccountRule(user, detail);
        AccountRule result = ruleEngineService.executeRuleEngine("myDetail", accountRule);
        result.updateUserAndInsertDetail();
        EntityWrapper<Dictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("code", "poundage_configuration");
        Dictionary dictionary = dictionaryService.selectOne(wrapper);
        BigDecimal poundage_configuration = new BigDecimal(dictionary.getValue());
        BigDecimal money = apply.getMoney();
        BigDecimal poundage = poundage_configuration.multiply(money).multiply(new BigDecimal(Percent)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        apply.setPoundage(poundage);
        apply.insert();
        return new Result().success();
    }

    @ApiOperation("骑手-提现申请列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer"),
    })
    @GetMapping("/list")
    public Result list(Page page, Integer userId) {
        Page applyPage = applyService.selectPage(page, new EntityWrapper<DistributionApply>()
                .eq(DistributionApply.USER_ID, userId)
                .orderBy(DistributionApply.PUBLISH_TIME, false));
        return new Result().success(applyPage);
    }

    @GetMapping("/ui/list")
    public Result uiList(Page page, String phone, Integer status) {
        Page<UserApplyVo> userApplyVoPage = applyService.selectPageByUserInfo(page, new MyEntityWrapper()
                .like("phone", phone)
                .eq("status", status)
                .orderBy("t1.publish_time", false));
        return new Result().success(userApplyVoPage);
    }

    @PostMapping("/ui/adopt")
    public Result uiAgree(DistributionApply apply) {
        apply.setStatus(1);
        apply.updateById();
        DisJPushUtils.pushMsg(apply.getUserId() + "", "您的提现申请已通过注意查收", "", null);
        return new Result().success();
    }

    @PostMapping("/ui/refuse")
    public Result uiRefuse(DistributionApply apply) {
        DistributionUser user = userService.selectById(apply.getUserId());

        DistributionDetail detail = new DistributionDetail();
        detail.setDetail("提现拒绝");
        detail.setMoney(apply.getMoney());
        AccountRule accountRule = new AccountRule(user, detail);
        AccountRule result = ruleEngineService.executeRuleEngine("myDetail", accountRule);
        result.updateUserAndInsertDetail();

        apply.setStatus(2);
        apply.updateById();

        DisJPushUtils.pushMsg(apply.getUserId() + "", "您的提现申请未通过：" + apply.getDesc(), "", null);
        return new Result().success();
    }

}

