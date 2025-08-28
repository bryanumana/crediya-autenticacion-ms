package co.com.bancolombia.api.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequestDTO {
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private Double baseSalary;
}
