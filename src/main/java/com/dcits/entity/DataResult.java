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
public class DataResult {
    private int total_data;
    private Object list_data;

}
