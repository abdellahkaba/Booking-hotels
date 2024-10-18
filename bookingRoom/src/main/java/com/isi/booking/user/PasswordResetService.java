package com.isi.booking.user;

import com.isi.booking.email.EmailService;
import com.isi.booking.email.EmailTemplateName;
import com.isi.booking.token.Token;
import com.isi.booking.token.TokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public void createPasswordResetToken(String userEmail) throws MessagingException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String resetToken = UUID.randomUUID().toString();

        Token token = new Token();
        token.setToken(resetToken);
        token.setUser(user);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(60));
        token.setExpired(false);
        tokenRepository.save(token);

        // Lien pour la réinitialisation du mot de passe
        String resetPasswordUrl = "http://localhost:4200/reset-password?token=" + resetToken;

        // Utiliser EmailService pour envoyer l'email
        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.RESET_PASSWORD,  // Utilisation du template de réinitialisation
                resetPasswordUrl,
                resetToken,
                "Réinitialisation du mot de passe"
        );
    }
}
