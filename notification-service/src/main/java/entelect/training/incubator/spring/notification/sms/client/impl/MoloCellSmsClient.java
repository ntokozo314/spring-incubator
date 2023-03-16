package entelect.training.incubator.spring.notification.sms.client.impl;

import entelect.training.incubator.spring.notification.sms.client.SmsClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * A custom implementation of a fictional SMS service.
 */
@Service
public class MoloCellSmsClient implements SmsClient {
    
    @Override
    public void sendSms(String name) {
        System.out.println(String.format("Sending SMS, destination='{}'", name));
    }

    @JmsListener(destination = "CoolNewQueue")
    public void sendSmsds(String name) {
        System.out.println(String.format("Sending SMS, destination='{}'", name));
    }
}
