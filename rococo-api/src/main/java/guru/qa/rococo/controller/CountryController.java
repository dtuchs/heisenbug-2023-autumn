package guru.qa.rococo.controller;


import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.CountryService;
import guru.qa.rococo.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/country")
public class CountryController {

  private final CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @GetMapping()
  public Page<CountryJson> getAll(@RequestParam(required = false) String title,
                                  @PageableDefault Pageable pageable) {
    return countryService.getAll(title, pageable);
  }

  @GetMapping("/{id}")
  public CountryJson findCountryById(@PathVariable("id") String id) {
    return countryService.findCountryById(id);
  }

  @GetMapping("/name/{name}")
  public CountryJson findCountryByName(@PathVariable("name") String name) {
    return countryService.findCountryByName(name);
  }
}
