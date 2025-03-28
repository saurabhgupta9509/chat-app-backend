package com.stech.chatapp.chat_app.controllers;


//import com.stech.chatapp.chat_app.config.AppConstants;
import com.stech.chatapp.chat_app.entities.Message;
import com.stech.chatapp.chat_app.entities.Room;
import com.stech.chatapp.chat_app.playload.MessageRequest;
import com.stech.chatapp.chat_app.repositories.RoomsReposiotry;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
//@CrossOrigin(origins = AppConstants.FRONT_END_BASE_URL)
@CrossOrigin("http://localhost:5173")
public class ChatController {


        private RoomsReposiotry roomsReposiotry;

    public ChatController(RoomsReposiotry roomsReposiotry) {
        this.roomsReposiotry = roomsReposiotry;
    }


//    @MessageMapping("/sendMessage/{roomId}")
//    @SendTo("/topic/room/{roomId}")
//    public Message sendMessage(
//            @DestinationVariable String roomId,
//        @RequestBody MessageRequest request
//    ){
//            Room room = roomsReposiotry.findByRoomId(request.getRoomId());
//            Message message  = new Message();
//            message.setContent(request.getContent());
//            message.setSender(request.getSender());
//            message.setTimeStamp(request.getMessageTime());
//
//            if (room != null){
//                    room.getMessages().add(message);
//                    roomsReposiotry.save(room);
//
//            }else {
//                throw new RuntimeException("room not found e  !!! ");
//            }
//            return message;
//    }
@MessageMapping("/sendMessage/{roomId}")
@SendTo("/topic/room/{roomId}")
public Message sendMessage(
        @DestinationVariable String roomId,
        @RequestBody MessageRequest request) {

    Room room = roomsReposiotry.findByRoomId(roomId); // use roomId directly here
    Message message = new Message();
    message.setContent(request.getContent());
    message.setSender(request.getSender());
    message.setTimeStamp(request.getMessageTime());

    if (room != null) {
        room.getMessages().add(message);
        roomsReposiotry.save(room);
    } else {
        throw new RuntimeException("Room not found!");
    }
    return message;
}

}
