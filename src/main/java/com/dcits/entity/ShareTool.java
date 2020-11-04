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
public class ShareTool {
    private Integer id; /** 工具id */
    private String name = "";    /** 工具名称 */
    private String describe = "";    /** 工具描述 */
    private String uploadTime = "";  /** 上传时间 */
    private String author = "";  /** 作者 */
    private Integer downloads = 0;   /** 下载次数 */
    private Integer likes = 0;   /** 点赞数 */
    private String url = ""; /** 压缩包地址 */

}
