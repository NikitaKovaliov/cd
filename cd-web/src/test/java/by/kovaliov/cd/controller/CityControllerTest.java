package by.kovaliov.cd.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import by.kovaliov.cd.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
@ComponentScan(basePackages = {"by.kovaliov.cd"})
public class CityControllerTest {

  private static CityDto cityDto;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CityService cityService;


  @BeforeClass
  public static void init() {
    cityDto = new CityDto(10L, "name", "text");
  }

  @Test
  public void findAllWithoutParamsReturnsCityListTest() throws Exception {
    when(cityService.findAll(any(CitySearchRequestDto.class))).thenReturn(Collections.singletonList(cityDto));
    mockMvc
        .perform(get("/cities"))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(Collections.singletonList(cityDto))));
  }

  @Test
  public void createWithIdValueReturnsBadRequestTest() throws Exception {
    mockMvc
        .perform(post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createWithoutNameReturnsBadRequestTest() throws Exception {
    CityDto cityDto = new CityDto();
    cityDto.setDescription("description");
    mockMvc
        .perform(post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createWithoutDescriptionReturnsBadRequestTest() throws Exception {
    CityDto cityDto = new CityDto();
    cityDto.setName("name");
    mockMvc
        .perform(post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createWithCorrectBodyReturnsCityDtoTest() throws Exception {
    CityDto city = new CityDto(null, "name", "description");
    when(cityService.create(city)).thenReturn(cityDto);
    mockMvc
        .perform(post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(city)))
        .andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(cityDto)));
  }

  @Test
  public void findByIdReturnsCityDtoTest() throws Exception {
    when(cityService.findById(10L)).thenReturn(cityDto);
    mockMvc
        .perform(get("/cities/10"))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(cityDto)));
  }

  @Test
  public void updateWithIdValueReturnsBadRequestTest() throws Exception {
    mockMvc
        .perform(put("/cities/10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateWithoutNameReturnsBadRequestTest() throws Exception {
    CityDto cityDto = new CityDto();
    cityDto.setDescription("description");
    mockMvc
        .perform(put("/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateWithoutDescriptionReturnsBadRequestTest() throws Exception {
    CityDto cityDto = new CityDto();
    cityDto.setName("name");
    mockMvc
        .perform(put("/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(cityDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateWithCorrectBodyReturnsCityDtoTest() throws Exception {
    CityDto city = new CityDto(null, "name", "description");
    when(cityService.update(any(CityDto.class))).thenReturn(cityDto);
    mockMvc
        .perform(put("/cities/15")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(city)))
        .andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(cityDto)));
  }

  @Test
  public void deleteReturnsOkTest() throws Exception {
    when(cityService.update(any(CityDto.class))).thenReturn(cityDto);
    mockMvc
        .perform(delete("/cities/15"))
        .andExpect(status().isOk());
    verify(cityService).delete(15L);
  }
}