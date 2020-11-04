package com.dcits.service.impl;

import com.dcits.dao.OfficeSourceMapper;
import com.dcits.entity.DataResult;
import com.dcits.entity.PPTemplate;
import com.dcits.entity.ShareTool;
import com.dcits.entity.WordTemplate;
import com.dcits.service.OfficeSourceService;
import com.github.pagehelper.PageHelper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:00
 */
@Service
public class OfficeSourceServiceImpl implements OfficeSourceService {
    private final static Logger logger = LoggerFactory.getLogger(OfficeSourceServiceImpl.class);
    @Resource
    private OfficeSourceMapper officeSourceMapper;

    /***
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public DataResult findShareToolDataByPage(int pageNumber, int pageSize) {
        int total = officeSourceMapper.findShareToolDataByPage().size();
//        int total = officeSourceMapper.selectTool().size();
        PageHelper.startPage(pageNumber,pageSize);
        List<ShareTool> allInfo = officeSourceMapper.findShareToolDataByPage();
        return DataResult.builder().total_data(total).list_data(allInfo).build();
    }

    /***
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public DataResult findPPTemplateDataByPage(int pageNumber, int pageSize) {
        int total = officeSourceMapper.findPPTemplateDataByPage().size();
        PageHelper.startPage(pageNumber,pageSize);
        List<ShareTool> allInfo = officeSourceMapper.findPPTemplateDataByPage();
        return DataResult.builder().total_data(total).list_data(allInfo).build();
    }

    /***
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public DataResult findWordTemplateDataByPage(int pageNumber, int pageSize) {
        int total = officeSourceMapper.findWordTemplateDataByPage().size();
        PageHelper.startPage(pageNumber,pageSize);
        List<ShareTool> allInfo = officeSourceMapper.findWordTemplateDataByPage();
        return DataResult.builder().total_data(total).list_data(allInfo).build();
    }

    /***
     *
     * @param mapInfo
     * @return
     */
    @Override
    @Transactional
    public String doUploadFile(Map<String,Object> mapInfo) {
        MultipartFile[] fileArr = (MultipartFile[]) mapInfo.get("sourceFile");
        String name = (String) mapInfo.get("name");
        String describe = (String) mapInfo.get("describe");
        String type = (String) mapInfo.get("type"); //上传类型：1代表PPT模板，2代表word，3代表工具
        String author = (String) mapInfo.get("author");
        String targetFilePath = System.getProperty("user.dir") + File.separator + "uploadFilePath";
        String regUrl = "";
        if (fileArr!=null && fileArr.length>0) {
            for ( int i = 0; i < fileArr.length; i++) {
                MultipartFile sourceFile = fileArr[i];
                if (sourceFile != null && !sourceFile.isEmpty()) {
                    String softwareFile = targetFilePath + File.separator + new Date().getTime()+"_"+sourceFile.getOriginalFilename();
                    File file = new File(softwareFile);
                    FileOutputStream fileOutputStream = null;
                    regUrl += softwareFile + ";";
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        IOUtils.copy(sourceFile.getInputStream(), fileOutputStream);
                        logger.info("文件上传Service结束:=====Service");
                    } catch (IOException e) {
                        logger.error("文件上传Service报错:=====Service",e);
                    } finally {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
            regUrl = regUrl.substring(0,regUrl.length()-1);
            switch (Integer.parseInt(type)){
                case 3:
                    officeSourceMapper.insertPathForUpload_ShareTool(ShareTool.builder().name(name).describe(describe).author(author).url(regUrl).build());
                    break;
                case 2:
                    officeSourceMapper.insertPathForUpload_WordTemplate(WordTemplate.builder().name(name).url(regUrl).build());
                    break;
                case 1:
                    officeSourceMapper.insertPathForUpload_PPTemplate(PPTemplate.builder().name(name).url(regUrl).build());
                    break;
            }
        }
        return targetFilePath;
    }

    @Override
    @Transactional
    public void deleteShareToolById(int id) {
        officeSourceMapper.deleteShareToolById(ShareTool.builder().id(id).build());
    }
    @Override
    @Transactional
    public void deletePPTemplateById(int id) {
        officeSourceMapper.deletePPTemplateById(ShareTool.builder().id(id).build());
    }
    @Override
    @Transactional
    public void deleteWordTemplateById(int id) {
        officeSourceMapper.deleteWordTemplateById(ShareTool.builder().id(id).build());
    }

}
