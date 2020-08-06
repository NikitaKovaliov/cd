package by.kovaliov.cd.bot;

import by.kovaliov.cd.dto.CityDto;
import by.kovaliov.cd.dto.CitySearchRequestDto;
import by.kovaliov.cd.service.CityService;
import java.util.List;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@ConfigurationProperties(prefix = "bot")
public class CityBot extends TelegramLongPollingBot {

  private static final String START_COMMAND = "/start";
  private static final String START_MESSAGE = "Добро пожаловать в справочный бот с информацией по городам мира! Для "
      + "работы просто отправьте боту название города.";
  private static final String NO_CITY_DATA_MESSAGE = "К сожалению данные о \"%s\" не найдены.";
  private static final Logger LOGGER = LoggerFactory.getLogger(CityBot.class);

  private final CityService cityService;
  @Setter
  private String token;
  @Setter
  private String username;

  public CityBot(CityService cityService) {
    this.cityService = cityService;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText();
      String response;
      List<CityDto> cityList;
      response = messageText.equals(START_COMMAND) ?
          START_MESSAGE : (cityList = cityService.findAll(new CitySearchRequestDto(messageText))).size() == 0 ?
          String.format(NO_CITY_DATA_MESSAGE, messageText) : cityList.get(0).getDescription();
      long chatId = update.getMessage().getChatId();
      SendMessage message = new SendMessage()
          .setChatId(chatId)
          .setText(response);
      try {
        execute(message);
      } catch (TelegramApiException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }

  @Override
  public String getBotUsername() {
    return username;
  }
}