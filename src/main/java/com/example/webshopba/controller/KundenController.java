package com.example.webshopba.controller;

import com.example.webshopba.model.Kunden;
import com.example.webshopba.service.KundenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class KundenController {
    private final KundenService kundenService;

    @Autowired
    public KundenController(KundenService kundenService) {
        this.kundenService = kundenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Kunden> registerUser(@RequestBody Kunden kunden) {
        Kunden registeredKunden = kundenService.registerNewUserAccount(kunden);
        return new ResponseEntity<>(registeredKunden, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kunden> getUserById(@PathVariable Long id) {
        Kunden kunden = kundenService.findById(id);
        return ResponseEntity.ok(kunden);
    }

    // Weitere Endpunkte können hier hinzugefügt werden
}