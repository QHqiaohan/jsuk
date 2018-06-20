package com.jh.jsuk.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lyq on 2017/7/17.
 */
@Configuration
public class SpringMVCConfig {

    @Bean
    public ConversionServiceFactoryBean getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(addNewConvert());
        converters.add(addNewConvert2());
        bean.setConverters(converters);
        return bean;
    }


    @Bean("TimeConverter")
    public Converter<String, Time> addNewConvert2() {
        return new Converter<String, Time>() {
            @Override
            public Time convert(String source) {
                Time time = Time.valueOf(source);
                return time;
            }
        };
    }

    @Bean("DateConverter")
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(source);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }


}
