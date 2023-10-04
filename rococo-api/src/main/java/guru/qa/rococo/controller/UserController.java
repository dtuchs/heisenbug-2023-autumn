package guru.qa.rococo.controller;


import guru.qa.rococo.model.UserJson;
import guru.qa.rococo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public UserJson getCurrent(
//       @AuthenticationPrincipal Jwt principal
  ) {
//    return userService.getByUsername(principal.getClaim("sub"));
    return UserJson.empty();
  }

  @PatchMapping("/")
  public UserJson updateUser(@RequestBody UserJson user
//      , @AuthenticationPrincipal Jwt principal
  ) {
//    String username = principal.getClaim("sub");
//    if (!Objects.equals(username, user.username())) {
//      throw new ResponseStatusException(FORBIDDEN, "Can`t access to another user");
//    }
    return userService.update(user);
  }
}
