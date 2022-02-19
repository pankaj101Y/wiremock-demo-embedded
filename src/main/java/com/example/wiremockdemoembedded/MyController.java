package com.example.wiremockdemoembedded;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("test")
public class MyController {

    RestTemplate restTemplate=new RestTemplate();


    @GetMapping("/call")
    public Object call(){
        return WebUtils.getMonoSafely(WebUtils.getImagesHash(new Request()));
    }



    @PostMapping("/post")
    public Object post(@RequestBody Request request){
        return new Response();
    }
}
