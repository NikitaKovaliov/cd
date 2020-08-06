package by.kovaliov.cd.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import by.kovaliov.cd.exception.ServerException;
import by.kovaliov.cd.mapper.CityMapper;
import by.kovaliov.cd.model.City;
import by.kovaliov.cd.repository.CityRepository;
import by.kovaliov.cd.service.impl.CityServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

  private static List<City> cityList;
  private static List<CityDto> cityDtoList;
  private static City city;
  private static CityDto cityDto;

  @Mock
  private CityRepository cityRepository;
  @Mock
  private CityMapper cityMapper;
  @InjectMocks
  private CityServiceImpl cityService;

  @BeforeClass
  public static void init() {
    city = new City(10L, "City Name", "City description");
    cityList = Collections.singletonList(city);
    cityDto = new CityDto(10L, "City Name", "City description");
    cityDtoList = Collections.singletonList(cityDto);
  }

  @Test
  public void findAllWithNullNameParamReturnsCityDtoListTest() {
    when(cityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(cityList));
    when(cityMapper.cityListToCityDtoList(cityList)).thenReturn(cityDtoList);
    List<CityDto> actual = cityService.findAll(new CitySearchRequestDto());
    Assert.assertEquals(cityDtoList, actual);
  }

  @Test
  public void findAllWithNameParamReturnsCityDtoListTest() {
    when(cityRepository.findByNameIgnoreCase(eq(city.getName()), any(Pageable.class))).thenReturn(cityList);
    when(cityMapper.cityListToCityDtoList(cityList)).thenReturn(cityDtoList);
    CitySearchRequestDto searchRequestDto = new CitySearchRequestDto(city.getName());
    List<CityDto> actual = cityService.findAll(searchRequestDto);
    Assert.assertEquals(cityDtoList, actual);
  }

  @Test
  public void findByIdReturnsCityDtoTest() {
    when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
    when(cityMapper.cityToCityDto(city)).thenReturn(cityDto);
    CityDto actual = cityService.findById(city.getId());
    Assert.assertEquals(cityDto, actual);
  }

  @Test(expected = ServerException.class)
  public void findByIdCityNotFoundThrowsExceptionDtoTest() {
    when(cityRepository.findById(city.getId())).thenReturn(Optional.empty());
    cityService.findById(city.getId());
  }

  @Test
  public void createReturnsCityDtoTest() {
    when(cityMapper.cityDtoToCity(cityDto)).thenReturn(city);
    when(cityRepository.save(city)).thenReturn(city);
    when(cityMapper.cityToCityDto(city)).thenReturn(cityDto);
    CityDto actual = cityService.create(cityDto);
    Assert.assertEquals(cityDto, actual);
  }

  @Test(expected = ServerException.class)
  public void createCityWithSuchNameAlreadyExistsThrowsExceptionTest() {
    when(cityMapper.cityDtoToCity(cityDto)).thenReturn(city);
    when(cityRepository.save(city)).thenThrow(new DataIntegrityViolationException("exception"));
    cityService.create(cityDto);
  }

  @Test
  public void updateReturnsCityDtoTest() {
    when(cityRepository.existsById(city.getId())).thenReturn(true);
    when(cityMapper.cityDtoToCity(cityDto)).thenReturn(city);
    when(cityRepository.save(city)).thenReturn(city);
    when(cityMapper.cityToCityDto(city)).thenReturn(cityDto);
    CityDto actual = cityService.update(cityDto);
    Assert.assertEquals(cityDto, actual);
  }

  @Test(expected = ServerException.class)
  public void updateCityWithSuchIdNotExistsThrowsExceptionTest() {
    when(cityRepository.existsById(city.getId())).thenReturn(false);
    cityService.update(cityDto);
  }

  @Test(expected = ServerException.class)
  public void updateCityWithSuchNameAlreadyExistsThrowsExceptionTest() {
    when(cityRepository.existsById(city.getId())).thenReturn(true);
    when(cityMapper.cityDtoToCity(cityDto)).thenReturn(city);
    when(cityRepository.save(city)).thenThrow(new DataIntegrityViolationException("exception"));
    cityService.update(cityDto);
  }

  @Test
  public void deleteCityDeletedTest() {
    cityService.delete(city.getId());
    verify(cityRepository).deleteById(city.getId());
  }

  @Test(expected = ServerException.class)
  public void deleteCityWithSuchIdNotExistsThrowsExceptionTest() {
    doThrow(new EmptyResultDataAccessException(1)).when(cityRepository).deleteById(city.getId());
    cityService.delete(city.getId());
  }
}