package by.kovaliov.cd.controller;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import by.kovaliov.cd.service.CityService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping
  public ResponseEntity<List<CityDto>> findAll(@Valid CitySearchRequestDto citySearchRequestDto) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cityService.findAll(citySearchRequestDto));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CityDto> create(@Valid @RequestBody CityDto cityDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(cityService.create(cityDto));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<CityDto> findById(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cityService.findById(id));
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CityDto> update(@PathVariable Long id, @Valid @RequestBody CityDto cityDto) {
    cityDto.setId(id);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(cityService.update(cityDto));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cityService.delete(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }
}