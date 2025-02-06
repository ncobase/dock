package com.ncobase.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

/**
 * Index Controller
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Robots-Tag", "noindex, nofollow");
        headers.add("X-Content-Type-Options", "nosniff");

        return ResponseEntity
            .ok()
            .headers(headers)
            .body("Server is running");
    }
}
