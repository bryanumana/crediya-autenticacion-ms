package co.com.bancolombia.r2dbc.entities;

import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table("USERS")
public class UserEntity {
    private String id;
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private Double baseSalary;
}
