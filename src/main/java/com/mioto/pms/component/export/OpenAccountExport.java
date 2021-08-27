package com.mioto.pms.component.export;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.room.model.RoomListVO;

import java.util.List;

/**
 * 开户导出
 * @author admin
 * @date 2021-08-19 11:47
 */
public class OpenAccountExport extends AbstractExport{

    static {
        EXCEL_HEADER.add("区域");
        EXCEL_HEADER.add("电表");
        EXCEL_HEADER.add("水表");
        EXCEL_HEADER.add("开户时间");
    }

    public OpenAccountExport(String fileName) {
        super(fileName);
    }

    @Override
    protected void writeHead() {
        writer.setColumnWidth(0,30);
        writer.setColumnWidth(1,20);
        writer.setColumnWidth(2,20);
        writer.setColumnWidth(3,26);
        writer.writeHeadRow(EXCEL_HEADER);
    }

    @Override
    protected void writeContent(List list) {
        if (ObjectUtil.isNotEmpty(list)){
            List<RoomListVO> roomListVOList = list;
            int x;
            int y = 1;
            for (RoomListVO roomListVO : roomListVOList) {
                x = 0;
                writer.writeCellValue(x++,y,roomListVO.getAddress()+roomListVO.getRoomName());
                writer.writeCellValue(x++,y,roomListVO.getElect());
                writer.writeCellValue(x++,y,roomListVO.getWater());
                writer.writeCellValue(x++,y, DateUtil.format(roomListVO.getCreateTime(), DatePattern.NORM_DATETIME_PATTERN));
                y++;
            }
        }
    }
}
