package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.model.request.PushNotificationRequest;
import app.BinarFudBackend.service.FCMService;
import app.BinarFudBackend.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    @Autowired
    private FCMService fcmService;

    @Override
    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationCustomDataWithTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataCustom(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationCustomDataWithTopicWithSpecificJson(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataWithSpecificJsonFormat(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "Notification for pending work");
        pushData.put("message", "pls complete your pending task immediately");
        pushData.put("image", "https://github.com/Nanang-Wahyudi/BinarFud_v7/blob/main/src/main/resources/BinarFud.png");
        pushData.put("timestamp", "2020-07-11 19:23:21");
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return pushData;
    }

    private Map<String, String> getSamplePayloadDataCustom() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "Notification for pending work-custom");
        pushData.put("message", "pls complete your pending task immediately-custom");
        pushData.put("image", "https://github.com/Nanang-Wahyudi/BinarFud_v7/blob/main/src/main/resources/BinarFud.png");
        pushData.put("timestamp", String.valueOf(new Date()));
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return pushData;
    }

    private Map<String, String> getSamplePayloadDataWithSpecificJsonFormat() {
        Map<String, String> pushData = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        ArrayList<Map<String, String>> payload = new ArrayList<>();
        Map<String, String> article_data = new HashMap<>();

        pushData.put("title", "Example Title Testing");
        pushData.put("message", "Example Message Testing");
        pushData.put("image", "Image.jpg");
        pushData.put("timestamp", "Example Timestamp Testing");
        article_data.put("article_data", "Example Article Data Testing");
        payload.add(article_data);
        pushData.put("payload", String.valueOf(payload));
        data.put("data", String.valueOf(pushData));
        return data;
    }

}
