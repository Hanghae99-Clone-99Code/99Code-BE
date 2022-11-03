package com.hanghae.code99.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomRequestDto {
    private String roomName;
    private String imageUrl;
    private String description;

}
