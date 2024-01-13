package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.project.enterprise.Entity.Message;
import th.project.enterprise.Service.MessageService;
import th.project.enterprise.Service.UserService;

@Component("messageLogic")
public class MessageLogic {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

     public void buildAndSendMessage(){


//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User sender = userService.findUserByName(auth.getName());

        String title = "notification";
        String body = "new Order are added";

        Message message = new Message();
        message.setTitle(title);
        message.setBody(body);
        messageService.insert(message);
    }
    
    
}
