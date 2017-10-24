package com.example.user.uyari;

import org.json.JSONObject;

public class CustomResponse {

    public String message;
    public int status_code;

    public CustomResponse(String message, int status_code) {
        this.message = message;
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
