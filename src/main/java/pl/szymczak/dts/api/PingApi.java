package pl.szymczak.dts.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingApi {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
