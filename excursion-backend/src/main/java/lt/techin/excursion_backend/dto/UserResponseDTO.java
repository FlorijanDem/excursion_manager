package lt.techin.excursion_backend.dto;

import lt.techin.excursion_backend.model.Role;

import java.util.List;
import java.util.Set;

public record UserResponseDTO(long id,
                              String email,
                              Set<Role> roles
) {
}
