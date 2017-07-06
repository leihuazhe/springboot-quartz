package xyz.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * RespCode
 *
 * @author leihz
 * @date 2017/7/6 15:33
 */
public class RespCode extends HashMap<String, Object> implements java.io.Serializable {

    public static final String SYS_SUCCESS = "000";
    public static final String SYS_MESSAGE = "007";//返回信息
    public static final String SYS_ERROR = "777"; // 系统错误

    private String code;
    private String message;

    public RespCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.get("message")+"";
    }

    public void setMessage(String message) {
        this.message = message;
    }


    private RespCode() {
    }

    public RespCode success(String message, Object data) {
        this.put("code", SYS_SUCCESS);
        this.put("message", message);
        this.put("data", data);
        return this;
    }

    private RespCode success(String message, Object data, Integer total) {
        this.put("code", SYS_SUCCESS);
        this.put("message", message);
        this.put("data", data);
        this.put("total", total);
        return this;
    }


    public RespCode success(String message) {
        this.put("code", SYS_SUCCESS);
        this.put("message", message);
        return this;
    }

    public RespCode success(int rows, String code,String msg) {
        this.put("rows", rows);
        this.put("code", code);
        this.put("msg",msg);
        return this;
    }

    public RespCode success() {
        this.put("code", SYS_SUCCESS);
        return this;
    }

    public RespCode error(String message) {
        this.put("code", SYS_ERROR);
        this.put("message", message);
        return this;
    }

    public RespCode message(String message) {
        this.put("message", message);
        return this;
    }

    public RespCode message(String code, String message) {
        this.put("code", code);
        this.put("message", message);
        return this;
    }

    public RespCode error(int code, String message) {
        this.put("code", code);
        this.put("message", message);
        return this;
    }

    public RespCode error(String code, String message) {
        this.put("code", code);
        this.put("message", message);
        return this;
    }



    public static RespCode SUCCESS() {
        return new RespCode().success();
    }


    public static RespCode MESSAGE(String message) {
        return new RespCode().message(message);
    }
    public static RespCode MESSAGE(String code, String message) {
        return new RespCode().message(code, message);
    }


    public static RespCode SUCCESS(String message, Object data) {
        return new RespCode().success(message, data);
    }


    public static RespCode SUCCESS(String message, Object datas, Integer total) {
        return new RespCode().success(message, datas, total);
    }

    public static RespCode SUCCESS(String message) {
        return new RespCode().success(message);
    }

    public static RespCode ERROR(String message) {
        return new RespCode().error(message);
    }

    public static ResponseEntity<Object> ERROR(int code, HttpStatus status, String message) {
        return new ResponseEntity<Object>(new RespCode().error(code, message), status);
    }

    public static RespCode ERROR(String status, String message) {
        return new RespCode().error(status,message);
    }

}
