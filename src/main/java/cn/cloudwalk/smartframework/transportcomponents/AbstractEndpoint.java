package cn.cloudwalk.smartframework.transportcomponents;

import cn.cloudwalk.smartframework.transportcomponents.support.ProtocolConstants;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

/**
 * @author LIYANHUI
 */
public abstract class AbstractEndpoint extends AbstractPeer {
    private int timeout;
    private int connectTimeout;
    private Codec codec;

    AbstractEndpoint(TransportContext transportContext, ChannelHandler handler) {
        super(transportContext, handler);
        this.codec = transportContext.getCodec();
        this.timeout = transportContext.getParameter(ProtocolConstants.CLIENT_CONNECT_TIME, 3000);
        this.connectTimeout = transportContext.getParameter(ProtocolConstants.CLIENT_CONNECT_TIMEOUT, 3000);
    }

    @Override
    public void reset(TransportContext transportContext) {
    }

    protected Codec getCodec() {
        return codec;
    }

    protected int getTimeout() {
        return timeout;
    }

    protected int getConnectTimeout() {
        return connectTimeout;
    }
}
