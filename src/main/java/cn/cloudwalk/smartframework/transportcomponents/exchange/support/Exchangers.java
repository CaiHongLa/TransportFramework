package cn.cloudwalk.smartframework.transportcomponents.exchange.support;

import cn.cloudwalk.smartframework.transportcomponents.exchange.ExchangeClient;
import cn.cloudwalk.smartframework.transportcomponents.exchange.ExchangeHandler;
import cn.cloudwalk.smartframework.transportcomponents.exchange.ExchangeServer;
import cn.cloudwalk.smartframework.transportcomponents.exchange.Exchanger;
import cn.cloudwalk.smartframework.transportcomponents.exchange.support.header.HeaderExchangeClient;
import cn.cloudwalk.smartframework.transportcomponents.exchange.support.header.HeaderExchangeHandler;
import cn.cloudwalk.smartframework.transportcomponents.exchange.support.header.HeaderExchangeServer;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportBinder;
import cn.cloudwalk.smartframework.transportcomponents.support.transport.TransportContext;

/**
 * Exchangers
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public class Exchangers {

    private Exchangers() {
    }

    public static ExchangeServer bind(TransportContext transportContext, ExchangeHandler handler) {
        return getExchanger().bind(transportContext, handler);
    }

    public static ExchangeClient connect(TransportContext transportContext, ExchangeHandler handler) {
        return getExchanger().connect(transportContext, handler);
    }

    private static Exchanger getExchanger() {
        return new Exchanger() {
            @Override
            public ExchangeServer bind(TransportContext transportContext, ExchangeHandler handler) {
                return new HeaderExchangeServer(TransportBinder.bind(transportContext, new HeaderExchangeHandler(handler)));
            }

            @Override
            public ExchangeClient connect(TransportContext transportContext, ExchangeHandler handler) {
                return new HeaderExchangeClient(TransportBinder.connect(transportContext, new HeaderExchangeHandler(handler)), false);
            }
        };
    }

}