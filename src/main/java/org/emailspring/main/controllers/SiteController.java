package org.emailspring.main.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.emailspring.main.dto.MessageDTO;
import org.emailspring.main.entity.Message;
import org.emailspring.main.entity.User;
import org.emailspring.main.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SiteController {
    @Autowired
    private EntityService entityService;

    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchToken(HttpServletRequest request,
                                              HttpServletResponse response) {
        return ResponseEntity.ok().build();
    }
    //If some errors occure in the mappings
    //Try to Trace debug, because these errors will look like errors with security config
    @PostMapping("/send_message")
    public ResponseEntity<String> sendMessage(@RequestBody Message message, HttpServletRequest request,
                                              HttpServletResponse response) {
        entityService.sendMessage(message);
        return ResponseEntity.ok("Message sent");
    }
    @GetMapping("/get_all_received_messages")
    public ResponseEntity<List<MessageDTO>> getAllReceivedMessages(@CookieValue("USERNAME") String username,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        User u = entityService.getUser(username);
        List<Message> orig = entityService.getAllReceivedMessagesPerUser(u);
        List<MessageDTO> dto = new ArrayList<>();
        for (Message m : orig) {
            dto.add(new MessageDTO(m.getUserSender().getUsername(),m.getUserReceiver().getUsername(), m.getContent()));
        }

        return ResponseEntity.ok(dto);
    }

}
