package com.amm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotCommandRequest {
    private Long chatId;
    private String command;
    private String parameters;
    private String userName;
}