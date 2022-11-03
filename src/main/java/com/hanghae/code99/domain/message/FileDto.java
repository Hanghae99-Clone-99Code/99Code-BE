package com.hanghae.code99.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class FileDto {

    //private String fileType;
    private MultipartFile file;

}