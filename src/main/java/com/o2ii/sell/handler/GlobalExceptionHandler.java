package com.o2ii.sell.handler;

import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.exception.StatusCodeException;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * GlobalException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = GlobalException.class)
    public ResponseData globalExceptionHandler(GlobalException e) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(e.getCode());
        responseData.setMsg(e.getMessage());
        return responseData;
    }

    /**
     * 特定状态码异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = StatusCodeException.class)
    public void statusCodeExceptionHandler() {}

    /**
     * body参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("【请求入参错误】MethodArgumentNotValidException");
        ResponseData responseData = new ResponseData();
        responseData.setCode(BusinessEnum.PARAM_ERROR.getCode());
        responseData.setMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return responseData;
    }

    /**
     * form表单参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseData bindExceptionHandler(BindException e) {
        log.error("【请求入参错误】BindException");
        ResponseData responseData = new ResponseData();
        responseData.setCode(BusinessEnum.PARAM_ERROR.getCode());
        responseData.setMsg(e.getFieldError().getDefaultMessage());
        return responseData;
    }

    /**
     * queryString参数异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseData missingUrlParameterExceptionHandler(MissingServletRequestParameterException e) {
        System.out.println(e.toString());
        System.out.println(e instanceof Exception);
        System.out.println(e instanceof MissingServletRequestParameterException);
        return ResponseDataUtil.generatorMsg(e.getMessage());
    }
}
