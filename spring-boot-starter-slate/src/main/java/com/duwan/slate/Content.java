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
public class Content {

    private String id;

    private String content;

    private String title;

    private Integer level;

    private List<Content> children;

}
