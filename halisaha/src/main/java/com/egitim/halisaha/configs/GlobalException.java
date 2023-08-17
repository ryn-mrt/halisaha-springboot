package com.egitim.halisaha.configs;


import com.egitim.halisaha.utility.Rest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid(MethodArgumentNotValidException ex ) {
        return Rest.fail(ex.getFieldErrors(),"VALIDATION ERR", HttpStatus.BAD_REQUEST);
    }

}
