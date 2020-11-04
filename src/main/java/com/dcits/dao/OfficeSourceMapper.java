package com.dcits.dao;

import com.dcits.entity.PPTemplate;
import com.dcits.entity.ShareTool;
import com.dcits.entity.WordTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 *  @author nanmiaoa
 *  @data 2020/10/27 9:30
 */
@Mapper
public interface OfficeSourceMapper {
    @Select("select * from share_tool")
    List<ShareTool> findShareToolDataByPage();
    @Select("select * from ppt_template")
    List<ShareTool> findPPTemplateDataByPage();
    @Select("select * from word_template")
    List<ShareTool> findWordTemplateDataByPage();
    /**保存上传压缩包的路径*/
    void insertPathForUpload_ShareTool(ShareTool shareTool);
    void insertPathForUpload_WordTemplate(WordTemplate wordTemplate);
    void insertPathForUpload_PPTemplate(PPTemplate wordTemplate);

    void deleteShareToolById(ShareTool shareTool);
    void deletePPTemplateById(ShareTool shareTool);
    void deleteWordTemplateById(ShareTool shareTool);


}
