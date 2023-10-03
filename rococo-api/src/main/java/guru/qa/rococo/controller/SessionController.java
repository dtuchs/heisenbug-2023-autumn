package guru.qa.rococo.controller;


import guru.qa.rococo.model.SessionJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/session")
public class SessionController {

  @GetMapping()
  public SessionJson session(
      //       @AuthenticationPrincipal Jwt principal
  ) {
//    if (principal != null) {
//      return new SessionJson(
//          principal.getClaim("sub"),
//          Date.from(principal.getIssuedAt()),
//          Date.from(principal.getExpiresAt())
//      );
//    } else {
    return SessionJson.empty();
//    }
  }
}
