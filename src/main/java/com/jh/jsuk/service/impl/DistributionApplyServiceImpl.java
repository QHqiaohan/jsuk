package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.DistributionApplyDao;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.vo.UserApplyVo;
import com.jh.jsuk.entity.vo.DistributionApplyVo;
import com.jh.jsuk.envm.DistributionApplyStatus;
import com.jh.jsuk.envm.DistributionApplyType;
import com.jh.jsuk.service.DistributionApplyService;
import com.jh.jsuk.service.DistributionDetailService;
import com.jh.jsuk.service.DistributionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 配送端提现申请
 * 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class DistributionApplyServiceImpl extends ServiceImpl<DistributionApplyDao, DistributionApply> implements DistributionApplyService {


    @Override
    public Page<UserApplyVo> selectPageByUserInfo(Page page, Wrapper wrapper) {
        SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(this.baseMapper.selectPageByUserInfo(page, wrapper));
        return page;
    }

    @Override
    public Page searchDistributionUserTiXian(Page page, Wrapper wrapper, Integer tixianId, Integer begin, Integer end, Integer status) {
        SqlHelper.fillWrapper(page, wrapper);
        List<DistributionApplyVo> list = baseMapper.searchDistributionUserTiXian(page, wrapper, tixianId, begin, end, status);
        page.setRecords(list);
        return page;
    }

    @Override
    public void createCashApplying(Integer userId, BigDecimal price, String tiXianNo, Integer bankId) {
        DistributionApply a = new DistributionApply();
        a.setUserId(userId);
        a.setMoney(price);
        a.setStatus(DistributionApplyStatus.APPLYING);
        a.setBankId(bankId);
        a.setPlatformNo(tiXianNo);
        a.insert();
    }

    @Autowired
    DistributionDetailService distributionDetailService;

    @Override
    public void confirm(String no) {
        DistributionApply entity = new DistributionApply();
        EntityWrapper<DistributionApply> wrapper = new EntityWrapper<>();
        entity.setStatus(DistributionApplyStatus.PASSED);
        wrapper.eq(DistributionApply.PLATFORM_NO, no);
        DistributionApply apply = selectOne(wrapper);
        update(entity, wrapper);
        distributionDetailService.tixian(apply.getUserId(), apply.getMoney());
    }

    @Override
    public void decline(String no) {
        DistributionApply entity = new DistributionApply();
        EntityWrapper<DistributionApply> wrapper = new EntityWrapper<>();
        entity.setStatus(DistributionApplyStatus.REFUSED);
        wrapper.eq(DistributionApply.PLATFORM_NO, no);
        update(entity, wrapper);
    }

    @Override
    public BigDecimal getRemainder(Integer userId) {
        EntityWrapper<DistributionApply> wrapper = new EntityWrapper<>();
        wrapper.eq(DistributionApply.USER_ID, userId);
        wrapper.eq(DistributionApply.STATUS, DistributionApplyStatus.PASSED.getKey());
        List<DistributionApply> list = selectList(wrapper);
        BigDecimal bigDecimal = new BigDecimal("0");
        if (list == null || list.isEmpty()) {
            return bigDecimal;
        }
        for (DistributionApply apply : list) {
            DistributionApplyType type = apply.getType();
            BigDecimal money = apply.getMoney();
            if (type == null || money == null)
                continue;
            money = money.abs();
            switch (type) {
                case DISTP_COMPLETE:
                    bigDecimal = bigDecimal.add(money);
                    break;
                case CASH:
                    bigDecimal = bigDecimal.subtract(money);
                    break;
            }
        }
        return bigDecimal;
    }

    @Autowired
    DistributionUserService distributionUserService;

    @Override
    public void syncRemainder(Integer userId) {
        DistributionUser entity = new DistributionUser();
        entity.setId(userId);
        entity.setAccount(getRemainder(userId));
        distributionUserService.updateById(entity);
    }

}
