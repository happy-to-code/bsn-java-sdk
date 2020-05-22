package chain.tj.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhangyifei
 * @version 1.0
 * @descrition
 * @date 2019/5/9
 **/
@Aspect
@Component
@Slf4j
public class ServiceExceptionAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    private void servicePointcut() {
    }

    /**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e 异常对象
     */
    @AfterThrowing(pointcut = "servicePointcut()", throwing = "e")
    public void handleThrowing(Exception e) throws Exception {
        if (e instanceof ServiceException) {
            throw new ServiceException(((ServiceException) e).getCode(), e.getMessage());
        } else {
            throw e;
        }
    }

}
