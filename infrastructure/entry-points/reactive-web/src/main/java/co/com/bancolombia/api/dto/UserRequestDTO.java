package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "{user.firstName.required}")
    private String name;

    @NotBlank(message = "{user.lastName.required}")
    private String lastName;

    @NotBlank(message = "{user.email.required}")
    @Email(message = "{user.email.format}")
    private String email;

    @NotNull(message = "{user.salary.required}")
    private Double baseSalary;

    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
}
