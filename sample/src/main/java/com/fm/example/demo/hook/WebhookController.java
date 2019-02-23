package com.fm.example.demo.hook;

import com.fm.example.demo.handler.*;
import com.fm.example.demo.util.PersistentMenuCreator;
import com.fm.example.demo.util.SettingMessageHelper;
import com.github.codedrinker.fm.FMClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author wangwei on 2019/2/20.
 * wangwei@jiandaola.com
 */
@Slf4j
@RestController
@RequestMapping("/fm")
public class WebhookController {

    private static boolean isFirstHook = true;

    @Value("${fm.access.token}")
    private String token;
    @Value("${fm.access.secret}")
    private String secret;

    private PostbackHandler postbackHandler;
    private MessageHandler messageHandler;
    private ReadHandler readHandler;
    private ResultAspect resultAspect;
    private DeliveryHandler deliveryHandler;
    private ReferralHandler referralHandler;

    private SettingMessageHelper settingMessageHelper;
    private PersistentMenuCreator menuCreator;


    @Autowired
    public WebhookController(PostbackHandler postbackHandler, MessageHandler messageHandler,
                             ReadHandler readHandler, ResultAspect resultAspect,
                             DeliveryHandler deliveryHandler, ReferralHandler referralHandler,
                             SettingMessageHelper settingMessageHelper, PersistentMenuCreator menuCreator) {
        this.postbackHandler = postbackHandler;
        this.messageHandler = messageHandler;
        this.readHandler = readHandler;
        this.resultAspect = resultAspect;
        this.deliveryHandler = deliveryHandler;
        this.referralHandler = referralHandler;
        this.settingMessageHelper = settingMessageHelper;
        this.menuCreator = menuCreator;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public String check() {
        log.info("check");
        return "ok";
    }

    @RequestMapping(value = "/webhook", method = RequestMethod.GET)
    @ResponseBody
    public void getWebhook(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("getWebhook .......");
        log.info("GET Webhook ...");
        log.info("fmAccessToken= " + token);
        log.info("fmAccessSecret= " + secret);
        String mode = request.getParameter("hub.mode");
        String token = request.getParameter("hub.verify_token");
        log.info("mode= " + mode);
        log.info("token= " + token);
        if (StringUtils.equals("subscribe", mode) && StringUtils.equals(this.token, token)) {
            log.info("Validating webhook");
            String parameter = request.getParameter("hub.challenge");
            try {
                response.getWriter().println(parameter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("Failed validation. Make sure the validation tokens match.");
            response.setStatus(403);
        }
    }

    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    @ResponseBody
    public void postWebhook(HttpServletRequest request, HttpServletResponse response) {
        log.info("post Webhook ...");
        log.info("fmpAccessToken= " + token);
        log.info("fmpAccessSecret= " + secret);
        // 收到 Webhook 事件后，您必须始终返回 200 OK HTTP 响应。Messenger 平台将每隔 20 秒重新发送一个 Webhook 事件，
        // 直到收到 200 OK 响应。如果未能返回 200 OK，可能会导致 Webhook 被 Messenger 平台取消订阅。
        response.setStatus(200);

        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            log.debug("post Webhook payload => " + payload);

            log.debug("this is debug log");
            log.debug("postWebhook token->{}", token);
            log.debug("postWebhook secret->{}", secret);
            String xHubSignature = request.getHeader("X-Hub-Signature");
            FMClient fmClient = FMClient.getInstance();
            fmClient.withAccessToken(token)
                    .withAccessSecret(secret)
                    .withFmMessagePostBackHandler(postbackHandler)
                    .withFmMessageHandler(messageHandler)
                    .withFmMessageReadHandler(readHandler)
                    .withFmMessageDeliveryHandler(deliveryHandler)
                    .withFmMessageReferralHandler(referralHandler)
                    .withFmResultAspect(resultAspect);
            log.info("xHubSignature -> {}", xHubSignature);
            log.info("WebHookController receive message-> {}", payload);
            boolean signature = fmClient.signature(payload, xHubSignature);
            log.info("signature => " + signature);
            if (signature) {
                //设置 Get Started 欢迎语
                log.info("isFirstHook => " + isFirstHook);
                if (isFirstHook) {
                    log.info("first hook, set profile ==>>>");
                    fmClient.sendProfileSetting(settingMessageHelper.greetingSetting());
                    fmClient.sendProfileSetting(settingMessageHelper.getStartedSetting());
                    fmClient.sendProfileSetting(menuCreator.createPersistentMenu());
                    isFirstHook = false;
                }
                fmClient.dispatch(payload);

            } else {
                log.error("WebHookController post webhook Failed validation. xhubSignature -> {}, signature -> {}", xHubSignature, signature);
                //response.setStatus(403);
            }
        } catch (IOException e) {
            log.error("WebHookController post webhook error", e);
            //response.setStatus(403);
        }
    }
}
