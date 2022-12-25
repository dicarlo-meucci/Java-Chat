package com.itismeucci;

public final class Constants {
    public static final String ACTION_CONNECT = "connect";
    public static final String ACTION_DISCONNECT = "disconnect";
    public static final String TARGET_EVERYONE = "*";
    public static final String TYPE_COMMAND = "command";
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_NOTIFICATION = "notification";
    public static final String TYPE_RESPONSE = "response";
    public static final String TYPE_COMMAND_RESPONSE = "command-response";
    public static final int STATUS_VALID = 200;
    public static final int STATUS_BAD_PARAMETERS = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_INVALID = 422;
    public static final String RESPONSE_NAME_ALREADY_IN_USE = "This name is already in use, try changing it";
    public static final String RESPONSE_INVALID = "Could not parse request object";
    public static final String RESPONSE_VALID = "OK";
    public static final String RESPONSE_NOT_FOUND = "Target not found, the specified target may not exist or you're the only client connected";
    public static final String RESPONSE_NAME_NOT_VALID = "Your name is invalid (either too short, long or contains forbidden characters";
}