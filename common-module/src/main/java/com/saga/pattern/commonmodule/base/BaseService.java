package com.saga.pattern.commonmodule.base;

public interface BaseService<RESPONSE extends BaseResponse, REQUEST extends BaseRequest>{
    /** base service **/
    RESPONSE execute(REQUEST request);
}
