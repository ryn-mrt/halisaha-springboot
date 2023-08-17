package com.egitim.halisaha.utility;

import models.RestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Rest {

    public static ResponseEntity success(Object object){
        RestModel restModel = new RestModel();
        restModel.setStatus(true);
        restModel.setResult(object);
        restModel.setStatusDescription("Başarılı" );
        return new ResponseEntity(restModel, HttpStatus.OK);
    }
    public static ResponseEntity fail(Object object, String errorDesc, HttpStatus httpStatus){
        RestModel restModel = new RestModel();
        restModel.setStatusDescription(errorDesc);
        restModel.setStatus(false);
        restModel.setResult(object);
        return new ResponseEntity(restModel, httpStatus);
    }
}
