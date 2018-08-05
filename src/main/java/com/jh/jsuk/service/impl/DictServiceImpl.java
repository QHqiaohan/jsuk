package com.jh.jsuk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jh.jsuk.entity.Dict;
import com.jh.jsuk.dao.DictDao;
import com.jh.jsuk.service.DictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements DictService {

    private static final String ENUM_PACKAGE = "com.jh.jsuk.envm";

    @Override
    public List<Map<String, Object>> getDict(String type, String code) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        if (StrUtil.isBlank(type)) {
            type = "enum";
        }
        if ("enum".equals(type)) {
            Class<?> clazz = Class.forName(ENUM_PACKAGE + "." + code);
            Object[] values = clazz.getEnumConstants();
            for (Object value : values) {
                Map<String, Object> map = new HashMap<>();
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    if (Pattern.compile("^get").matcher(name).find()) {
                        String key = name.replaceAll("^get", "");
                        key = key.substring(0, 1).toLowerCase() + key.substring(1);
                        map.put(key, method.invoke(value));
                    }
                }
                map.put("VALUE", String.valueOf(value));
                list.add(map);
            }
        } else {

        }
        return list;
    }
}
