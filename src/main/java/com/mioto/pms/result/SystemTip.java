package com.mioto.pms.result;

/**
 * @author mioto-qinxj
 * @description
 * @date 2020/3/24
 */
public enum SystemTip {
    OK("10000", "success"),
    //全局错误
    INSERT_FAIL("10001", "新增失败"),
    UPDATE_FAIL("10002", "更新失败"),
    DELETE_FAIL("10003", "删除失败"),
    PARAM_VALID_FAIL("10004", "参数验证失败"),
    SYSTEM_ERROR("10005", "系统错误"),
    UNIQUE_VALID_FAIL("10006", "违反唯一约束"),
    METHOD_NOT_SUPPORTED("10007", "错误的方法调用"),
    //token验证
    TOKEN_EXPIRED("10100","token过期"),
    TOKEN_VERIFY("10101","token校验失败"),
    //认证
    UNAUTHORIZED("11001", "未授权"),
    NOT_LOGGED_IN("11002", "未登录"),
    LOGIN_FAILURE("11003", "登录失败"),
    USERNAME_PASSWORD_ERROR("11004", "用户名或密码错误"),
    USER_LOCKED("11005", "用户被冻结"),
    USER_DISABLED("11007", "用户被禁用"),
    MINI_USER_NOT_EXIST("11008", "小程序用户不存在"),

    //文件上传下载
    FILE_NOT_EXIST("12000","文件不存在"),
    FILE_UPLOAD_FAIL("12001","文件上传失败"),
    //文件导入
    IMPORT_ERROR("12002","导入数据失败"),
    //用戶
    USERNAME_NOT_EMPTY("13000","用户名不能为空"),
    PASSWORD_ERROR("13001","密码错误"),
    USER_NAME_REUSE("13002","账号已存在"),
    PHONE_REUSE ("13003","手机号重复"),

    BILL_NUMBER_NOT_EMPTY("14000","账单编号不能为空"),
    BILL_NUMBER_NOT_EXIST("14001","账单编号不存在"),
    BILL_NUMBER_SEND_FAIL("14002","账单发送失败"),

    PRICE_METER_EMPTY("15000","收费策略和抄表策略不能为空"),
    METER_READ_ERROR("15001","每月只能抄表一次"),

    ZIP_DEVICE_EMPTY("16000","设备为空,打包失败"),
    EXPORT_ERROR("16001","暂不支持的导出类型"),

    ADDRESS_EMPTY("17000","省、市、区、地址不能为空"),
    PROVINCE_NOT_EXIST("17001","省不存在"),
    CITY_NOT_EXIST("17002","市不存在"),
    DISTRICT_NOT_EXIST("17003","区不存在"),
    PROVINCE_CITY_RELATION_ERROR("17004","省、市、区关系错误");

    private String code;
    private String desc;

    SystemTip(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return "SystemTip{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
