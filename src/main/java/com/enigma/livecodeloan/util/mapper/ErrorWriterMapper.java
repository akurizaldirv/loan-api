package com.enigma.livecodeloan.util.mapper;

import com.enigma.livecodeloan.model.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.util.Date;

public class ErrorWriterMapper {
    public static String mapToString(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(message, new Date());
        return objectMapper.writeValueAsString(errorResponse);
    }
}
