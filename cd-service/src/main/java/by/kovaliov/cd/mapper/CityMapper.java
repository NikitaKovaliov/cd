package by.kovaliov.cd.mapper;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.model.City;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CityMapper {

  CityDto cityToCityDto(City city);

  City cityDtoToCity(CityDto city);

  List<CityDto> cityListToCityDtoList(List<City> cities);
}