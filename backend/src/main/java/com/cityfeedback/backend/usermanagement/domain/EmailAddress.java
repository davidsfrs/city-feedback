package com.cityfeedback.backend.usermanagement.domain;

import java.util.Locale;
import java.util.regex.Pattern;

public record EmailAddress(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@" +
                    "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?" +
                    "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+$"
    );

    public EmailAddress {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Email address must not be null"
            );
        }

        value = value.strip().toLowerCase(Locale.ROOT);

        if (!EMAIL_PATTERN.matcher(value).matches()
                || value.contains("..")) {
            throw new IllegalArgumentException(
                    "Invalid email address"
            );
        }
    }
}