package com.amm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway", url = "${api.gateway.url}")
public interface ApiGatewayService {

    @GetMapping("${api.gateway.endpoints.staff}")
    String getStaff();

    @GetMapping("${api.gateway.endpoints.appointments}")
    String getAppointments();

    @GetMapping("${api.gateway.endpoints.appointments}/user/{userId}")
    String getAppointmentsByUser(@PathVariable String userId);
}