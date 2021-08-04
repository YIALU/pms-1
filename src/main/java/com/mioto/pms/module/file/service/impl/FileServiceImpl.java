package com.mioto.pms.module.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import com.mioto.pms.module.file.dao.FileDao;
import com.mioto.pms.module.file.model.FileInfo;
import com.mioto.pms.module.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
* @auther lzc
* date 2021-03-22 15:20:48
*/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    @Value("${file.path}")
    private String filePath;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<FileInfo> findList(FileInfo file) {
        return fileDao.findList(file);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public FileInfo findByColumn(String column, String value) {
        return fileDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(FileInfo file) {
        return fileDao.insertIgnoreNull(file);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(FileInfo file) {
        return fileDao.updateIgnoreNull(file);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        FileInfo fileInfo = fileDao.findByColumn(column, value);
        if(fileInfo!=null){
            File file = new File(filePath + fileInfo.getFilePath());
            file.delete();
        }
        return fileDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return fileDao.batchDelete(ids);
    }

    @Override
    public void downloadCode(HttpServletResponse response, String[] ids) {

    List<FileInfo>  files=     fileDao.findByIds(ids);
        File file;
        if(files.size()==0){
        return;
    }else if(files.size()==1){
        file = new File(filePath + files.get(0).getFilePath());
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(file.getName()));
            ServletOutputStream outputStream = response.getOutputStream();
            FileUtil.writeToStream(file,outputStream);
            outputStream.close();
        }catch (Exception e){
            log.info("二维码下载异常");
        }
        }else {
            for (FileInfo fileInfo:files){
                 FileUtil.copy(filePath + fileInfo.getFilePath(), filePath + "/code/temp/"+fileInfo.getName(), true);
                 }
               file = ZipUtil.zip(filePath + "/code/temp/");
            try {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(file.getName()));
                ServletOutputStream outputStream = response.getOutputStream();
                FileUtil.writeToStream(file,outputStream);
                outputStream.close();
            }catch (Exception e){
                log.info("二维码下载异常");
            }finally {
                FileUtil.del(filePath+"/code/temp");
                FileUtil.del(file);
            }
    }
    }

    @Override
    public int batchInsert(List<FileInfo> fileInfos) {
        return fileDao.batchInsert(fileInfos);
    }
}
