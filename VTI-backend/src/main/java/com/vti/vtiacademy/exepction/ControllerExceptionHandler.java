package com.vti.vtiacademy.exepction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice // tuong duong try o lop controller
public class ControllerExceptionHandler {
    @Autowired
    private HttpServletRequest servletRequest;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException> catchExceptionCustom(CustomException exception){
        exception.setPath(servletRequest.getRequestURI());
        return ResponseEntity.status(exception.getStatus())
                .body(exception);
    }

    // Mehtod bắt lỗi validate
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomException> handleBindException(BindException e, HttpServletRequest request) {
        String errorMessage = "";
        if (e.getBindingResult().hasErrors()){
            for(int i=0;i< e.getBindingResult().getAllErrors().size();i++){
                errorMessage += e.getBindingResult().getAllErrors().get(i).getDefaultMessage();
                errorMessage += (i==e.getBindingResult().getAllErrors().size()-1) ? "." : ", ";
            }
        }
        CustomException customException= new CustomException();
        customException.setStatus(400);
        customException.setMessage(errorMessage);
        customException.setTimestamp(LocalDateTime.now());
        customException.setPath(servletRequest.getRequestURI());
        return new ResponseEntity<>(customException, HttpStatus.valueOf(customException.getStatus()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> catchExceptionGlobal(Exception exception){
        CustomException customException = new CustomException();
        customException.setStatus(500);
        customException.setMessage(exception.getMessage());
        customException.setTimestamp(LocalDateTime.now());
        customException.setPath(servletRequest.getRequestURI());
        return ResponseEntity.status(500)
                .body(customException);
    }
}
