package minebank_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ControllerPing {
    @GetMapping("/ping")
    public Map<String, String> ping(){
        return Map.of("status", "ok");
    }
}
