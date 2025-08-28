package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.dto.UserResponseDTO;
import co.com.bancolombia.model.user.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .baseSalary(dto.getBaseSalary())
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}