package by.kovaliov.cd.service;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import java.util.List;

public interface CityService {

  List<CityDto> findAll(CitySearchRequestDto citySearchRequestDto);

  CityDto findById(Long id);

  CityDto create(CityDto cityDto);

  CityDto update(CityDto cityDto);

  void delete(Long id);
}