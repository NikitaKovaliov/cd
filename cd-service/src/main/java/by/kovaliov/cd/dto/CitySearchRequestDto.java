package by.kovaliov.cd.dto;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitySearchRequestDto {

  @Pattern(regexp = "(?i)name|id")
  private String sortField = "id";
  @Pattern(regexp = "(?i)asc|desc")
  private String sortDirection = "asc";
  @Pattern.List({
      @Pattern(regexp = "\\d{1,2}"),
      @Pattern(regexp = "^(?!0*$).*$")
  })
  private String perPage = "5";
  @Pattern.List({
      @Pattern(regexp = "\\d{1,3}"),
      @Pattern(regexp = "^(?!0*$).*$")
  })
  private String page = "1";
  private String name;

  public CitySearchRequestDto(String name) {
    this.name = name;
  }
}