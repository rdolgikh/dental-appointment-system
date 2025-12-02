package com.amm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final CommandHandlerService commandHandlerService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    public TelegramBotService(CommandHandlerService commandHandlerService) {
        this.commandHandlerService = commandHandlerService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Received update: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getFrom().getFirstName();

            log.info("Message from {} (chatId: {}): {}", firstName, chatId, messageText);

            String response;

            switch (messageText) {
                case "/start":
                    response = "👋 Привет, " + firstName + "!\n" +
                            "Я бот клиники Beaver Teeth.\n" +
                            "Доступные команды:\n" +
                            "/help - помощь\n" +
                            "/doctors - список врачей\n" +
                            "/appointments - мои записи\n" +
                            "/info - информация о клинике";
                    break;

                case "/help":
                    response = "📋 Помощь:\n" +
                            "/start - начать работу\n" +
                            "/doctors - список врачей\n" +
                            "/appointments - мои записи\n" +
                            "/info - информация о клинике\n" +
                            "/contact - контакты клиники";
                    break;

                case "/doctors":
                    response = commandHandlerService.getDoctorsInfo();
                    break;

                case "/appointments":
                    response = commandHandlerService.getAppointmentsInfo(chatId.toString());
                    break;

                case "/info":
                    response = "🦷 Клиника Beaver Teeth\n" +
                            "Часы работы: Пн-Пт 9:00-20:00\n" +
                            "Адрес: ул. Стоматологическая, 123\n" +
                            "Телефон: +7 (999) 123-45-67\n" +
                            "Сайт: www.beaverteeth.ru";
                    break;

                case "/contact":
                    response = "📞 Контакты:\n" +
                            "Телефон: +7 (999) 123-45-67\n" +
                            "Email: info@beaverteeth.ru\n" +
                            "Telegram: @beaverteeth_support";
                    break;

                default:
                    response = "🤔 Не понимаю команду. Используйте /help для списка команд.";
            }

            sendMessage(chatId, response);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
            log.debug("Message sent to chatId {}: {}", chatId, text);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to chatId {}: {}", chatId, e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}