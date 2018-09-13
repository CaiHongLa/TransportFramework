package cn.cloudwalk.smartframework.transportcomponents.exchange;

import cn.cloudwalk.smartframework.transportcomponents.Channel;

/**
 * 数据交换连接
 *
 * @author LIYANHUI
 * @since 1.0.0
 */
public interface ExchangeChannel extends Channel {

    /**
     * 获取连接Handler
     *
     * @return ExchangeHandler
     */
    ExchangeHandler getExchangeHandler();

}