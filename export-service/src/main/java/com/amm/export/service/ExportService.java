package com.amm.export.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExportService {

    @Value("${export.location:./exports}")
    private String exportLocation;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(cron = "${export.schedule:0 0 2 * * MON}")
    public void scheduledExport() {
        performExport();
    }

    public void performExport() {
        try {
            // Создаем директорию если не существует
            Path exportPath = Paths.get(exportLocation);
            if (!Files.exists(exportPath)) {
                Files.createDirectories(exportPath);
            }

            // Генерируем имя файла с timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = String.format("clinic_export_%s.json", timestamp);
            File exportFile = new File(exportPath.toFile(), fileName);

            // Создаем объект для экспорта
            ExportData exportData = new ExportData();
            // TODO: Заполнить данными из других сервисов

            // Записываем в файл
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(exportFile, exportData);

            System.out.println("Export completed: " + exportFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }

    // Внутренний класс для структуры экспорта
    public static class ExportData {
        // TODO: Добавить поля для данных из всех сервисов
        private String exportTime = LocalDateTime.now().toString();
        private String version = "1.0";

        // Геттеры и сеттеры
        public String getExportTime() { return exportTime; }
        public void setExportTime(String exportTime) { this.exportTime = exportTime; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
    }
}