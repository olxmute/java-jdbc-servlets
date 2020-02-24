package com.jdbcservlets.service.impl;

import com.jdbcservlets.service.GenerationService;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Pattern;

public class GenerationServiceImpl implements GenerationService {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");

    @Override
    public String generatePassword() {
        String password;
        do {
            password = RandomStringUtils.randomAlphanumeric(8);
        } while (!PASSWORD_REGEX.matcher(password).matches());

        return password;
    }
}
