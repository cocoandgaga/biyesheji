
package io.renren.config;

import io.renren.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //代理过滤器 @Bean("shiroFilter")
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);//优先级越小越高
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        /**
         * Filter是有作用范围的，我们平常都是使用Filter作用于Request，
         * 这也是Filter中dispatcherTypes属性的默认值，
         * 这个属性的意思是由该过滤器管理的资源是通过什么样的方式访问到的，可以是请求、转发、声明式错误、包含等，
         * 但是我们还可以配置使Filter作用于其他范围，这是由dispatcherTypes属性决定的，它有如下几个值：
         * DispatcherType.REQUEST
         * DispatcherType.FORWARD
         * DispatcherType.INCLUDE
         * DispatcherType.ERROR
         * DispatcherType.ASYNC
         * Filter属性的默认值就是DispatcherType.REQUEST，由于它是一个数组属性，所以可以配置多个值
         */
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
