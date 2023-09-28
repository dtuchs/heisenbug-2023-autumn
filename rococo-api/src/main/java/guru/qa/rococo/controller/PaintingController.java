package guru.qa.rococo.controller;


import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
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
import org.springframework.web.bind.annotation.ValueConstants;


@RestController
@RequestMapping("/api/painting")
public class PaintingController {

  private final PaintingService paintingService;

  @Autowired
  public PaintingController(PaintingService paintingService) {
    this.paintingService = paintingService;
  }

  @GetMapping()
  public Page<PaintingJson> getAll(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
    return ValueConstants.DEFAULT_NONE.equals(title)
        ? paintingService.getAll(pageable)
        : paintingService.getAll(title, pageable);
  }

  @GetMapping("/{id}")
  public PaintingJson findPaintingById(@PathVariable("id") String id) {
    return paintingService.findArtistById(id);
  }

  @PatchMapping("/")
  public PaintingJson updatePainting(@RequestBody PaintingJson painting) {
    return paintingService.update(painting);
  }

  @PostMapping("/")
  public PaintingJson addPainting(@RequestBody PaintingJson painting) {
    return paintingService.add(painting);
  }
}
