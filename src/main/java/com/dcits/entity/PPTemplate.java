package com.dcits.entity;

import lombok.*;
/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:00
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PPTemplate {
    private int id ;    /** ppt模板id */
    private String name;    /** ppt模板名称 */
    private String images;  /** ppt模板图片 */
    private String url;
}
