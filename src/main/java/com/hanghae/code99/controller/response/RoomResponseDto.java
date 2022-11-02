package com.hanghae.code99.controller.response;

import com.hanghae.code99.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private String roomName;
    private String imageUrl;
    private String description;
}
