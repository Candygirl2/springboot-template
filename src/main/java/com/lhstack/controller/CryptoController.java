package com.lhstack.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Single;

@RestController
@RequestMapping("crypto")
public class CryptoController {
    
    @PostMapping("decrypt")
    public Single<String> decrypt(@RequestBody Map<String,String> body){
        return Single.defer(() -> {
            System.out.println(body);
            return Single.just("hello world");
        });
    }

    @GetMapping
    public Single<String> test(){
        return Single.defer(() -> {
            return Single.just("hello world");
        });
    }
}
