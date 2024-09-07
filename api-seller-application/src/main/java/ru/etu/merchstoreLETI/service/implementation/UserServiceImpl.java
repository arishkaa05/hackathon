package ru.etu.merchstoreLETI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.etu.merchstoreLETI.domain.enums.Role;
import ru.etu.merchstoreLETI.domain.exception.ResourceNotFoundException;
import ru.etu.merchstoreLETI.domain.model.*;
import ru.etu.merchstoreLETI.repository.*;
import ru.etu.merchstoreLETI.service.interf.PaginationService;
import ru.etu.merchstoreLETI.service.interf.UserService;
import ru.etu.merchstoreLETI.util.EntityDtoUtil;
import ru.etu.merchstoreLETI.util.UserDtoUtil;
import ru.etu.merchstoreLETI.web.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractCRUDService<UserDto, Long,User> implements UserService, PaginationService<UserDto,Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoUtil userDtoUtil;
    private final OrderRepository orderRepository;
    private final BucketRepository bucketRepository;
    private final CommentRepository commentRepository;
    private final FavoriteItemRepository favoriteItemRepository;
    @Override
    JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    EntityDtoUtil<UserDto, User> getEntityDtoUtil() {
        return userDtoUtil;
    }


    @Override
    @Transactional
    public void deleteEntity(UserDto objectDto) {
        User user = userRepository.findById(objectDto.getId())
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found"));
        if(user!=null){
            List<Order> userOrders = orderRepository.findByUserId(objectDto.getId());
            for (Order order: userOrders) order.setUser(null);
            List<Bucket> buckets = bucketRepository.findByUserId(objectDto.getId());
            for(Bucket bucket: buckets) bucketRepository.delete(bucket);
            List<Comment> comments = commentRepository.findByUserId(objectDto.getId());
            for(Comment comment: comments) comment.setUser(null);
            List<FavoriteItem> favoriteItems = favoriteItemRepository.findByUserId(objectDto.getId());
            for(FavoriteItem favoriteItem: favoriteItems) favoriteItemRepository.delete(favoriteItem);
        }
        super.deleteEntity(objectDto);
    }

    @Override
    @Transactional
    public void createEntity(UserDto userDto) {
        if(userRepository.findUserByEmail(userDto.getEmail()).isPresent()){
            throw new IllegalStateException("Пользователь уже зарегистрирован!.");
        }
        if(!userDto.getPassword().equals(userDto.getPasswordConfirmation())){
            throw new IllegalStateException("Пароль и подтверждение пароля не совпадают!");
        }
        User user = getEntityDtoUtil().toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto updateEntity(UserDto objectDto) {
        User user = getEntityDtoUtil().toEntity(objectDto);
        user.setPassword(passwordEncoder.encode(objectDto.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        User entitySave = userRepository.save(user);
        UserDto dto = getEntityDtoUtil().toDto(entitySave);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден"));
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user =  userRepository.findUserByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Пользователь не найден"));
        UserDto userDto = userDtoUtil.toDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> searchUsers(LocalDateTime minCreateDate, LocalDateTime maxCreateDate) {
        List<User> matchingUsers = userRepository.searchUsers(minCreateDate, maxCreateDate);
        return userDtoUtil.toDtoList(matchingUsers);
    }

    @Override
    public List<String> findUserRoles(Authentication authentication) {
        List<String> userRoles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return userRoles;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll(int pageNumber,String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,10);
        if(searchKey.equals("")) {
            List<User> objects = new ArrayList<>();
            getRepository().findAll(pageable).forEach(objects::add);
            List<UserDto> entityDtoList = getEntityDtoUtil().toDtoList(objects);
            return entityDtoList;
        }else {
            List<User> objects = new ArrayList<>();
            userRepository.findByCityContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                    searchKey,searchKey,pageable
            ).forEach(objects::add);
            List<UserDto> entityDtoList = getEntityDtoUtil().toDtoList(objects);
            return entityDtoList;
        }
    }
}

