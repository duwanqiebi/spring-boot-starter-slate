package com.duwan.slate;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author duwan.zq
 * @date 2020/3/6
 */
@Data
@ConfigurationProperties("slate")
public class SlateProperties {

    private Boolean enabled;

    private String defaultTitle = "API DOCUMENT";

    private String contextPath;

    private String resourcePath;

    private String index;

    private List<String> includes;

    private List<String> languages;

}
