package br.com.felippe.authservice.auth;

import br.com.felippe.authservice.config.JwtService;
import br.com.felippe.authservice.tfa.TwoFactorAuthenticationService;
import br.com.felippe.authservice.user.Role;
import br.com.felippe.authservice.user.User;
import br.com.felippe.authservice.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .mfaEnabled(request.isMfaEnabled())
                .password(encoder.encode(request.getPassword()))
                .build();
        if(request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.DEFAULT_USER);
        }

        if(request.isMfaEnabled()) {
            user.setSecret(twoFactorAuthenticationService.generateNewSecret());
        }
        System.out.println(request.isMfaEnabled());
        repository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .secretImageUri(twoFactorAuthenticationService.generateQrCodeImageUri(user.getSecret()))
                .isMfaEnabled(user.getMfaEnabled())
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        if(user.getMfaEnabled()) {
            return AuthenticationResponse.builder()
                    .token("")
                    .isMfaEnabled(true)
                    .build();
        }
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .isMfaEnabled(user.getMfaEnabled())
                .build();
    }

    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {
        User user = repository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("No user found"));

        if(twoFactorAuthenticationService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException("Code is not valid");
        }

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .isMfaEnabled(user.getMfaEnabled())
                .build();
    }
}
