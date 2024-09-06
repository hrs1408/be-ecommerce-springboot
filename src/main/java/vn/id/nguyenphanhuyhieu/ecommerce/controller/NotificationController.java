package vn.id.nguyenphanhuyhieu.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import vn.id.nguyenphanhuyhieu.ecommerce.service.NotificationService;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody String message) {
        notificationService.sendNotification(message);
    }
}
