package com.mioto.pms.module.file.controller;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.file.FileHelper;
import com.mioto.pms.module.file.model.FileInfo;
import com.mioto.pms.module.file.service.FileService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
* @auther csl
* date 2021-03-22 15:20:48
*/
@RestController
@RequestMapping("file")
@Api(tags = "文件")
public class FileController {
    @Resource
    private FileService fileService ;
    @Resource
    private FileHelper fileHelper;

    /**
    * 查询文件
    */
    @GetMapping
    @ApiOperation(value="查询文件")
    public ResultData list (FileInfo file){
        return ResultData.success(fileService.findList(file));
    }

    /**
    * 分页查询文件
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询文件")
    public ResultData pager (FileInfo file, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<FileInfo> list = fileService.findList(file);
        PageInfo<FileInfo> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询文件
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询文件")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(fileService.findByColumn("id",id));
    }

    /**
    * 新增文件
    */
    @PostMapping
    @ApiOperation(value="新增文件")
    public ResultData add (@RequestBody FileInfo file){
        return Optional.of(fileService.insertIgnoreNull(file)).filter(count -> count > 0)
        .map(count -> ResultData.success(file)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增文件
    */
    @PutMapping
    @ApiOperation(value="更新新增文件")
    public ResultData update (FileInfo file){
        return Optional.of(fileService.updateIgnoreNull(file)).filter(count -> count > 0)
        .map(count -> ResultData.success(file)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除文件
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除文件")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(fileService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除文件
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除文件")
    public ResultData batchDelete (String... ids){
        return Optional.of(fileService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    @GetMapping("/code")
    @ApiOperation(value = "二维码下载",produces="application/octet-stream")
    public void downloadCode(HttpServletResponse response,String[] ids){
        fileService.downloadCode(response,ids);
    }


    @PostMapping("/upload")
    @ApiOperation(value="文件上传")
    public synchronized ResultData upload (@RequestPart("files") MultipartFile[] files){
        List<FileInfo> fileInfos;
        try {
            fileInfos = new ArrayList<>(files.length);
            for (MultipartFile file : files) {
                //文件存储路径+文件名
                String realPath = StrBuilder.create()
                        .append(fileHelper.createDirPath())
                        .append(StrUtil.SLASH)
                        .append(fileHelper.createNewFileName(file.getOriginalFilename()))
                        .toString();
                file.transferTo(new File(realPath));
                fileInfos.add(fileHelper.buildFileInfo(realPath,file.getOriginalFilename()));
            }
            fileService.batchInsert(fileInfos);
        }catch (Exception exception){
            return ResultData.result(SystemTip.FILE_UPLOAD_FAIL);
        }
        return ResultData.success(fileInfos);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download/{id}")
    @ApiOperation(value="文件下载")
    public void download (@PathVariable("id") String id, HttpServletResponse response){
        FileInfo fileInfo = fileService.findByColumn("id",id);
        Optional.ofNullable(fileInfo).orElseThrow(() -> new BasicException(SystemTip.FILE_NOT_EXIST));
        fileHelper.download(response,fileInfo);
    }
}
