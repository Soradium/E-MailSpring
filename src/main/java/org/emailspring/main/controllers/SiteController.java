package org.emailspring.main.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {
    @GetMapping("/status")
    public ResponseEntity<String> home(@CookieValue("JSESSIONID") String sessionId) {
        if(sessionId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            return ResponseEntity.ok("");
        }
    }
    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchToken(HttpServletRequest request,
                                              HttpServletResponse response) {
        return ResponseEntity.ok().build();
    }
    //If some errors occure in the mappings
    //Try to Trace debug, because these errors will look like errors with security config
    @PostMapping("/send_message")
    public ResponseEntity<String> sendMessage(HttpServletRequest request) {
        return ResponseEntity.ok("Message sent");
    }
    @GetMapping("/get_all_messages")
    public ResponseEntity<String> getAllMessages(HttpServletRequest request) {
        return ResponseEntity.ok("All messages");
    }

}
