package com.example.webshopba.service;

import com.example.webshopba.model.Kunden;
import com.example.webshopba.repository.KundenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class KundenServiceTest {

    @Mock
    private KundenRepository kundenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private KundenService kundenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerNewUserAccount() {
        Kunden kunden = new Kunden();
        kunden.setPassword("plainPassword");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(kundenRepository.save(any(Kunden.class))).thenReturn(kunden);

        Kunden savedKunden = kundenService.registerNewUserAccount(kunden);

        assertNotNull(savedKunden);
        assertEquals("encodedPassword", savedKunden.getPassword());
        verify(kundenRepository, times(1)).save(kunden);
    }

    @Test
    void emailExists() {
        when(kundenRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new Kunden()));

        boolean exists = kundenService.emailExists("test@example.com");

        assertTrue(exists);
        verify(kundenRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void authenticateUser() {
        Kunden kunden = new Kunden();
        kunden.setPassword("encodedPassword");

        when(kundenRepository.findByEmail(any(String.class))).thenReturn(Optional.of(kunden));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        Kunden authenticatedKunden = kundenService.authenticateUser("test@example.com", "plainPassword");

        assertNotNull(authenticatedKunden);
        verify(kundenRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("plainPassword", "encodedPassword");
    }

    @Test
    void authenticateUser_UserNotFound() {
        when(kundenRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            kundenService.authenticateUser("test@example.com", "plainPassword");
        });

        assertEquals("User not found with email: test@example.com", exception.getMessage());
    }

    @Test
    void authenticateUser_InvalidPassword() {
        Kunden kunden = new Kunden();
        kunden.setPassword("encodedPassword");

        when(kundenRepository.findByEmail(any(String.class))).thenReturn(Optional.of(kunden));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            kundenService.authenticateUser("test@example.com", "plainPassword");
        });

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void changePassword() {
        Kunden kunden = new Kunden();
        kunden.setPassword("encodedOldPassword");

        when(kundenRepository.findById(any(Long.class))).thenReturn(Optional.of(kunden));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedNewPassword");

        boolean result = kundenService.changePassword(1L, "oldPassword", "newPassword");

        assertTrue(result);
        assertEquals("encodedNewPassword", kunden.getPassword());
        verify(kundenRepository, times(1)).save(kunden);
    }

    @Test
    void changePassword_InvalidOldPassword() {
        Kunden kunden = new Kunden();
        kunden.setPassword("encodedOldPassword");

        when(kundenRepository.findById(any(Long.class))).thenReturn(Optional.of(kunden));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        boolean result = kundenService.changePassword(1L, "oldPassword", "newPassword");

        assertFalse(result);
        verify(kundenRepository, never()).save(kunden);
    }
}