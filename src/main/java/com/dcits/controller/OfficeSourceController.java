package com.dcits.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.entity.*;
import com.dcits.service.OfficeSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:10
 */
@RestController
@RequestMapping("/officeSource")
public class OfficeSourceController {
    private final static Logger logger = LoggerFactory.getLogger(OfficeSourceController.class);
    @Resource
    private OfficeSourceService officeSourceService;

    /***
     * 查询工具列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findShareToolDataByPage" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult findShareToolDataByPage(@RequestBody String jsonString){
        try {
            JSONObject jsonObject =  JSONObject.parseObject(jsonString);
            String pageNumber = (String) jsonObject.get("pageNumber");
            String pageSize = (String) jsonObject.get("pageSize");
            DataResult dataResult = officeSourceService.findShareToolDataByPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
            logger.info("分页查询share_tool信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e){
            logger.error("分页查询share_tool信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }

    /***
     * 获取ppt模板列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findPPTemplateDataByPage" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult findPPTemplateDataByPage(@RequestBody String jsonString){
        try {
            JSONObject jsonObject =  JSONObject.parseObject(jsonString);
            String pageNumber = (String) jsonObject.get("pageNumber");
            String pageSize = (String) jsonObject.get("pageSize");
            DataResult dataResult = officeSourceService.findPPTemplateDataByPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
            logger.info("分页查询ppt_template信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e){
            logger.error("分页查询ppt_template信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }

    /***
     * 获取word模板列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findWordTemplateDataByPage" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult findWordTemplateDataByPage(@RequestBody String jsonString){
        try {
            JSONObject jsonObject =  JSONObject.parseObject(jsonString);
            String pageNumber = (String) jsonObject.get("pageNumber");
            String pageSize = (String) jsonObject.get("pageSize");
            DataResult dataResult = officeSourceService.findWordTemplateDataByPage(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
            logger.info("分页查询word_template信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e){
            logger.error("分页查询word_template信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }

    /***
     * 文件上传(ShareTool&WordTemplate)
     * @param sourceFile
     * @param author
     * @param name
     * @param type
     * @param describe
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult uploadFile(@RequestParam("uploadFile") final MultipartFile[] sourceFile, final String author,
            final String name,final String type ,final String describe)
    {
        Map<String,Object> mapInfo = new HashMap<String,Object>(){
            {
                put("sourceFile",sourceFile);
                put("name",name);
                put("describe",describe);
                put("type",type);
                put("author",author);
            }
        };
        String targetFilePath = "";
        try {
            if(!mapInfo.isEmpty()) {
                targetFilePath = officeSourceService.doUploadFile(mapInfo);
            }
            logger.info("文件上传信息====成功");
            DataResult dataResult = null;
            switch (Integer.parseInt(type)){
                case 3:
                    dataResult = officeSourceService.findShareToolDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
                    break;
                case 2:
                    dataResult = officeSourceService.findWordTemplateDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
                    break;
                case 1:
                    dataResult = officeSourceService.findPPTemplateDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
                    break;
            }
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).url(targetFilePath).dataResult(dataResult).build();
        }catch (Exception e){
            logger.error("文件上传信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }

    /***
     * 根据文件的全路径地址进行下载
     * @param request
     * @param response
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/download", method =  {RequestMethod.POST},produces = "application/json")
    public RequestResult downloadFile( HttpServletRequest request,HttpServletResponse response, @RequestBody String jsonString ){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String filePath = jsonObject.getString("filePath");
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.length());
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        try{
            // 如果文件名存在，则进行下载
            if (file.exists()) {
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                // 方法2： 设置下载的文件的名称-该方式已解决中文乱码问题，swagger,postman看到的是%...等，浏览器直接输url,OK（
                // 把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。）
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
                // 方法3：设置下载的文件的名称-该方式已解决中文乱码问题，postman可以，，swagger看到的是%...等，浏览器直接输url,OK
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";filename*=utf-8''"
                        + URLEncoder.encode(fileName, "UTF-8"));
                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    logger.info("Download the song successfully!");
                }
                catch (Exception e) {
                    logger.error("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            logger.info("下载信息====成功");
            return RequestResult.builder().code(RequestResult.SUCCESS_GETS_CODE).message(RequestResult.SUCCESS_GETS_MSG).build();
        }catch (Exception e){
            logger.error("下载信息====失败",e);
            return RequestResult.builder().code(RequestResult.ERROR_CODE).message(RequestResult.ERROR_MSG).build();
        }
    }

    /***
     * ShareTool根据ID删除
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteShareToolById" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult deleteShareToolById(@RequestBody String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        try{
            Integer id = (Integer)jsonObject.get("id");
            officeSourceService.deleteShareToolById(id);
            logger.info("share_tool删除信息====成功");
            DataResult dataResult = officeSourceService.findShareToolDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
            logger.info("分页查询share_tool信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e ){
            logger.error("分页查询share_tool信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }

    /***
     * PPTemplate根据ID删除
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletePPTemplateById" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult deletePPTemplateById(@RequestBody String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        try{
            Integer id = (Integer)jsonObject.get("id");
            officeSourceService.deletePPTemplateById(id);
            logger.info("PPTemplate删除信息====成功");
            DataResult dataResult = officeSourceService.findPPTemplateDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
            logger.info("分页查询PPTemplate信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e ){
            logger.error("分页查询PPTemplate信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }

    }

    /***
     * WordTemplate根据ID删除
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteWordTemplateById" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult deleteWordTemplateById(@RequestBody String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        try{
            Integer id = (Integer)jsonObject.get("id");
            officeSourceService.deleteWordTemplateById(id);
            logger.info("WordTemplate删除信息====成功");
            DataResult dataResult = officeSourceService.findWordTemplateDataByPage(Integer.parseInt("1"),Integer.parseInt("5"));
            logger.info("分页查询WordTemplate信息====成功");
            return PaginatedResult.builder().code(PaginatedResult.SUCCESS_GETS_CODE).message(PaginatedResult.SUCCESS_GETS_MSG).dataResult(dataResult).build();
        }catch (Exception e ){
            logger.error("分页查询WordTemplate信息====失败",e);
            return PaginatedResult.builder().code(PaginatedResult.ERROR_CODE).message(PaginatedResult.ERROR_MSG).build();
        }
    }
}
