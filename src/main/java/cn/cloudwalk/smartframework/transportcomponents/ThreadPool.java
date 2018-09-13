package cn.cloudwalk.smartframework.transportcomponents;


import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

import java.util.concurrent.Executor;

/**
 * 线程池
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public interface ThreadPool {

    /**
     * 获取线程池实例
     *
     * @param transportContext 协议上下文
     * @return Executor
     */
    Executor newExecutor(TransportContext transportContext);

}
