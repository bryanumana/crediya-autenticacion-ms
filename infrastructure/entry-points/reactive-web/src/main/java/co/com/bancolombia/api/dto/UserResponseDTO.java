package co.com.bancolombia.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
    private String id;
    private String name;
    private String lastName;
    private String email;

    public UserResponseDTO(String id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
}
