package com.github.iamhi.hizone.lite.authentication.core;

import com.github.iamhi.hizone.lite.authentication.database.UserEntity;
import com.github.iamhi.hizone.lite.authentication.database.UserRepository;
import com.github.iamhi.hizone.lite.authentication.domain.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
record UserServiceImpl(
    UserRepository userRepository,
    BCryptPasswordEncoder bCryptPasswordEncoder
) implements UserService {

    @Override
    public Optional<UserDto> createUser(String username, String password, String email) {
        UserEntity userEntity = generateUserEntity();

        userEntity.setUsername(username);
        userEntity.setName(username);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));
        userEntity.setEmail(email);
        userEntity.setRoles("ROLE_BASIC");

        UserEntity savedEntity = userRepository.save(userEntity);

        return Optional.of(savedEntity).map(this::mapEntityToDto);
    }

    @Override
    public Optional<InternalUserDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapEntityToInternalDto);
    }

    @Override
    public boolean attemptUserLogin(String username, String password) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            return bCryptPasswordEncoder.matches(password, userEntity.getPassword());
        }

        return false;
    }

    private UserEntity generateUserEntity() {
        UserEntity userEntity = new UserEntity();

        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());

        return userEntity;
    }

    private InternalUserDto mapEntityToInternalDto(UserEntity userEntity) {
        return new InternalUserDto(
            userEntity.getUuid(),
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.getName(),
            userEntity.getEmail(),
            Arrays.stream(StringUtils.defaultString(userEntity.getRoles()).split(",")).toList(),
            userEntity.getCreatedAt(),
            userEntity.getUpdatedAt()
        );
    }

    private UserDto mapEntityToDto(UserEntity userEntity) {
        return new UserDto(
            userEntity.getUuid(),
            userEntity.getUsername(),
            userEntity.getName(),
            userEntity.getEmail(),
            Arrays.stream(StringUtils.defaultString(userEntity.getRoles()).split(",")).toList(),
            userEntity.getCreatedAt(),
            userEntity.getUpdatedAt()
        );
    }
}
