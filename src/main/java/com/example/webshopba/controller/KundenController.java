package com.example.webshopba.controller;


import com.example.webshopba.LoginRequest;
import com.example.webshopba.model.Kunden;
import com.example.webshopba.service.KundenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class KundenController {
    private final KundenService kundenService;

    @Autowired
    public KundenController(KundenService kundenService) {
        this.kundenService = kundenService;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
        Kunden authenticatedKunden = kundenService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("email", authenticatedKunden.getEmail());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);

       // Kunden authenticatedKunden = kundenService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        //return ResponseEntity.ok(new AuthResponse(authenticatedKunden.getFirstName(), authenticatedKunden.getLastName(), authenticatedKunden.getEmail()));
    }


   /* @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        kundenService.logoutUser();
        return ResponseEntity.ok().build();
    }*/


    /*@PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Authentication logic here
        Map<String, String> response = new HashMap<>();
        response.put("token", "generated-token");
        response.put("firstName", "John");
        response.put("lastName", "Doe");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }*/

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Kunden user) {
        // Registration logic here
        Kunden registeredKunden = kundenService.registerNewUserAccount(user);
        Map<String, String> response = new HashMap<>();
        response.put("email", registeredKunden.getEmail());
        response.put("firstName", registeredKunden.getFirstName());
        response.put("lastName", registeredKunden.getLastName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
    /*@PostMapping("/register")
    public ResponseEntity<Kunden> registerUser(@RequestBody Kunden kunden) {
        Kunden registeredKunden = kundenService.registerNewUserAccount(kunden);
        return new ResponseEntity<>(registeredKunden, HttpStatus.CREATED);
    }*/


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Logout logic here
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kunden> getUserById(@PathVariable Long id) {
        Kunden kunden = kundenService.findById(id);
        return ResponseEntity.ok(kunden);
    }

    private static class AuthResponse {

        private String firstName;

        public AuthResponse(String firstName, String lastName, String email) {

            this.firstName = firstName;
        }

        // Getters and Setters
    }

    // Weitere Endpunkte können hier hinzugefügt werden
}