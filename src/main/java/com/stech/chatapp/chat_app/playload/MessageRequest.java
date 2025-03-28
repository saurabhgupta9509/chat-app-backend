package com.stech.chatapp.chat_app.playload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest{

    private String content;
    private String sender;
    private String roomId;
    private LocalDateTime MessageTime;



}
