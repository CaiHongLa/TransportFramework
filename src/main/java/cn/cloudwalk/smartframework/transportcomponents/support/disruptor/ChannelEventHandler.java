package cn.cloudwalk.smartframework.transportcomponents.support.disruptor;

import com.lmax.disruptor.WorkHandler;

public class ChannelEventHandler implements WorkHandler<ChannelEvent> {

    @Override
    public void onEvent(ChannelEvent channelEvent) throws Exception {
        channelEvent.executeEvent();
        channelEvent.clear();
    }
}
