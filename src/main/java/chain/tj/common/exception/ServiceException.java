package chain.tj.common.exception;


import chain.tj.common.StatusCode;

/**
 * @author zhangyifei
 * @version 1.0
 * @date 2020/5/9
 **/
@lombok.Getter
public class ServiceException extends RuntimeException {

    private Integer code = StatusCode.SERVER_520000.value();

    private Exception e;

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Exception e) {
        super(msg);
        this.e = e;
    }

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(Integer code, String msg, Exception e) {
        super(msg);
        this.code = code;
        this.e = e;
    }
}
