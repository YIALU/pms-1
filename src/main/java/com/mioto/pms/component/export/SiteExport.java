package com.mioto.pms.component.export;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.site.model.SiteDTO;

import java.util.List;

/**
 * 区域导出
 * @author admin
 * @date 2021-08-19 12:10
 */
public class SiteExport extends AbstractExport{
    static {
        EXCEL_HEADER.add("省");
        EXCEL_HEADER.add("市");
        EXCEL_HEADER.add("区/镇");
        EXCEL_HEADER.add("详细地址");
    }

    public SiteExport(String fileName) {
        super(fileName);
    }

    @Override
    protected void writeHead() {
        writer.setColumnWidth(0,20);
        writer.setColumnWidth(1,20);
        writer.setColumnWidth(2,20);
        writer.setColumnWidth(3,32);
        writer.writeHeadRow(EXCEL_HEADER);
    }

    @Override
    protected void writeContent(List list) {
        if (ObjectUtil.isNotEmpty(list)){
            List<SiteDTO> siteDTOList = list;
            int x;
            int y = 1;
            for (SiteDTO siteDTO : siteDTOList) {
                x = 0;
                writer.writeCellValue(x++,y,siteDTO.getProvinceName());
                writer.writeCellValue(x++,y,siteDTO.getCityName());
                writer.writeCellValue(x++,y,siteDTO.getDistrictName());
                writer.writeCellValue(x++,y,siteDTO.getAddress());
                y++;
            }
        }
    }
}
