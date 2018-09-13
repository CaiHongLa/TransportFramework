package cn.cloudwalk.smartframework.transportcomponents.support.dispatcher;

import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.Dispatcher;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

/**
 * 只将接收消息分发到业务线程池，其余在IO线程处理
 *
 * @author LIYANHUI
 * @date 2018/1/14
 * @since 1.0.0
 */
public class MessageDispatcher implements Dispatcher {

    @Override
    public ChannelHandler dispatch(TransportContext transportContext, ChannelHandler handler) {
        return new MessageChannelHandler(transportContext, handler);
    }
}
