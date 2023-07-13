package tn.esprit.tunisiacampbackend.RestControllers;

import tn.esprit.tunisiacampbackend.DAO.Entities.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tn.esprit.tunisiacampbackend.DAO.Repositories.MessageRepository;

import java.util.Date;

@Controller
public class chatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Sends a message to its destination channel
     *
     * @param message
     */
    @MessageMapping("/messages")
    public void handleMessage(Message message) {
        message.setTimestamp(new Date());
        messageRepository.save(message);
        template.convertAndSend("/channel/chat/" + message.getChannel(), message);
    }
}
