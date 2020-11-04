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
public class WordTemplate {
    private int id ;    /** word模板id */
    private String name;    /** word模板名称 */
    private String images;  /** word模板封面图片 */
    private String url; /** word模板地址 */
}
