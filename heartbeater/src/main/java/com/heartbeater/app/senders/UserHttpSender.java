package com.heartbeater.app.senders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartbeater.app.domain.Heart;
import com.heartbeater.app.domain.Patient;
import com.heartbeater.app.exceptions.HttpSenderException;
import com.heartbeater.app.mappers.BeatMapper;

import java.io.IOException;
import java.util.List;

public class UserHttpSender extends AbstractHttpSender implements IUserSender {
    private ObjectMapper objectMapper;
    private BodyParser parser;
    private final String USER_AGENT = "Mozilla/5.0";
    // TODO: Get this from a property
    private String usersURL = "http://192.168.99.100:8000/users";
    private String usersBulkURL = "http://192.168.99.100:8000/beats/bulk";

    public UserHttpSender() {
        this.parser = new BodyParser();
        this.objectMapper = new ObjectMapper();
    }

    public List<Patient> getPatients() {
        try {
            String result = parser.parseIncomingBody(get(usersURL, USER_AGENT));
            return objectMapper.readValue(result, new TypeReference<List<Patient>>(){});
        } catch(HttpSenderException exception) {
            System.out.println(exception.getMessage());
            return null;
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public void sendBeats(Heart heart) {
        try {
            BeatMapper beatMapper = new BeatMapper(heart);
            String body = objectMapper.writeValueAsString(beatMapper);
            post(usersBulkURL, USER_AGENT, body);
        } catch(HttpSenderException exception) {
            System.out.println(exception.getMessage());
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        } 
    }
}