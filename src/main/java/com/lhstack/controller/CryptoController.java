package com.lhstack.controller;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhstack.utils.Aes;

import io.reactivex.Single;
import io.reactivex.exceptions.CompositeException;

@RestController
@RequestMapping("crypto")
public class CryptoController {

    @Value("${aes.key:}")
    private String defaultAesKey;

    @Value("${aes.iv:}")
    private String defaultAesIv;
    
    @PostMapping("decrypt")
    public Single<String> decrypt(@RequestBody Map<String,String> body){
        String aesKey = body.getOrDefault("key", defaultAesKey);
        String aesIv = body.getOrDefault("iv", defaultAesIv);
        byte[] content = body.getOrDefault("content","").getBytes();
        return Single
        .fromCallable(() -> Aes.decrypt(aesKey, aesIv, Base64.decode(content)))
        .onErrorResumeNext(e ->Single.just(Aes.decrypt(aesKey, aesIv, Hex.decode(content))))
        .map(bytes -> new String(bytes,StandardCharsets.UTF_8))
        .onErrorReturn(e -> {
            if(e instanceof CompositeException){
                CompositeException compositeException = (CompositeException)e;
                return compositeException.getExceptions().stream().map(item -> String.format("%s=%s",item.getClass().getSimpleName(),item.getMessage())).collect(Collectors.joining(","));
            }
            return e.getMessage();
        });
    }

    @GetMapping
    public Single<String> test(){
        return Single.defer(() -> {
            return Single.just("hello world");
        });
    }
}
