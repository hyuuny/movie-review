package com.setge.talkingtoday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {

    private String fileName;    // 파일의 이름
    private String uuid;        // uuid
    private String folderPath;  // 업로드 저장 경로

    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
