package com.jh.jsuk.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        if ("true".equals(source)) {
            return 1;
        } else if ("false".equals(source)) {
            return 0;
        }
        return Integer.valueOf(source);
    }

}
