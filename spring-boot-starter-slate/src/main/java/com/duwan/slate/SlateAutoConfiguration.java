package com.duwan.slate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duwan.zq
 * @date 2020/3/6
 */
@Configuration
@ConditionalOnProperty(name = "slate.enabled", matchIfMissing = true)
public class SlateAutoConfiguration {

    @Autowired
    private SlateProperties slateProperties;

    @Bean
    @ConditionalOnMissingBean
    public SlateProperties slateProperties() {
        return new SlateProperties();
    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new SlateServlet(slateProperties));
        registration.addUrlMappings(slateProperties.getContextPath());
        return registration;
    }


}
