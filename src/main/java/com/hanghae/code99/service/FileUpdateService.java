package com.hanghae.code99.service;

import com.hanghae.code99.domain.message.Files;
import com.hanghae.code99.domain.message.Room;
import com.hanghae.code99.repositrory.FileRepository;
import com.hanghae.code99.repositrory.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileUpdateService {

    private final FileService fileService;
    private final RoomRepository roomRepository;
    private final FileRepository fileRepository;

    public void update(MultipartFile multipartFile){

        String fileUrl = "";

        try {
            fileUrl = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
        }

        Room room = Room.builder()
                .imageUrl(fileUrl)
                .build();

        roomRepository.save(room);

        Files files = Files.builder()
                .room(room)
                .url(fileUrl)
                .build();
        fileRepository.save(files);

    }
}

