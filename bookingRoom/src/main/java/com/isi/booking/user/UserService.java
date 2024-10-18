package com.isi.booking.user;


import com.isi.booking.handler.BusinessErrorCodes;
import com.isi.booking.exception.RoleNameException;
import com.isi.booking.role.Role;
import com.isi.booking.role.RoleRepository;
import com.isi.booking.token.Token;
import com.isi.booking.token.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository ;

    public List<ResponseUser> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }
    public ResponseUser getUserById(Integer userId) {
        return repository.findById(userId)
                .map(mapper::fromUser)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription()));
    }
    public void deleteUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription()));
        repository.delete(user);
    }
    public ResponseUser getInfo(String email) {
         return repository.findByEmail(email)
                .map(mapper::fromUser)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription()));

    }

    public User assignAdminRole(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " : " + userId));
        //get the role admin and assign it to the user
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RoleNameException(BusinessErrorCodes.ROLE_NAME_NOT_EXIST.getDescription())) ;
        user.getRoles().add(adminRole) ;
        return repository.save(user);
    }
    public User assignManagerRole(Integer userId){
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " : " + userId));
        // get the role manager and assign it to the user
        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseThrow(() -> new RoleNameException(BusinessErrorCodes.ROLE_NAME_NOT_EXIST.getDescription()));
        user.getRoles().add(managerRole);
        return repository.save(user);
    }

    public void resetPassword(String token, String newPassword){
        Token passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token Invalid"));
        if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Le Token a expiré");
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        // Marquer le token comme expiré

        passwordResetToken.setExpired(true);
        tokenRepository.save(passwordResetToken);
    }
}
