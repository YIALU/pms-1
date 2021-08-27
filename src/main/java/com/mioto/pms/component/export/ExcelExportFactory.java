package com.mioto.pms.component.export;

import com.mioto.pms.exception.BasicException;
import com.mioto.pms.result.SystemTip;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2021-08-21 14:34
 */
public class ExcelExportFactory {

    public static final String EXPORT_COST = "cost";
    public static final String EXPORT_DEVICE = "device";
    public static final String EXPORT_SITE = "site";
    public static final String EXPORT_ACCOUNT = "account";

    private static Map<String,AbstractExport> abstractExportMap = new HashMap<>(4);

    static {
        abstractExportMap.put(EXPORT_COST,new CostExport("费用账单"));
        abstractExportMap.put(EXPORT_DEVICE,new CostExport("设备信息"));
        abstractExportMap.put(EXPORT_SITE,new CostExport("区域信息"));
        abstractExportMap.put(EXPORT_ACCOUNT,new CostExport("开户配表"));
    }

    public static AbstractExport create(String exportType){
        if (abstractExportMap.containsKey(exportType)){
            return abstractExportMap.get(exportType);
        }

        throw new BasicException(SystemTip.EXPORT_ERROR);
    }
}
