package com.duwan.slate;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author duwan.zq
 * @date 2020/3/9
 */
@Data
@Builder
public class Attribute {

    private List<Content> contents;

    private String html;

}
