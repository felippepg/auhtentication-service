package br.com.felippe.authservice.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @GetMapping
    private ResponseEntity<String> saySomething() {
        return ResponseEntity.ok("Hello from security endpoint");
    }
}
