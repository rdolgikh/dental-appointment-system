package com.amm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class CommandHandlerService {

    private final ApiGatewayService apiGatewayService;
    private final ObjectMapper objectMapper;

    public CommandHandlerService(ApiGatewayService apiGatewayService) {
        this.apiGatewayService = apiGatewayService;
        this.objectMapper = new ObjectMapper();
    }

    public String getDoctorsInfo() {
        try {
            String staffJson = apiGatewayService.getStaff();
            log.debug("Received staff JSON: {}", staffJson);

            JsonNode root = objectMapper.readTree(staffJson);
            StringBuilder response = new StringBuilder("👨‍⚕️ Наши врачи:\n\n");

            if (root.isArray()) {
                int count = 0;
                for (JsonNode doctor : root) {
                    if (count >= 5) break; // Ограничим 5 врачами

                    String name = doctor.path("name").asText("Неизвестно");
                    String specialization = doctor.path("specialization").asText("Стоматолог");
                    String experience = doctor.path("experience").asText("Опыт не указан");

                    response.append("• ").append(name).append("\n")
                            .append("  Специализация: ").append(specialization).append("\n")
                            .append("  Опыт: ").append(experience).append("\n\n");
                    count++;
                }

                if (count == 0) {
                    response.append("Врачи не найдены");
                }
            } else {
                response.append("Информация о врачах временно недоступна");
            }

            return response.toString();

        } catch (Exception e) {
            log.error("Error getting doctors info: {}", e.getMessage(), e);
            return "😕 Не удалось получить информацию о врачах. Попробуйте позже.";
        }
    }

    public String getAppointmentsInfo(String userId) {
        try {
            String appointmentsJson = apiGatewayService.getAppointmentsByUser(userId);
            log.debug("Received appointments JSON: {}", appointmentsJson);

            JsonNode root = objectMapper.readTree(appointmentsJson);
            StringBuilder response = new StringBuilder("📅 Ваши записи:\n\n");

            if (root.isArray()) {
                int count = 0;
                for (JsonNode appointment : root) {
                    if (count >= 5) break; // Ограничим 5 записями

                    String date = appointment.path("date").asText("Дата не указана");
                    String time = appointment.path("time").asText("Время не указано");
                    String doctor = appointment.path("doctorName").asText("Врач не указан");
                    String service = appointment.path("service").asText("Услуга не указана");

                    response.append("• ").append(date).append(" в ").append(time).append("\n")
                            .append("  Врач: ").append(doctor).append("\n")
                            .append("  Услуга: ").append(service).append("\n\n");
                    count++;
                }

                if (count == 0) {
                    response.append("У вас нет активных записей");
                }
            } else {
                response.append("У вас нет активных записей");
            }

            return response.toString();

        } catch (Exception e) {
            log.error("Error getting appointments info: {}", e.getMessage(), e);
            return "😕 Не удалось получить информацию о записях. Попробуйте позже.";
        }
    }
}