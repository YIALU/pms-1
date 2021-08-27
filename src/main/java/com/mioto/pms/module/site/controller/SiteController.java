package com.mioto.pms.module.site.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.component.export.ExcelExportFactory;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.site.model.Site;
import com.mioto.pms.module.site.model.SiteDTO;
import com.mioto.pms.module.site.service.SiteService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
@RestController
@RequestMapping("site")
@Api(tags = "区域信息")
public class SiteController {
    @Resource
    private SiteService siteService ;

    /**
    * 查询区域信息
    */
    @GetMapping
    @ApiOperation(value="查询区域信息")
    public ResultData list (Site site){
        return ResultData.success(siteService.findList(site));
    }

    /**
    * 分页查询区域信息
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询区域信息")
    public ResultData pager (Site site, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<SiteDTO> list = siteService.findList(site);
        PageInfo<SiteDTO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @GetMapping("/export")
    @ApiOperation(value="导出区域信息")
    public void export (Site site, HttpServletResponse response){
        List<SiteDTO> list = siteService.findList(site);
        ExcelExportFactory.create(ExcelExportFactory.EXPORT_SITE).writeExcel(list,response);
    }

    /**
    * 通过id查询区域信息
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询区域信息")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(siteService.findDetail(id));
    }

    /**
    * 新增区域信息
    */
    @PostMapping
    @ApiOperation(value="新增区域信息")
    public ResultData add (Site site){
        return Optional.of(siteService.insertIgnoreNull(site)).filter(count -> count > 0)
        .map(count -> ResultData.success(site)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增区域信息
    */
    @PutMapping
    @ApiOperation(value="更新新增区域信息")
    public ResultData update (Site site){
        return Optional.of(siteService.updateIgnoreNull(site)).filter(count -> count > 0)
        .map(count -> ResultData.success(site)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除区域信息
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除区域信息")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(siteService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除区域信息
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除区域信息")
    public ResultData batchDelete (String... ids){
        return Optional.of(siteService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }


    @PostMapping("/import")
    @ApiOperation(value = "导入区域管理信息")
    public ResultData importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> list = reader.readAll();
        return Optional.of(siteService.importExcel(list)).filter(count -> count > 0)
                .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.IMPORT_ERROR));
    }
}
