package com.isi.booking.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender ;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String resetPasswordUrl,
            String resetToken,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplateName == null) {
            templateName = "reset-password";
        }else {
            templateName = emailTemplateName.name();
        }
        // creer un email avec contenu HTML
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new  MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        // Définir les propriétés à utiliser dans le template
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("resetPasswordUrl", resetPasswordUrl);  // Lien de réinitialisation
        properties.put("reset_token", resetToken); // Token de réinitialisation

        // Construire le contexte du template avec les variables
        Context context = new Context();
        context.setVariables(properties);

        // Configurer l'email
        helper.setFrom("abdallahkaba98@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Processer le template avec les variables
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);

        // Envoyer l'email
        mailSender.send(mimeMessage);

    }
}
