package org.emailspring.main.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping("/checkengine")
    public String checkEngine() {
        return "OK";
    }
    //If some errors occure in the mappings
    //Try to Trace debug, because these errors will look like errors with security config
    @GetMapping( "/refresh_csrf_token")
    public ResponseEntity<String> refreshCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok(csrfToken != null ? csrfToken.getToken() : "No CSRF Token");
    }
    @PostMapping("/send_message")
    public ResponseEntity<String> sendMessage(HttpServletRequest request) {
        return ResponseEntity.ok("Message sent");
    }
    @GetMapping("/get_all_messages")
    public ResponseEntity<String> getAllMessages(HttpServletRequest request) {
        return ResponseEntity.ok("All messages");
    }

    /*
    * Порядок выполнения работы:
    * 1. Происходит вход на сайт
    * 2. В этот момент реакт проверяет наличие сессии (запрашивая её у сервера)
    * 3. Если сессия есть - то рендерим без логина
    * 4. Если сессии нет - рендер только логина
    * 2.1 Спринг в этот момент проверяет наличие сессии - если сессия есть
    * то отправляем данные пользователя которые закручены к этой сессии
    * Как соединить сессию и данные пользователя?
    * Как вытащить сессию из реакта? Надо ли?

    * */

}
