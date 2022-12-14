package hello.itemservice.domain.login;

import hello.itemservice.converter.IntegerToStringConverter;
import hello.itemservice.converter.IpPortToStringConverter;
import hello.itemservice.converter.StringToIntegerConverter;
import hello.itemservice.converter.StringToIpPortConverter;
import hello.itemservice.converter.type.formatter.MyNumberFormatter;
import hello.itemservice.exception.filter.LogFilter;
import hello.itemservice.web.filter.LoginCheckFilter;
import hello.itemservice.web.intercepter.LogInterceptor;
import hello.itemservice.web.intercepter.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add",
                        "/login", "/logout", "/css/**",
                        "/*.ico", "/error/**", "/error-page/**",
                        "/error/*", "/error*", "/hello/**",
                        "/servlet/**", "/spring/**", "/items/**",
                        "/images/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 주석처리 우선순위
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //추가
        registry.addFormatter(new MyNumberFormatter());

    }

    //
//    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//
//        filterRegistrationBean.setFilter(new LogFilter());
//        filterRegistrationBean.setOrder(1);
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
//
//        return filterRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//        filterFilterRegistrationBean.setFilter(new LogFilter());
//        filterFilterRegistrationBean.setOrder(1);
//        filterFilterRegistrationBean.addUrlPatterns("/*");
//
//        return filterFilterRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean loginCheckFilter() {
//        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
//        filterFilterRegistrationBean.setOrder(2);
//        filterFilterRegistrationBean.addUrlPatterns("/*");
//
//        return filterFilterRegistrationBean;
//    }
}
