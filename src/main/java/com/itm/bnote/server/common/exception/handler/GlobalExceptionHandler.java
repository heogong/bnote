package com.itm.bnote.server.common.exception.handler;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.exception.ExcuteiveException;
import com.itm.bnote.server.common.exception.FailUuidException;
import com.itm.bnote.server.common.exception.PrivateAddressException;
import com.itm.bnote.server.common.exception.RsaKeyException;
import com.itm.bnote.server.common.model.ExceptionResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler {

    @Autowired
    ExceptionResponseModel exceptionResponseModel;

    //  공통 exception
    @ExceptionHandler(value = Exception.class)
    public ExceptionResponseModel handleException(Exception ex) {
        log.debug(ex.getMessage());
        log.error("ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(CommonValue.ERROR_EXCEPTION_MESSAGE);
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_EXCEPTION_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }

    // bind exception
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ExceptionResponseModel handleBindException(org.springframework.validation.BindException ex) {
        log.debug(ex.getMessage());
        log.error("handleBindException ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE);
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_CHECK_COMMONREQUEST_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }

    @ExceptionHandler(FailUuidException.class)
    public ExceptionResponseModel handelFailUuidHandler(FailUuidException ex) {
        log.debug(ex.getMessage());
        log.error("handelFailUuidHandler ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(CommonValue.ERROR_UUID_MESSAGE);
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_UUID_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }

    @ExceptionHandler(value = ExcuteiveException.class)
    public ExceptionResponseModel handelExcuteiveHandler(ExcuteiveException ex) {
        log.debug(ex.getMessage());
        log.error("handelExcuteiveHandler ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(CommonValue.ERROR_EXCUTEIVE_NULL_MESSAGE);
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_EXCUTEIVE_NULL_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }

    @ExceptionHandler(RsaKeyException.class)
    public ExceptionResponseModel handelRsaKeyHandler(RsaKeyException ex) {
        log.debug(ex.getMessage());
        log.error("handelRsaKeyHandler ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(CommonValue.ERROR_RSA_MESSAGE);
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_RSA_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }

    @ExceptionHandler(PrivateAddressException.class)
    public ExceptionResponseModel handelPrivateAddressHandler(PrivateAddressException ex) {
        log.debug(ex.getMessage());
        log.error("handelRsaKeyHandler ERROR - ", ex);

        exceptionResponseModel.setReturnMessage(ex.getMessage());
        exceptionResponseModel.setReturnCode(CommonValue.ERROR_EXCEPTION_CODE);
        exceptionResponseModel.setReturnDetailMessage(ex.toString());

        return exceptionResponseModel;
    }
}
