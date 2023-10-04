package guru.qa.rococo.controller;


import guru.qa.rococo.model.SessionJson;
import guru.qa.rococo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static java.util.Objects.requireNonNull;


@RestController
@RequestMapping("/api/session")
public class SessionController {

  private final UserService userService;

  @Autowired
  public SessionController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public SessionJson session(@AuthenticationPrincipal Jwt principal) {
    if (principal != null) {
      final String username = principal.getClaim("sub");
      userService.createNewUserIfNotPresent(username);
      return new SessionJson(
          username,
          Date.from(requireNonNull(principal.getIssuedAt())),
          Date.from(requireNonNull(principal.getExpiresAt()))
      );
    } else {
      return SessionJson.empty();
    }
  }
}
