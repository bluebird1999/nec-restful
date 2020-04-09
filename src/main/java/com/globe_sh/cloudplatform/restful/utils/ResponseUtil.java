package com.globe_sh.cloudplatform.restful.utils;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @program: jccp_webserver
 * @Date: 2020/3/13 14:34
 * @Author: zyoung
 * @Description:
 */
public class ResponseUtil {

    public static JSONObject success(Object data) {
        JSONObject obj = new JSONObject();
        obj.put("status", 200);
        obj.put("message", "成功");
        obj.put("data", data);
        return obj;
    }

    public static JSONObject success() {
    	JSONObject obj = new JSONObject();
        obj.put("status", 200);
        obj.put("message", "成功");
        return obj;
    }

    public static JSONObject failureMore(int status, String msg, Object data) {
    	JSONObject obj = new JSONObject();
        obj.put("status", status);
        obj.put("message", msg);
        obj.put("data", data);
        return obj;
    }
    
    public static JSONObject failure(int status, String msg) {
    	JSONObject obj = new JSONObject();
        obj.put("status", status);
        obj.put("message", msg);
        return obj;
    }

    public static JSONObject badArgument() {
        return failure(401, "参数不对");
    }

    public static JSONObject badArgumentValue() {
        return failure(402, "参数值不对");
    }

    public static JSONObject unRegisterMobile() {
        return failure(507, "手机号码未注册");
    }

    public static JSONObject tokenExpire() {
        return failure(508, "会话已过期");
    }

    public static JSONObject unlogin() {
        return failure(501, "请登录");
    }

    public static JSONObject serious() {
        return failure(502, "系统内部错误");
    }

    public static JSONObject unsupport() {
        return failure(503, "业务不支持");
    }

    public static JSONObject updatedDateExpired() {
        return failure(504, "更新数据已经失效");
    }

    public static JSONObject updatedDataFailed() {
        return failure(505, "更新数据失败");
    }

    public static JSONObject unauthz() {
        return failure(506, "无操作权限");
    }

}
