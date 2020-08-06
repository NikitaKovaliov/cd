package by.kovaliov.cd.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {

  @Null
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String description;
}