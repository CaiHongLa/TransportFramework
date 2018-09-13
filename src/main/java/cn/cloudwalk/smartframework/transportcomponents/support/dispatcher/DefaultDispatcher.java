package cn.cloudwalk.smartframework.transportcomponents.support.dispatcher;

import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.Dispatcher;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

/**
 * DefaultDispatcher 默认的handler分发器
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public class DefaultDispatcher implements Dispatcher {

    @Override
    public ChannelHandler dispatch(TransportContext transportContext, ChannelHandler handler) {
        return new DefaultChannelHandler(transportContext, handler);
    }
}
