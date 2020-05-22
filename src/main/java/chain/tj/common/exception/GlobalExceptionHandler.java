package chain.tj.common.exception;


import chain.tj.common.StatusCode;
import chain.tj.common.response.RestResponse;
import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.nio.file.AccessDeniedException;

/**
 * 功能描述: 异常处理类
 *
 * @author zhangyifei
 * @return
 * @date 2020/5/9 11:31
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有接口数据验证异常信息
     * <p>
     * 参数中不写BindingResult时调用
     * 自定义校验结果时请参考
     * ValidatorConfiguration类中详解
     * </p>
     *
     * @param e
     * @returns
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        //得到校验字段信息
        String[] str = e.getBindingResult().getAllErrors().get(0).getCodes()[1].split("\\.");
        //数据校验信息
        //校验字段的注解类型
        String validateType = str[0];
        //根据类型得到code
        Integer code = ValidateTypeUtils.validateType(validateType);
        StringBuffer msg = new StringBuffer(str[1] + ":");
        msg.append(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        //返回校验信息
        String message = msg.toString();
        return RestResponse.failure(message, code);
    }

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResponse handleException(Exception e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.stackTraceToString(e));

        return RestResponse.failure(e.getMessage(), StatusCode.SERVER_500000.value());
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public RestResponse handleAccessDeniedException(AccessDeniedException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.stackTraceToString(e));
        return RestResponse.failure(e.getMessage(), StatusCode.CLIENT_400001.value());
    }

    /**
     * 类型转换错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RestResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return RestResponse.failure("参数类型转换错误:" + e.getMessage(), StatusCode.CLIENT_400009.value());
    }

    /**
     * 参数不能为空异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public RestResponse missingServletRequestPartException(MissingServletRequestPartException e) {
        return RestResponse.failure("字段不能为空:" + e.getMessage(), StatusCode.CLIENT_410001.value());
    }

    /**
     * 请求类型异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return RestResponse.failure("请求类型异常:" + e.getMessage(), StatusCode.CLIENT_400005.value());
    }

    /**
     * JSON转换异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return RestResponse.failure("JSON序列化错误:" + e.getMessage(), StatusCode.CLIENT_410009.value());
    }

    /**
     * 文件大小超出系统限制大小
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    public RestResponse fileUploadBase$FileSizeLimitExceededException(FileUploadBase.FileSizeLimitExceededException e) {

        return RestResponse.failure("上传文件过大:" + e.getMessage(), StatusCode.CLIENT_410010.value());
    }


}
