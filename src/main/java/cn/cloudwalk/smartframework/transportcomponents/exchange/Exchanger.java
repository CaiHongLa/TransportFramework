package cn.cloudwalk.smartframework.transportcomponents.exchange;

import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

/**
 * 数据交换层解耦接口，与传输层对接解耦
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public interface Exchanger {

    /**
     * 绑定传输层
     *
     * @param transportContext 协议上下文
     * @param handler          Handler
     * @return ExchangeServer
     */
    ExchangeServer bind(TransportContext transportContext, ExchangeHandler handler);

    /**
     * 与传输层建立连接
     *
     * @param transportContext 协议上下文
     * @param handler          Handler
     * @return ExchangeClient
     */
    ExchangeClient connect(TransportContext transportContext, ExchangeHandler handler);

}