package by.kovaliov.cd.service.impl;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import by.kovaliov.cd.exception.ExceptionType;
import by.kovaliov.cd.exception.ServerException;
import by.kovaliov.cd.mapper.CityMapper;
import by.kovaliov.cd.repository.CityRepository;
import by.kovaliov.cd.service.CityService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

  private final CityRepository cityRepository;
  private final CityMapper cityMapper;

  public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
    this.cityRepository = cityRepository;
    this.cityMapper = cityMapper;
  }

  @Override
  public List<CityDto> findAll(CitySearchRequestDto citySearchRequestDto) {
    return citySearchRequestDto.getName() == null ?
        cityMapper
            .cityListToCityDtoList(cityRepository.findAll(preparePageableObject(citySearchRequestDto)).getContent()) :
        cityMapper.
            cityListToCityDtoList(cityRepository
                .findByNameIgnoreCase(citySearchRequestDto.getName(), preparePageableObject(citySearchRequestDto)));

  }

  private Pageable preparePageableObject(CitySearchRequestDto citySearchRequestDto) {
    return PageRequest
        .of(Integer.parseInt(citySearchRequestDto.getPage()) - 1, Integer.parseInt(citySearchRequestDto.getPerPage()),
            Sort.by(Direction.fromString(citySearchRequestDto.getSortDirection()),
                citySearchRequestDto.getSortField()));
  }

  @Override
  public CityDto findById(Long id) {
    return cityMapper.cityToCityDto(
        cityRepository.findById(id).orElseThrow(() -> new ServerException(ExceptionType.RESOURCE_NOT_FOUND)));
  }

  @Override
  public CityDto create(CityDto cityDto) {
    try {
      return saveCity(cityDto);
    } catch (DataIntegrityViolationException e) {
      throw new ServerException(ExceptionType.RESOURCE_ALREADY_EXISTS);
    }
  }

  @Override
  public CityDto update(CityDto cityDto) {
    try {
      if (!cityRepository.existsById(cityDto.getId())) {
        throw new ServerException(ExceptionType.RESOURCE_NOT_FOUND);
      }
      return saveCity(cityDto);
    } catch (DataIntegrityViolationException e) {
      throw new ServerException(ExceptionType.RESOURCE_ALREADY_EXISTS);
    }
  }

  private CityDto saveCity(CityDto cityDto) {
    return cityMapper.cityToCityDto(cityRepository.save(cityMapper.cityDtoToCity(cityDto)));
  }

  @Override
  public void delete(Long id) {
    try {
      cityRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ServerException(ExceptionType.RESOURCE_NOT_FOUND);
    }
  }
}