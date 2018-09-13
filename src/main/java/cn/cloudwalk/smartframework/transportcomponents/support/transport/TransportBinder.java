package cn.cloudwalk.smartframework.transportcomponents.support.transport;

import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.Client;
import cn.cloudwalk.smartframework.transportcomponents.Server;
import cn.cloudwalk.smartframework.transportcomponents.Transport;
import cn.cloudwalk.smartframework.transportcomponents.support.ChannelHandlerAdapter;
import cn.cloudwalk.smartframework.transportcomponents.support.ChannelHandlerDispatcher;

/**
 * TransportBinder
 * <p>
 * 绑定传输层，用于数据交换层与传输层之间解耦
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public class TransportBinder {

    /**
     * static调用
     */
    private TransportBinder() {
    }

    public static Server bind(TransportContext transportContext, ChannelHandler... handlers) {
        if (transportContext == null) {
            throw new IllegalArgumentException("transportContext can not be null！");
        }
        if (handlers == null || handlers.length == 0) {
            throw new IllegalArgumentException("handlers can not be null！");
        }
        ChannelHandler handler;
        if (handlers.length == 1) {
            handler = handlers[0];
        } else {
            handler = new ChannelHandlerDispatcher(handlers);
        }
        return getTransporter(transportContext).bind(transportContext, handler);
    }


    public static Client connect(TransportContext transportContext, ChannelHandler... handlers) {
        if (transportContext == null) {
            throw new IllegalArgumentException("transportContext can not be null！");
        }
        ChannelHandler handler;
        if (handlers == null || handlers.length == 0) {
            handler = new ChannelHandlerAdapter();
        } else if (handlers.length == 1) {
            handler = handlers[0];
        } else {
            handler = new ChannelHandlerDispatcher(handlers);
        }
        return getTransporter(transportContext).connect(transportContext, handler);
    }

    private static Transport getTransporter(TransportContext transportContext) {
        return transportContext.getTransport();
    }

}
