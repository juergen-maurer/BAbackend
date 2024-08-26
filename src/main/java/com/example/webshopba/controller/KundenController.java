package com.example.webshopba.controller;


import com.example.webshopba.ChangePasswordRequest;
import com.example.webshopba.KundenResponse;
import com.example.webshopba.LoginRequest;
import com.example.webshopba.UpdateRequest;
import com.example.webshopba.model.Kunden;
import com.example.webshopba.service.KundenService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Map<String, String> response = new HashMap<>();
        try {
            Kunden authenticatedKunden = kundenService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            response.put("kundenId", authenticatedKunden.getId().toString());
            response.put("email", authenticatedKunden.getEmail());
            response.put("firstName", authenticatedKunden.getFirstName());
            response.put("lastName", authenticatedKunden.getLastName());
            response.put("warenkorbId", authenticatedKunden.getWarenkorbId().toString());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "Email not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", "Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Kunden user) {
        // Registration logic here
        Map<String, String> response = new HashMap<>();
        if (kundenService.emailExists(user.getEmail())) {
            response.put("error", "Email address already in use");
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        Kunden registeredKunden = kundenService.registerNewUserAccount(user);
        response.put("kundenId", registeredKunden.getId().toString());
        response.put("email", registeredKunden.getEmail());
        response.put("firstName", registeredKunden.getFirstName());
        response.put("lastName", registeredKunden.getLastName());
        response.put("warenkorbId", registeredKunden.getWarenkorbId().toString());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


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

    @GetMapping("/profile")
    public ResponseEntity<KundenResponse> getUser(@RequestParam Long id) {
        try {
            Kunden kunden = kundenService.findById(id);
            KundenResponse response = new KundenResponse(kunden.getId(), kunden.getEmail(), kunden.getFirstName(), kunden.getLastName(), kunden.getLastUsedAddress(), kunden.getBestellungen());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/profile/{kunde}")
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody UpdateRequest updateRequest) {
        Map<String, String> response = new HashMap<>();
        if (!kundenService.checkPassword(updateRequest.getUser(), updateRequest.getPassword())) {
            response.put("error", "Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        Kunden kunden = kundenService.updateCustomerDetails(updateRequest.getUser());
        response.put("message", "Profile updated successfully");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @PutMapping("/change-password/{kundenId}")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable Long kundenId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        Map<String, String> response = new HashMap<>();
        boolean success = kundenService.changePassword(kundenId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        if (!success) {
            response.put("error", "Invalid old password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    // Weitere Endpunkte können hier hinzugefügt werden
}