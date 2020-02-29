package com.jdbcservlets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcservlets.dto.ClientCreateDto;
import com.jdbcservlets.dto.ClientResponseDto;
import com.jdbcservlets.exeptions.BadRequestException;
import com.jdbcservlets.exeptions.IORuntimeException;
import com.jdbcservlets.service.ClientService;
import com.jdbcservlets.service.impl.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.stream.Collectors.joining;

@Slf4j
@WebServlet("/clients/*")
public class ClientController extends HttpServlet {

    private final ClientService clientService = new ClientServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object responseBody;
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            responseBody = clientService.findAll();
        } else {
            String[] splits = pathInfo.split("/");

            if (splits.length != 2) {
                throw new BadRequestException("Invalid request path!");
            }

            String clientId = splits[1];
            responseBody = clientService.findById(Long.valueOf(clientId));
        }

        sendAsJson(resp, responseBody);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();

        if (!(pathInfo == null || pathInfo.equals("/"))) {
            throw new BadRequestException("Invalid request path!");
        }

        ClientCreateDto clientCreateDto;
        try {
            String payload = req.getReader().lines().collect(joining());
            clientCreateDto = objectMapper.readValue(payload, ClientCreateDto.class);
        } catch (IOException e) {
            throw new IORuntimeException("Cannot read payload.");
        }
        ClientResponseDto responseBody = clientService.create(clientCreateDto);

        sendAsJson(resp, responseBody);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length != 2) {
            throw new BadRequestException("Invalid request path!");
        }

        String clientId = pathInfo.split("/")[1];
        clientService.deleteById(Long.valueOf(clientId));
    }

    private void sendAsJson(HttpServletResponse response, Object obj) {
        try {
            String body = objectMapper.writeValueAsString(obj);
            response.setContentType("application/json");
            response.getWriter().append(body).flush();
        } catch (IOException e) {
            throw new IORuntimeException("Cannot write response.");
        }
    }
}
