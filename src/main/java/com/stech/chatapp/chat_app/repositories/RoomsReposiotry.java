package com.stech.chatapp.chat_app.repositories;

import com.stech.chatapp.chat_app.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomsReposiotry extends MongoRepository<Room, String> {

    // get me room using room id
    Room findByRoomId(String roomId);





}
