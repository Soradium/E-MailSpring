package org.emailspring.main.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {

    @GetMapping("/checkengine")
    public String checkEngine() {
        return "OK";
    }
    //If some errors occure in the mappings
    //Try to Trace debug, because these errors will look like errors with security config
    @GetMapping( "/refresh_csrf_token")
    @PermitAll
    public ResponseEntity<String> refreshCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok(csrfToken != null ? csrfToken.getToken() : "No CSRF Token");
    }
}
