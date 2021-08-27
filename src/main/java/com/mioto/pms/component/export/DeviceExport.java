package com.mioto.pms.component.export;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.device.model.DeviceDTO;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-19 14:21
 */
public class DeviceExport extends AbstractExport{
    static {
        EXCEL_HEADER.add("设备名称");
        EXCEL_HEADER.add("设备id");
        EXCEL_HEADER.add("集中器id");
        EXCEL_HEADER.add("关联房间");
    }

    public DeviceExport(String fileName) {
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
            List<DeviceDTO> deviceDTOList = list;
            int x;
            int y = 1;
            for (DeviceDTO deviceDTO : deviceDTOList) {
                x = 0;
                writer.writeCellValue(x++,y,deviceDTO.getName());
                writer.writeCellValue(x++,y,deviceDTO.getDeviceId());
                writer.writeCellValue(x++,y,deviceDTO.getFocus());
                writer.writeCellValue(x++,y,deviceDTO.getRoomName());
                y++;
            }
        }
    }
}
