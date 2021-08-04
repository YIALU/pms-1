package com.mioto.pms.exception;


import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;

/**
 * @author mioto-qinxj
 * @date 2020/4/23
 */
public class BasicException extends RuntimeException {
    private SystemTip systemTip;

    public BasicException(SystemTip systemTip) {
        this.systemTip = systemTip;
    }

    public ResultData toResultData() {
        return ResultData.result(systemTip);
    }

    @Override
    public String toString() {
        return "BasicException{" + systemTip +'}';
    }
}
