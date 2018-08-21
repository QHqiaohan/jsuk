package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.DistributionApplyDao;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.vo.UserApplyVo;
import com.jh.jsuk.entity.vo.DistributionApplyVo;
import com.jh.jsuk.envm.DistributionApplyStatus;
import com.jh.jsuk.service.DistributionApplyService;
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
    public void createCashApplying(Integer userId, BigDecimal price, String tiXianNo) {
        DistributionApply a = new DistributionApply();
        a.setUserId(userId);
        a.setMoney(price);
        a.setStatus(DistributionApplyStatus.APPLYING);
        a.setPlatformNo(tiXianNo);
        a.insert();
    }

    @Override
    public void confirm(String no) {
        DistributionApply entity = new DistributionApply();
        EntityWrapper<DistributionApply> wrapper = new EntityWrapper<>();
        entity.setStatus(DistributionApplyStatus.PASSED);
        wrapper.eq(DistributionApply.PLATFORM_NO, no);
        update(entity, wrapper);
    }

    @Override
    public void decline(String no) {
        DistributionApply entity = new DistributionApply();
        EntityWrapper<DistributionApply> wrapper = new EntityWrapper<>();
        entity.setStatus(DistributionApplyStatus.REFUSED);
        wrapper.eq(DistributionApply.PLATFORM_NO, no);
        update(entity, wrapper);
    }

}
