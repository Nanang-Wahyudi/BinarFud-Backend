package app.BinarFudBackend.service;

import app.BinarFudBackend.model.request.PushNotificationRequest;

public interface PushNotificationService {

    void sendPushNotification(PushNotificationRequest request);

    void sendPushNotificationCustomDataWithTopic(PushNotificationRequest request);

    void sendPushNotificationCustomDataWithTopicWithSpecificJson(PushNotificationRequest request);

    void sendPushNotificationWithoutData(PushNotificationRequest request);

    void sendPushNotificationToToken(PushNotificationRequest request);

}
