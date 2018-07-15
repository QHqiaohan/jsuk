//package com.jh.jsuk.conf;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.List;
//
//
///**
// * <p>
// * WEB 初始化相关配置
// * </p>
// *
// * @author hubin
// * @since 2017-12-11
// */
//@ControllerAdvice
//@Configuration
//public class JsonConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
////        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//        builder.serializationInclusion(JsonInclude.Include.ALWAYS);
////
//        ObjectMapper objectMapper = builder.build();
//
//        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
//
//
////        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
//        SimpleModule simpleModule = new SimpleModule();
////        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
////        converter.setJsonPrefix("asdfasfasd:sdfasdfasd");
////        converter.setPrettyPrint(true);
//
//        converters.add(converter);
//        super.configureMessageConverters(converters);
//    }
//}
