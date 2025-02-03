package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    @Size(min = 3, max = 100, message = "Username length must be in between 3 and 20")
    @NotBlank
    private String name;
    @Email
    @NotBlank
    @UniqueElements(message = "This email is already taken")
    private String email;
}
