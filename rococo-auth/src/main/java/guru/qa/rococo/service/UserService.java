package guru.qa.rococo.service;

import guru.qa.rococo.data.Authority;
import guru.qa.rococo.data.AuthorityEntity;
import guru.qa.rococo.data.UserEntity;
import guru.qa.rococo.data.repository.UserRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public @Nonnull
  String registerUser(@Nonnull String username, @Nonnull String password) {
    UserEntity userEntity = new UserEntity();
    userEntity.setEnabled(true);
    userEntity.setAccountNonExpired(true);
    userEntity.setCredentialsNonExpired(true);
    userEntity.setAccountNonLocked(true);
    userEntity.setUsername(username);
    userEntity.setPassword(passwordEncoder.encode(password));

    AuthorityEntity readAuthorityEntity = new AuthorityEntity();
    readAuthorityEntity.setAuthority(Authority.read);
    AuthorityEntity writeAuthorityEntity = new AuthorityEntity();
    writeAuthorityEntity.setAuthority(Authority.write);

    userEntity.addAuthorities(readAuthorityEntity, writeAuthorityEntity);
    return userRepository.save(userEntity).getUsername();
  }
}
