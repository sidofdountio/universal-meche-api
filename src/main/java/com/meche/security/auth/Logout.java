package com.meche.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sidof
 * @Since 15/10/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "*")
//@RequestMapping("api/v1/store")
public class Logout {
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("Logged out successfully");
    }
}
