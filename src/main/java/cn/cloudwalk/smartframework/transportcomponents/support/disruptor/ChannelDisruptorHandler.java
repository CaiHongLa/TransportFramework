package cn.cloudwalk.smartframework.transportcomponents.support.disruptor;

import cn.cloudwalk.smartframework.transportcomponents.Channel;
import cn.cloudwalk.smartframework.transportcomponents.ChannelHandler;
import cn.cloudwalk.smartframework.transportcomponents.ChannelHandlerDelegate;
import cn.cloudwalk.smartframework.transportcomponents.support.NamedThreadFactory;
import cn.cloudwalk.smartframework.transportcomponents.support.ProtocolConstants;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportException;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;

/**
 * 传输层消息Disruptor无锁队列实现
 *
 * @author liyanhui@cloudwalk.cn
 * @date 2018/4/28 16:43
 * @since 2.0.0
 */
public class ChannelDisruptorHandler implements ChannelHandlerDelegate {

    private static final Logger logger = LogManager.getLogger(ChannelDisruptorHandler.class);

    /**
     * Disruptor RingBuffer Size (2的N次方)
     */
    private static final int DISRUPTOR_RING_BUFFER_SIZE = 1024;

    /**
     * 异步消息处理的RingBuffer
     */
    private volatile Disruptor<ChannelEvent> disruptor;
    private ChannelHandler handler;
    private final ThreadLocal<EventInfo> threadLocalInfo = ThreadLocal.withInitial(() -> new EventInfo(new ChannelEventTranslator()));

    /**
     * 是否开启Disruptor
     */
    private boolean disruptorSwitch;

    /**
     * 默认不开启Disruptor
     */
    private static final int DISRUPTOR_SWITCH_OFF = 0;

    /**
     * 开启Disruptor
     */
    private static final int DISRUPTOR_SWITCH_ON = 1;

    public ChannelDisruptorHandler(TransportContext transportContext, ChannelHandler handler) {
        this.handler = handler;
        int disruptorSwitchValue = transportContext.getParameter(ProtocolConstants.DISRUPTOR_SWITCH, DISRUPTOR_SWITCH_OFF);
        this.disruptorSwitch = (disruptorSwitchValue == DISRUPTOR_SWITCH_ON);
        if (disruptorSwitch) {
            ThreadFactory factory = new NamedThreadFactory(transportContext.getParameter(ProtocolConstants.DISRUPTOR_CONSUMER_POOL_NAME, "channel_disruptor_consumer_pool"), false);
            this.disruptor = new Disruptor<>(
                    ChannelEvent.FACTORY,
                    DISRUPTOR_RING_BUFFER_SIZE,
                    factory,
                    ProducerType.MULTI,
                    new BlockingWaitStrategy()
            );
            WorkHandler<ChannelEvent> [] workHandlers = new ChannelEventHandler[16];
            for(int i = 0; i < 16 ; i++){
                workHandlers[i] = new ChannelEventHandler();
            }
            this.disruptor.setDefaultExceptionHandler(new ChannelEventExceptionHandler());
            this.disruptor.handleEventsWithWorkerPool(workHandlers);
            this.disruptor.start();
        }
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
    public void send(Channel channel, Object message) throws TransportException {
        handler.send(channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws TransportException {
        if (disruptorSwitch) {
            EventInfo info = threadLocalInfo.get();
            if (info == null) {
                info = new EventInfo(new ChannelEventTranslator());
                threadLocalInfo.set(info);
            }
            Disruptor<ChannelEvent> temp = disruptor;
            if (temp == null) {
                logger.error("The Disruptor queue has been closed, and the message is no longer received.");
            } else if (disruptor.getRingBuffer().remainingCapacity() == 0L) {
                logger.warn("The Disruptor has no remaining buffer, use handler instead");
                this.handler.received(channel, message);
            } else {
                info.eventTranslator.setEventValues(handler, channel, ChannelEvent.ChannelState.RECEIVED, null, message);
                try {
                    disruptor.publishEvent(info.eventTranslator);
                } catch (NullPointerException e) {
                    logger.error("The Disruptor queue has been closed, and the message is no longer received.");
                }
            }
        } else {
            handler.received(channel, message);
        }
    }

    @Override
    public void caught(Channel channel, Throwable throwable) throws TransportException {
        handler.caught(channel, throwable);
    }

    @Override
    public ChannelHandler getHandler() {
        return handler;
    }

    @Override
    public void close() {
        if(handler != null){
            handler.close();
        }
        if(disruptor != null){
            disruptor.shutdown();
        }
        threadLocalInfo.remove();
    }

    /**
     *
     */
    static class EventInfo {
        private final ChannelEventTranslator eventTranslator;

        EventInfo(ChannelEventTranslator eventTranslator) {
            this.eventTranslator = eventTranslator;
        }
    }
}