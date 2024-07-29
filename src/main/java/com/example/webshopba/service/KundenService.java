package com.example.webshopba.service;

import com.example.webshopba.model.Kunden;
import com.example.webshopba.repository.KundenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KundenService {
    private final KundenRepository kundenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public KundenService(KundenRepository kundenRepository, PasswordEncoder passwordEncoder) {
        this.kundenRepository = kundenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Kunden registerNewUserAccount(Kunden kunden) {
        kunden.setPassword(passwordEncoder.encode(kunden.getPassword()));
        return kundenRepository.save(kunden);
    }
    public Kunden findById(Long id) {
        return kundenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
