package cn.cloudwalk.smartframework.transportcomponents;

import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportException;

/**
 * 客户端
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public interface Client extends Channel, EndPoint {

    /**
     * 重连
     *
     * @throws TransportException
     */
    void reconnect() throws TransportException;
}
