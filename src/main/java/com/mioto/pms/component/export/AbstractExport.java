package com.mioto.pms.component.export;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * excel导出
 * @author admin
 * @date 2021-08-18 15:23
 */
public abstract class AbstractExport {
    protected static Set<String> EXCEL_HEADER = new LinkedHashSet<>();

    protected String fileName;

    protected ExcelWriter writer = ExcelUtil.getWriter(true);

    protected AbstractExport(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 写入excel
     * @param list
     */
    public void writeExcel(List list,HttpServletResponse response){
        writeHead();
        writeContent(list);
        write(response);
    }

    /**
     * 写入标题
     */
    protected abstract void writeHead();

    /**
     * 写入文件内容
     * @param list
     */
    protected abstract void writeContent(List list);

    /**
     * 写入输出流
     * @param response
     */
    private void write(HttpServletResponse response) {
        ServletOutputStream outStream = getOutStream(response);
        writer.flush(outStream,true);
        writer.close();
        IoUtil.close(outStream);
    }

    /**
     * 获取文件输出流
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    private ServletOutputStream getOutStream(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") +".xlsx");
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

}
