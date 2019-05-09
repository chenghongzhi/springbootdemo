package com.example.util;

public class Result {
    private static final String OK="ok";
    private static final String ERROR="error";
    private Meta meta;
    private Object data;

    public Result success(){
        this.meta=new Meta(true,OK);
        return this;
    }

    public Result success(Object data){
        this.meta=new Meta(true,OK);
        this.data=data;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result faile(){
        this.meta=new Meta(false,ERROR);
        return this;
    }

    public Result faile(String message){
        this.meta=new Meta(false,message);
        return this;
    }

    public class Meta{
        private boolean success;
        private String message;

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public Meta(boolean success) {
            this.success=success;
        }
    }
}

