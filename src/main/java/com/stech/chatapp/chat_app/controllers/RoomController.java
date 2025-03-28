package com.stech.chatapp.chat_app.controllers;

//import com.stech.chatapp.chat_app.config.AppConstants;
import com.stech.chatapp.chat_app.entities.Message;
import com.stech.chatapp.chat_app.entities.Room;
import com.stech.chatapp.chat_app.repositories.RoomsReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
//@CrossOrigin(origins = AppConstants.FRONT_END_BASE_URL)
@CrossOrigin("http://localhost:5173")
public class RoomController {

    @Autowired
    private RoomsReposiotry roomsReposiotry;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    // Create Room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {
        if (roomsReposiotry.findByRoomId(roomId) != null) {
            // Room already exists
            return ResponseEntity.badRequest().body("Room already exists");
        }

        // Create a new room
        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomsReposiotry.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // Get Room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room room = roomsReposiotry.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    // Get Messages of Room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        Room room = roomsReposiotry.findByRoomId(roomId);
        if (room == null || room.getMessages() == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = room.getMessages();

        // Pagination
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        if (start >= end) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }
}   
