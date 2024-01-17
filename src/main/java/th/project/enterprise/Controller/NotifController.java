package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import th.project.enterprise.Entity.Message;

@Controller
public class NotifController {
  
  
  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;
  
  @MessageMapping("/application")
  @SendTo("/all/messages")
  public Message send(final Message message) throws Exception {
    return message;
  }
  
  @MessageMapping("/private")
  public void sendToSpecificUser(@Payload Message message) {
    simpMessagingTemplate.convertAndSendToUser(message.getBody(), "/specific", message);
  }
}
