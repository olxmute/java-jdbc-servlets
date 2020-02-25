package com.jdbcservlets.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Pattern;

@UtilityClass
public class GenerationUtils {

    private final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");

    public String generatePassword() {
        String password;
        do {
            password = RandomStringUtils.randomAlphanumeric(8);
        } while (!PASSWORD_REGEX.matcher(password).matches());

        return password;
    }

}
