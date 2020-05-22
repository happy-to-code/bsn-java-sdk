package chain.tj.common.response;

import chain.tj.common.StatusCode;
import cn.hutool.core.date.DateUtil;

import java.util.HashMap;

/**
 * ResponseBody构造器。
 *
 * @author zhangyifei
 */
@SuppressWarnings("serial")
public class RestResponse extends HashMap<String, Object> {

    public static RestResponse success() {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(StatusCode.RESULT_OK.value());
        restResponse.setServerTime();
        restResponse.setMessage("成功");
        return restResponse;
    }

    public static RestResponse success(String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(StatusCode.RESULT_OK.value());
        restResponse.setServerTime();
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse failure(String message, Integer code) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(code);
        restResponse.setServerTime();
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse failure(RestResponse restResponse) {
        return restResponse;
    }

    public RestResponse setSuccess(Boolean success) {
        if (success != null) {
            put("success", success);
        }
        return this;
    }

    public RestResponse setMessage(String message) {
        if (message != null) {
            put("message", message);
        }
        return this;
    }

    public RestResponse setCode(Integer code) {
        if (code != null) {
            put("code", code);
        }
        return this;
    }

    public Integer getCode() {
        Integer code = 0;
        if (!(this.get("code") == null)) {
            code = Integer.valueOf(this.get("code").toString());
        }
        return code;
    }

    public RestResponse setData(Object data) {
        if (data != null) {
            put("data", data);
        }
        return this;
    }

    public RestResponse setServerTime() {
        put("serverTime", DateUtil.now());
        return this;
    }

}
