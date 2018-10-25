package me.aurora.app.rest.utils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.utils.AlipayConfig;
import me.aurora.domain.vo.TradeVo;
import me.aurora.service.AlipayService;
import me.aurora.util.HttpContextUtils;
import me.aurora.util.pay.AliPayStatusEnum;
import me.aurora.util.pay.AlipayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhengjie
 * @date 2018/07/27 13:41:41
 */
@Slf4j
@RestController
@RequestMapping("/aliPay")
public class AliPayController {

    @Autowired
    AlipayUtils alipayUtils;

    @Autowired
    private AlipayService alipayService;

    /**
     * 跳转到页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        AlipayConfig alipay = alipayService.findById(1L);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("alipay",alipay);
        if(alipay == null){
            request.setAttribute("alipay",new AlipayConfig());
        }
        return new ModelAndView("/utils/alipay/index");
    }

    @Log("配置支付宝")
    @PostMapping(value = "/config")
    public ResponseEntity payConfig(@Validated(AlipayConfig.New.class) @RequestBody AlipayConfig alipayConfig){
        log.warn("REST request to payConfig AlipayConfig : {}" +alipayConfig);
        alipayConfig.setId(1L);
        alipayService.updateConfig(alipayConfig);
        return ResponseEntity.ok();
    }

    @Log("支付宝PC网页支付")
    @ApiOperation(value = "PC网页支付")
    @PostMapping(value = "/toPayAsPC")
    public ResponseEntity toPayAsPC(@Validated(TradeVo.New.class) @RequestBody TradeVo trade) throws Exception{
        log.warn("REST request to toPayAsPC Trade : {}" +trade);
        AlipayConfig alipay = alipayService.findById(1L);
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPC(alipay,trade);
        return ResponseEntity.ok(payUrl);
    }

    @Log("支付宝手机网页支付")
    @ApiOperation(value = "手机网页支付")
    @PostMapping(value = "/toPayAsWeb")
    public ResponseEntity toPayAsWeb(@Validated(TradeVo.New.class) @RequestBody TradeVo trade) throws Exception{
        log.warn("REST request to toPayAsWeb Trade : {}" +trade);
        AlipayConfig alipay = alipayService.findById(1L);
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsWeb(alipay,trade);
        return ResponseEntity.ok(payUrl);
    }

    @Log("支付宝回调")
    @ApiIgnore
    @GetMapping("/return")
    @ApiOperation(value = "支付之后跳转的链接")
    public ResponseEntity returnPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AlipayConfig alipay = alipayService.findById(1L);
        response.setContentType("text/html;charset=" + alipay.getCharset());
        //内容验签，防止黑客篡改参数
        if(alipayUtils.rsaCheck(request,alipay)){
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            System.out.println("商户订单号"+outTradeNo+"  "+"第三方交易号"+tradeNo);

            /**
             * 根据业务需要返回数据，这里统一返回OK
             */
            return ResponseEntity.ok();
        }else{
            /**
             * 根据业务需要返回数据
             */
            return ResponseEntity.error();
        }
    }

    @Log("支付异步通知")
    @ApiIgnore
    @RequestMapping("/notify")
    @ApiOperation(value = "支付异步通知(要公网访问)，接收异步通知，检查通知内容app_id、out_trade_no、total_amount是否与请求中的一致，根据trade_status进行后续业务处理")
    public ResponseEntity notify(HttpServletRequest request) throws Exception{
        AlipayConfig alipay = alipayService.findById(1L);
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder("/****************************** pay notify ******************************/\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n") );
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request,alipay)) {
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //验证
            if(tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue())||tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())){
                /**
                 * 验证通过后应该根据业务需要处理订单
                 */
            }
            return ResponseEntity.ok();
        }
        return ResponseEntity.error();
    }
}
