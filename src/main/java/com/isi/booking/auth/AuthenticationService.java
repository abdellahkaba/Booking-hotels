package com.isi.booking.auth;

import com.isi.booking.exceptionHandler.BusinessErrorCodes;
import com.isi.booking.exceptionHandler.EmailConflictException;
import com.isi.booking.exceptionHandler.PhoneConflictException;
import com.isi.booking.role.RoleRepository;
import com.isi.booking.security.JwtService;
import com.isi.booking.user.User;
import com.isi.booking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER n'a pas été initialisé"));
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailConflictException(BusinessErrorCodes.DUPLICATE_EMAIL.getDescription());
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()){
            throw new PhoneConflictException(BusinessErrorCodes.DUPLICATE_PHONE.getDescription());
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enable(true)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String,Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("name",user.getName());
        var jwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
