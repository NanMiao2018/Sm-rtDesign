package com.dcits.service;

import com.dcits.entity.DataResult;
import com.dcits.entity.ShareTool;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:00
 */
public interface OfficeSourceService {
    DataResult findShareToolDataByPage(int pageNumber, int pageSize);
    DataResult findPPTemplateDataByPage(int pageNumber, int pageSize);
    DataResult findWordTemplateDataByPage(int pageNumber, int pageSize);
//    public String doUploadFile(MultipartFile sourceFile,String author);
    public String doUploadFile(Map<String,Object> mapInfo);
    public void deleteShareToolById(int id);
    public void deletePPTemplateById(int id);
    public void deleteWordTemplateById(int id);

}
