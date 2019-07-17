package com.mertcan.todolist.Models;

import java.util.ArrayList;
import java.util.List;

public class ResponseJsonModel {

    private boolean success = false;
    private String message;
    private List<Object> data = new ArrayList<Object>();


    public boolean isSuccess() {
        return success;
    }

    public ResponseJsonModel() {
    }

    public ResponseJsonModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseJsonModel(boolean success, List<Object> data) {
        this.success = success;
        this.data = data;
    }

    public ResponseJsonModel(boolean success, String message, List<Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getData() {
        return data;
    }
}
