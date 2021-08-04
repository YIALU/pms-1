package com.mioto.pms.exception;

import cn.hutool.core.exceptions.ValidateException;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author mioto-qinxj
 * @date 2020/4/23
 */
@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

    @ExceptionHandler
    public void handler(Exception e, HandlerMethod handlerMethod, HttpServletResponse response) {
        writeResultData(ResultData.result(SystemTip.SYSTEM_ERROR,e.getMessage()),response);
        log.error("{} method name - {}  handler error", handlerMethod.getBeanType(), handlerMethod.getMethod().getName(), e);
    }

    /**
     * 系统自定义异常
     * @param e
     * @param response
     */
    @ExceptionHandler({BasicException.class})
    public void basicExceptionHandler(BasicException e,  HttpServletResponse response) {
        writeResultData(e.toResultData(),response);
    }

    /**
     * hutool字段验证失败异常 -- cn.hutool.core.lang.Validator
     * @param response
     */
    @ExceptionHandler({ValidateException.class})
    public void validateExceptionHandler(ValidateException e,  HttpServletResponse response) {
        writeResultData(ResultData.result(SystemTip.PARAM_VALID_FAIL,e.getMessage()),response);
    }

    /**
     * 使用hibernate注解做参数验证时异常
     * @param response
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void bindExceptionHandler(MethodArgumentNotValidException e,  HttpServletResponse response) {
        String errorMessage = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining());
        writeResultData(ResultData.result(SystemTip.PARAM_VALID_FAIL,errorMessage),response);
    }

    /**
     * 必传参数异常 -- @RequestParam
     * @param e
     * @param response
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public void requestParameterExceptionHandler(MissingServletRequestParameterException e,HttpServletResponse response) {
        writeResultData(ResultData.result(SystemTip.PARAM_VALID_FAIL,e.getParameterName() + "不能为空"),response);
    }
    /**
     * 数据库字段唯一约束 -- @RequestParam
     * @param duplicateKeyException
     * @param response
     */
    @ExceptionHandler({DuplicateKeyException.class})
    public void duplicateKeyExceptionHandler(DuplicateKeyException duplicateKeyException,HttpServletResponse response){
        writeResultData(ResultData.result(SystemTip.UNIQUE_VALID_FAIL,duplicateKeyException.getLocalizedMessage()),response);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public void duplicateKeyExceptionHandler(HttpRequestMethodNotSupportedException methodNotSupportedException,HttpServletResponse response){
        writeResultData(ResultData.result(SystemTip.METHOD_NOT_SUPPORTED,methodNotSupportedException.getLocalizedMessage()),response);
    }

    private void writeResultData(ResultData resultData,HttpServletResponse response){
        ResultUtil.responseJson(response, resultData);
    }
}