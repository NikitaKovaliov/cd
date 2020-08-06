package by.kovaliov.cd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ApplicationStarter {

  public static void main(String[] args) {
    ApiContextInitializer.init();
    SpringApplication.run(ApplicationStarter.class, args);
  }
}