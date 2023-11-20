package app.BinarFudBackend.service;

import app.BinarFudBackend.model.request.PushNotificationRequest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface FCMService {

    void sendMessage(Map<String, String> data, PushNotificationRequest request) throws InterruptedException, ExecutionException;

    void sendMessageCustomDataWithTopic(Map<String, String> data, PushNotificationRequest request) throws InterruptedException, ExecutionException;

    void sendMessageWithoutData(PushNotificationRequest request) throws InterruptedException, ExecutionException;

    void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException;

}
