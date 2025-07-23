package lt.techin.excursion_backend.dto;

import lt.techin.excursion_backend.model.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());

        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getUserId(), user.getEmail(), user.getRoles());

        return userResponseDTO;
    }
}
