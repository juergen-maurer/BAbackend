package com.example.webshopba.service;

import com.example.webshopba.model.Cart;
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
        kunden.setWarenkorb(new Cart());
        kunden.setEmail(kunden.getEmail());
        kunden.setFirstName(kunden.getFirstName());
        kunden.setLastName(kunden.getLastName());
        return kundenRepository.save(kunden);
    }

    public boolean emailExists(String email) {
        return kundenRepository.findByEmail(email).isPresent();
    }

    public Kunden authenticateUser(String email, String password) {
        Kunden user = findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

    public void logoutUser() {

    }

    public Kunden updateCustomerDetails( Kunden updatedKunden) {
        Kunden existingKunden = findById(updatedKunden.getId());
            existingKunden.setFirstName(updatedKunden.getFirstName());
            existingKunden.setLastName(updatedKunden.getLastName());
            existingKunden.setEmail(updatedKunden.getEmail());


        return kundenRepository.save(existingKunden);
    }

    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        Kunden existingKunden = findById(id);
        if (!passwordEncoder.matches(oldPassword, existingKunden.getPassword())) {
            return false;
        }
        existingKunden.setPassword(passwordEncoder.encode(newPassword));
        kundenRepository.save(existingKunden);
        return true;
    }

    public boolean checkPassword(Kunden updatedKunde , String password) {
        Kunden existingKunden = findById(updatedKunde.getId());
        return passwordEncoder.matches(password, existingKunden.getPassword());
    }
    private Kunden findByEmail(String email) {
        return kundenRepository.findByEmail(email).orElse(null);
    }
    public Kunden findById(Long id) {
        return kundenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
