package com.liangrui.hadoop_disk.handler;



import com.liangrui.hadoop_disk.bean.model.GlobalExceptionInfoModel;
import com.liangrui.hadoop_disk.exception.ObjectAlreadyExistException;
import com.liangrui.hadoop_disk.exception.ObjectAlreadyInUseException;
import com.liangrui.hadoop_disk.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public GlobalExceptionInfoModel<String> exception(HttpServletRequest request, Exception e) {

        e.printStackTrace();

        return GlobalExceptionInfoModel.<String>builder()
                .code(1)
                .msg(e.getMessage())
                .data(e.getClass().getSimpleName())
                .uri(request.getRequestURI()).build();
    }


    @ExceptionHandler({ObjectAlreadyExistException.class, ObjectNotFoundException.class,
            ObjectAlreadyInUseException.class})
    @ResponseStatus(HttpStatus.OK)
    public GlobalExceptionInfoModel<String> exceptionForService(HttpServletRequest request, RuntimeException e) {
        e.printStackTrace();

        return GlobalExceptionInfoModel.<String>builder()
                .code(1)
                .msg(e.getMessage())
                .data(e.getClass().getSimpleName())
                .uri(request.getRequestURI()).build();
    }
}
