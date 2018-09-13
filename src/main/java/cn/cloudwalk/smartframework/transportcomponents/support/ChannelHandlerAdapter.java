package cn.cloudwalk.smartframework.transportcomponents.support;

import cn.cloudwalk.smartframework.transportcomponents.Channel;
import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportException;

/**
 * Handler适配器
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public class ChannelHandlerAdapter implements ChannelHandler {

    @Override
    public void connected(Channel channel) throws TransportException {
    }

    @Override
    public void disconnected(Channel channel) throws TransportException {
    }

    @Override
    public void send(Channel channel, Object message) throws TransportException {
    }

    @Override
    public void received(Channel channel, Object message) throws TransportException {
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws TransportException {
    }

    @Override
    public void close() {

    }
}
