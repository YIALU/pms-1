package com.mioto.pms.component.export;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import com.mioto.pms.module.cost.model.CostListVO;
import com.mioto.pms.module.price.PricingStrategyEnum;

import java.util.Date;
import java.util.List;

/**
 * 账单导出
 * @author admin
 * @date 2021-08-18 15:26
 */
public class CostExport extends AbstractExport{

    static {
        EXCEL_HEADER.add("订单编号");
        EXCEL_HEADER.add("住户信息");
        EXCEL_HEADER.add("电话");
        EXCEL_HEADER.add("位置");
        EXCEL_HEADER.add("子订单");
        EXCEL_HEADER.add("费用类型");
        EXCEL_HEADER.add("表数据");
        EXCEL_HEADER.add("时间");
        EXCEL_HEADER.add("金额");
        EXCEL_HEADER.add("单价");
        EXCEL_HEADER.add("缴纳情况");
    }

    public CostExport(String fileName){
        super(fileName);
    }

    @Override
    protected void writeHead() {
        writer.setColumnWidth(0,25);
        writer.setColumnWidth(1,16);
        writer.setColumnWidth(2,16);
        writer.setColumnWidth(3,38);
        writer.setColumnWidth(4,36);
        writer.setColumnWidth(5,12);
        writer.setColumnWidth(6,18);
        writer.setColumnWidth(7,34);
        writer.setColumnWidth(8,12);
        writer.setColumnWidth(9,12);
        writer.setColumnWidth(10,14);
        writer.writeHeadRow(EXCEL_HEADER);
    }

    @Override
    protected void writeContent(List list) {
        if (ObjectUtil.isNotEmpty(list)){
            List<CostListVO> costListVOList =list;
            List<CostDetailListVO> costDetailListVOList;
            int costInfoSize = costListVOList.size();
            int costDetailSize = 0;
            int firstRow = 1;
            int lastRow;
            for (int i = 0; i < costInfoSize; i++) {
                int x = 0;
                costDetailListVOList = costListVOList.get(i).getCostDetailListVOList();
                if (ObjectUtil.isNotEmpty(costDetailListVOList)){
                    costDetailSize = costDetailListVOList.size();
                }
                lastRow = costDetailSize + firstRow - 1;
                writer.merge(firstRow,lastRow,x,x++,costListVOList.get(i).getBillNumber(),false);
                writer.merge(firstRow,lastRow,x,x++,costListVOList.get(i).getTenantName(),false);
                writer.merge(firstRow,lastRow,x,x++,costListVOList.get(i).getTenantPhone(),false);
                writer.merge(firstRow,lastRow,x,x++,costListVOList.get(i).getAddress(),false);
                for (int index = 0;index < costDetailSize;index++){
                    int childX = x;
                    writer.writeCellValue(childX++,firstRow + index,costDetailListVOList.get(index).getChildBillNumber());
                    writer.writeCellValue(childX++,firstRow + index,costDetailListVOList.get(index).getCostType());
                    writer.writeCellValue(childX++,firstRow + index,builderMeterData(costDetailListVOList.get(index)));
                    writer.writeCellValue(childX++,firstRow + index,builderMeterTime(costDetailListVOList.get(index).getStartDate(),costDetailListVOList.get(index).getEndDate()));
                    writer.writeCellValue(childX++,firstRow + index,builderAmount(costDetailListVOList.get(index).getAmount()));
                    writer.writeCellValue(childX++,firstRow + index,builderUnit(costDetailListVOList.get(index).getUnit(),costDetailListVOList.get(index).getCostTypeId()));
                    writer.writeCellValue(childX++,firstRow + index,builderPayStatus(costDetailListVOList.get(index).getPayStatus()));
                }
                firstRow = lastRow + 1;
            }
        }
    }

    private String builderMeterData(CostDetailListVO costDetailListVO){
        StrBuilder strBuilder = StrBuilder.create();
        if (PricingStrategyEnum.getInstance(costDetailListVO.getCostTypeId()).getType() == 2){
            int month = Integer.parseInt(StrUtil.sub(costDetailListVO.getChildBillNumber(),4,6));
            strBuilder.append(month).append("月").append(costDetailListVO.getCostType());
        }else {
            if (ObjectUtil.isNotEmpty(costDetailListVO.getStartData())) {
                if (costDetailListVO.getStartData() == 0){
                    strBuilder.append("0.00");
                }else {
                    strBuilder.append(NumberUtil.decimalFormat(".00", costDetailListVO.getStartData()));
                }
            }
            strBuilder.append("~");
            if (ObjectUtil.isNotEmpty(costDetailListVO.getEndData())) {
                strBuilder.append(NumberUtil.decimalFormat(".00",costDetailListVO.getEndData()));
            }
        }
        return strBuilder.toString();
    }

    private String builderPayStatus(int payStatus){
        if (payStatus == 0){
            return "未缴纳";
        }
        return "已缴纳";
    }

    private String builderUnit(Double unitPrice,String costTypeId){
        StrBuilder strBuilder = StrBuilder.create();
        if (ObjectUtil.isNotEmpty(unitPrice)){
            strBuilder.append(NumberUtil.decimalFormatMoney(unitPrice));
        }else {
            strBuilder.append("0.00");
        }
        return strBuilder.append("元/").append(PricingStrategyEnum.getInstance(costTypeId).getUnit()).toString();
    }

    private String builderAmount(Double amount){
        StrBuilder strBuilder = StrBuilder.create("￥");
        if (ObjectUtil.isNotEmpty(amount)){
            strBuilder.append(NumberUtil.decimalFormatMoney(amount));
        }else {
            strBuilder.append("0.00");
        }
        return strBuilder.toString();
    }

    private String builderMeterTime(Date startDate, Date endDate){
        StrBuilder strBuilder = StrBuilder.create();
        if (ObjectUtil.isNotEmpty(startDate)){
            strBuilder.append(DateUtil.format(startDate, DatePattern.CHINESE_DATE_PATTERN));
        }
        strBuilder.append("至");
        if (ObjectUtil.isNotEmpty(endDate)){
            strBuilder.append(DateUtil.format(endDate, DatePattern.CHINESE_DATE_PATTERN));
        }
        return strBuilder.toString();
    }
}
