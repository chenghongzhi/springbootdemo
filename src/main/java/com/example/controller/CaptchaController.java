package com.example.controller;

import com.example.config.KaptchaConfig;
import com.example.util.ResultJSON;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private KaptchaConfig kaptchaConfig;
    @Value("${authCode.expire.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Value("${authCode.captchaKey}")
    private String AUTH_CODE_captchaKey;

    @GetMapping("/captcha")
    public ResultJSON generateVerificationCode(HttpServletRequest request) throws Exception {
        ResultJSON json =new ResultJSON();
        Map<String, Object> map = new HashMap<>();
        // 生成文字验证码
        String text = kaptchaConfig.producer().createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = kaptchaConfig.producer().createImage(text);
        ImageIO.write(image, "jpg", outputStream);
        request.getSession().setAttribute("code",text);
        System.out.println(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        map.put("img", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        //生成验证码对应的token  以token为key  验证码为value存在redis中
        String codeToken = UUID.randomUUID().toString().replaceAll("-","").substring(0,16);
        redisTemplate.opsForValue().set(AUTH_CODE_captchaKey+":"+codeToken, text, 10, TimeUnit.MINUTES);
        map.put("codeToken", codeToken);
        json.success(map);
        return json;
    }
}
