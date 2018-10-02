package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.utils.AlipayConfig;
import me.aurora.domain.vo.TradeVo;
import me.aurora.repository.AlipayRepo;
import me.aurora.service.AlipayService;
import me.aurora.util.pay.AlipayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * @author zhengjie
 * @date 2018/09/30 14:12:35
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    AlipayUtils alipayUtils;

    @Autowired
    private AlipayRepo alipayRepo;

    @Override
    public String toPayAsPC(AlipayConfig alipay, TradeVo trade) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppID(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        double money = Double.parseDouble(trade.getTotalAmount());
        if(money <= 0 || money>=500){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"测试金额过大");
        }

        /**
         * 创建API对应的request(电脑网页版)
         */
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        /**
         * 订单完成后返回的页面和异步通知地址
         */
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        /**
         *  填充订单参数
         */
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+trade.getOutTradeNo()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+trade.getTotalAmount()+"," +
                "    \"subject\":\""+trade.getSubject()+"\"," +
                "    \"body\":\""+trade.getBody()+"\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\""+alipay.getSysServiceProviderId()+"\"" +
                "    }"+
                "  }");//填充业务参数
        /**
         * 调用SDK生成表单
         * 通过GET方式，口可以获取url
         */
        return alipayClient.pageExecute(request, "GET").getBody();

    }

    @Override
    public String toPayAsWeb(AlipayConfig alipay, TradeVo trade) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppID(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        double money = Double.parseDouble(trade.getTotalAmount());
        if(money <= 0 || money >= 5000){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"测试金额过大");
        }

        /**
         * 创建API对应的request(手机网页版)
         */
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        /**
         * 订单完成后返回的页面和异步通知地址
         */
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        /**
         *  填充订单参数
         */
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+trade.getOutTradeNo()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+trade.getTotalAmount()+"," +
                "    \"subject\":\""+trade.getSubject()+"\"," +
                "    \"body\":\""+trade.getBody()+"\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\""+alipay.getSysServiceProviderId()+"\"" +
                "    }"+
                "  }");//填充业务参数
        /**
         * 调用SDK生成表单
         * 通过GET方式，口可以获取url
         */
        return alipayClient.pageExecute(request, "GET").getBody();
    }

    @Override
    public AlipayConfig findById(long id) {
        Optional<AlipayConfig> alipayConfig = alipayRepo.findById(1L);
        if (alipayConfig.isPresent()){
            return alipayConfig.get();
        } else {
            return null;
        }
    }

    @Override
    public AlipayConfig updateConfig(AlipayConfig alipayConfig) {
        alipayRepo.saveAndFlush(alipayConfig);
        return alipayConfig;
    }
}
