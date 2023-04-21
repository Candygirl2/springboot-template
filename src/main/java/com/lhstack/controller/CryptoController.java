package com.lhstack.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhstack.utils.Aes;

import io.reactivex.Single;

@RestController
@RequestMapping("crypto")
public class CryptoController {

    @Value("${aes.key:}")
    private String defaultAesKey;

    @Value("${aes.iv:}")
    private String defaultAesIv;
    
    @PostMapping("decrypt")
    public Single<String> decrypt(@RequestBody Map<String,String> body){
        return Single.defer(() -> {
            String aesKey = body.getOrDefault("key", defaultAesKey);
            String aesIv = body.getOrDefault("iv", defaultAesIv);
            byte[] content = body.getOrDefault("content","").getBytes();
            return Single.fromCallable(() -> {
                Optional<byte[]> resulOptional = Aes.decrypt(aesKey, aesIv, content);
                return "hello world";
            });
        });
    }

    @GetMapping
    public Single<String> test(){
        return Single.defer(() -> {
            return Single.just("hello world");
        });
    }
}
