package co.com.bancolombia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private Double baseSalary;
}
