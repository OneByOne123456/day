package com.dp.common;


public class AjaxResult {
	private Integer code;
    private String msg;
    private Object data;
    
    
    
    public AjaxResult() {
    	code=1;
    }
    public AjaxResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = "";
    }
    public AjaxResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static AjaxResult success() {
        return new AjaxResult(200, "success", null);
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(200, "success", data);
    }

    public static AjaxResult error() {
        return new AjaxResult(500,"error", null);
    }

    public static AjaxResult error(Object data) {
        return new AjaxResult(500,"error", data);
    }
 

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
