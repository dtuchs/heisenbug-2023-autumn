package guru.qa.rococo.controller;


import guru.qa.rococo.model.SessionJson;
import guru.qa.rococo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/session")
public class SessionController {

  private final UserService userService;

  @Autowired
  public SessionController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public SessionJson session(
      //       @AuthenticationPrincipal Jwt principal
  ) {
//    if (principal != null) {
//      final String username = principal.getClaim("sub");
//      userService.createNewUserIfNotPresent(username);
//      return new SessionJson(
//          username,
//          Date.from(principal.getIssuedAt()),
//          Date.from(principal.getExpiresAt())
//      );
//    } else {
    return SessionJson.empty();
//    }
  }
}
