package com.isi.booking.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Le nom est requis")
    @NotBlank(message = "Le prenom est requis")
    private String name;
    @NotEmpty(message = "Le nom est requis")
    @NotBlank(message = "Le prenom est requis")
    @Email(message = "Le mail n'est pas bien formaté")
    private String email;
    @NotEmpty(message = "Le numéro de téléphone est requis")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Le numéro de téléphone doit être valide (ex : +1234567890)")
    private String phone;
    @NotEmpty(message = "Le Mot de pass est requis")
    @NotBlank(message = "Le Mot de pass est requis")
    @Size(min = 8, message = "Le mail doit avoir au moins 8 caractère au minimum")
    private String password;
}
