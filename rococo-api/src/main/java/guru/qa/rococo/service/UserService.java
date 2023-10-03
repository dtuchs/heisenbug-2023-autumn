package guru.qa.rococo.service;

import guru.qa.rococo.data.UserEntity;
import guru.qa.rococo.data.repository.UserRepository;
import guru.qa.rococo.exception.NotFoundException;
import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.model.util.StringAsBytes;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
  private static final String TOPIC = "user";
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void createNewUserIfNotPresent(String username) {
    userRepository.findByUsername(username)
        .orElseGet(() -> {
          UserEntity userEntity = new UserEntity();
          userEntity.setUsername(username);
          return userRepository.save(userEntity);
        });
  }

  @Transactional(readOnly = true)
  public @Nonnull Page<UserJson> getAll(@Nonnull Pageable pageable) {
    return userRepository.findAll(pageable).map(UserJson::fromEntity);
  }

  @Transactional(readOnly = true)
  public @Nonnull UserJson findUserById(@Nonnull String id) {
    return UserJson.fromEntity(
        userRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(NotFoundException::new)
    );
  }

  @Transactional
  public UserJson update(UserJson user) {
    UserEntity userEntity = getRequiredUser(user.username());
    userEntity.setFirstname(user.firstname());
    userEntity.setLastname(user.lastname());
    userEntity.setAvatar(
        new StringAsBytes(
            user.avatar()
        ).bytes()
    );
    return UserJson.fromEntity(
        userRepository.save(userEntity)
    );
  }

  private @Nonnull UserEntity getRequiredUser(@Nonnull String username) {
    return userRepository.findByUsername(username).orElseThrow(
        NotFoundException::new
    );
  }
}
