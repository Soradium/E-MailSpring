package org.emailspring.main.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.emailspring.main.entity.User;
import org.emailspring.main.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class LoginController {
    @Autowired
    private EntityService entityService;
    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            Cookie cookieJSession = new Cookie("JSESSIONID", request.getSession().getId());
            Cookie cookieUsername = new Cookie("USERNAME", loginRequest.username());
            System.out.println("Entering login request");
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            loginRequest.username(),
                            loginRequest.password()
                    );
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            response.addCookie(cookieJSession);
            response.addCookie(cookieUsername);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public record LoginRequest(String username, String password) {
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody LoginRequest loginRequest,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            Cookie cookieJSession = new Cookie("JSESSIONID", request.getSession().getId());
            Cookie cookieUsername = new Cookie("USERNAME", loginRequest.username());
            System.out.println("Entering sign up request");
            User user = new User();
            user.setUsername(loginRequest.username());
            user.setPassword("{bcrypt}" + new BCryptPasswordEncoder()
                    .encode(loginRequest.password()));
            user.setEnabled(true);
            entityService.saveUserWithAuthority(user, "ROLE_USER");
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            loginRequest.username(),
                            loginRequest.password()
                    );
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            response.addCookie(cookieJSession);
            response.addCookie(cookieUsername);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}