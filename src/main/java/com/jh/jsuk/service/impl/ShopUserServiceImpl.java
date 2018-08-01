package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopUserDao;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.entity.vo.ShopUserVo;
import com.jh.jsuk.service.ShopUserService;
import com.jh.jsuk.utils.DatecConvertUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: xyl
 * Date:2018/7/31 16:40
 * Description:商户信息 服务类
 */
@Service
public class ShopUserServiceImpl extends ServiceImpl<ShopUserDao, ShopUser> implements ShopUserService {

    @Override
    public Page list(Page page, String userName, String name, String[] sectionTime) throws ParseException {
        String startTime = sectionTime == null ? null : DatecConvertUtils.dateFormat(sectionTime[0]);
        String endTime = sectionTime == null ? null : DatecConvertUtils.dateFormat(sectionTime[1]);
        List<ShopUserVo> shopUserVos = baseMapper.listShopUser(page, userName, name, startTime, endTime);
        return page.setRecords(shopUserVos);
    }

    @Override
    public List<ShopUserVo> excelData(Integer[] ids) {
        List<ShopUserVo> vos=new ArrayList<>();
        for (Integer id:ids){
            vos.add(baseMapper.getShopUserById(id));
        }
        return vos;
    }
}
