package com.duwan.slate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author duwan.zq
 * @date 2020/3/10
 */
@Getter
@AllArgsConstructor
public enum ContentType{

    CSS("css", "text/css;charset=utf-8"),

    JAVASCRIPT("js", "text/javascript;charset=utf-8"),

    PNG("png", "image/png"),

    TTF("ttf", "font/ttf"),

    WOFF2("woff2", "font/woff2"),

    HTML("html", "text/html; charset=utf-8");

    private String suffix;

    private String contentType;

    public static ContentType parse(String suffix){
        for(ContentType type : ContentType.values()){
            if(type.suffix.equals(suffix)){
                return type;
            }
        }
        return null;
    }

}
