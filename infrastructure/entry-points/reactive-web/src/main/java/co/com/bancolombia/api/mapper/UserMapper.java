package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.dto.UserResponseDTO;
import co.com.bancolombia.model.user.User;
import org.springframework.web.bind.annotation.Mapping;


public interface UserMapper {

    static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setBaseSalary(dto.getBaseSalary());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

    static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getBaseSalary()
        );
    }
}