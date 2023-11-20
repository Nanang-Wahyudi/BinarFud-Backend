package app.BinarFudBackend.config;

import app.BinarFudBackend.controller.PushNotificationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    private PushNotificationController pushNotificationController;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "0 0 12 * * *")
    public void remindMakanSiang() {
        pushNotificationController.sendAutomaticNotification();
        log.info("{} - Waktunya makan siang! Ada promo nih di BinarFud.", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 0 18 * * *")
    public void remindMakanMalam() {
        pushNotificationController.sendAutomaticNotification();
        log.info("{} - Waktunya makan malam! Ada promo nih di BinarFud.", dateTimeFormatter.format(LocalDateTime.now()));
    }

}
