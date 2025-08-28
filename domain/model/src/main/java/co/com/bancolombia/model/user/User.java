package co.com.bancolombia.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class User {
    private String id;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private Double baseSalary;
}
