package com.o2ii.sell.util;

import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.result.ResponseData;

public class ResponseDataUtil {

    public static ResponseData success() {
        return new ResponseData(BasicEnum.SUCCESS);
    }

    public static ResponseData success(Object data) {
        return new ResponseData(BasicEnum.SUCCESS, data);
    }

    public static ResponseData fail() {
        return new ResponseData(BasicEnum.FAIL);
    }

    public static ResponseData fail(BasicEnum basicEnum) {
        return new ResponseData(basicEnum.getCode(), basicEnum.getMsg());
    }

    public static ResponseData fail(BusinessEnum businessEnum) {
        return new ResponseData(businessEnum.getCode(), businessEnum.getMsg());
    }

    public static ResponseData generatorMsg(String msg) {
        return new ResponseData(BusinessEnum.PARAM_ERROR.getCode(), msg);
    }
}
