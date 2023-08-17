package models;

import lombok.Data;

@Data
public class RestModel {
    private boolean status;
    private String statusDescription;
    private Object result;

}
