package ru.practicum.shareit.user.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @Size(min = 3, max = 100, message = "Username length must be in between 3 and 20")
    private String name;
    private String email;
}
