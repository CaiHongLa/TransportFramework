package cn.cloudwalk.smartframework.transportcomponents.support.dispatcher;

import cn.cloudwalk.smartframework.transportcomponents.Channel;
import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.support.WrappedChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportException;

/**
 * MessageChannelHandler
 *
 * @author LIYANHUI
 * @date 2018/1/14
 * @since 1.0.0
 */
public class MessageChannelHandler extends WrappedChannelHandler {

    public MessageChannelHandler(TransportContext transportContext, ChannelHandler handler) {
        super(transportContext, handler);
    }

    @Override
    public void connected(Channel channel) throws TransportException {
        handler.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws TransportException {
        handler.disconnected(channel);
    }

    @Override
    public void received(Channel channel, Object message) throws TransportException {
        handler.received(channel, message);
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws TransportException {
        handler.caught(channel, exception);
    }

}