package com.lhstack;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhstack.utils.Aes;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateApplicationTests {
    @Value("${aes.key:}")
    private String defaultAesKey;

    @Value("${aes.iv:}")
    private String defaultAesIv;

    @Test
    public void test() throws Exception{
        Map<String,Object> data = new HashMap<>();
        data.put("date", new Date());
        data.put("time", System.currentTimeMillis());
        data.put("msg", "hello world");
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        data.put("msg", list);
        data.put("class", TemplateApplicationTests.class);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonBytes = objectMapper.writeValueAsBytes(data);
        byte[] bytes = Aes.encrypt(defaultAesKey, defaultAesIv, jsonBytes);
        System.out.println(new String(Base64.encode(bytes),StandardCharsets.UTF_8));
        System.out.println(new String(Hex.encode(bytes),StandardCharsets.UTF_8));
    }
}
