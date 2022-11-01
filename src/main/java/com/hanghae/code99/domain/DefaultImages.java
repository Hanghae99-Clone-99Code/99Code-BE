package com.hanghae.code99.domain;


import java.util.Random;

public class DefaultImages {

    private static final Random random = new Random(System.currentTimeMillis());

    private static final String[] DEFAULT_PROFILE_PICS = {
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/001.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/002.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/003.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/004.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/005.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/006.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/%ED%94%84%EB%A1%9C%ED%95%84%EC%9D%B4%EB%AF%B8%EC%A7%80/007.png",
            "https://byeongbumbucket.s3.ap-northeast-2.amazonaws.com/KakaoTalk_20221029_210115603.png"
    };


    public static String getRandomMemberPic() {
        return DEFAULT_PROFILE_PICS[random.nextInt(DEFAULT_PROFILE_PICS.length)];
    }
}


