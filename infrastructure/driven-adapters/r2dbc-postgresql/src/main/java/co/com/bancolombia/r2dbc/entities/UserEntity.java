/*package co.com.bancolombia.r2dbc.entities;

public class UserEntity {
}
package co.com.bancolombia.r2dbc.entities;

import lombok.*;
        import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @Column("user_id")
    private String userId;
    @Column("document_id")
    private String documentId;
    private String name;
    private String lastname;
    @Column("birth_date")
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    @Column("base_salary")
    private BigInteger baseSalary;

}*/