package ru.etu.merchstoreLETI.service.interf;


import org.springframework.security.core.Authentication;
import ru.etu.merchstoreLETI.domain.model.User;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService extends CRUDService<UserDto,Long>,PaginationService<UserDto,Long> {
    User getByEmail(String email);

    UserDto getUserDtoByEmail(String email);

    List<UserDto> searchUsers(LocalDateTime minCreateDate,LocalDateTime maxCreateDate);

    List<String> findUserRoles(Authentication authentication);
}

