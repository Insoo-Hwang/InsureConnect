package com.example.InsureConnect.Handler;

import com.example.InsureConnect.Entity.PromotionImg;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {

    public List<PromotionImg> parseFileInfo(
            List<MultipartFile> multipartFiles
    ) throws Exception {

        List<PromotionImg> fileList = new ArrayList<>();

        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath()
                + "/src/main/resources/static";

        String path = "/img/promotion/";

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                String contentType = multipartFile.getContentType();
                String originalFileExtension;
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else {
                        break;
                    }
                }
                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
                PromotionImg boardPicture = PromotionImg.builder()
                        .imgLink(path + new_file_name)
                        .build();
                        fileList.add(boardPicture);

                File file = new File(absolutePath + path + new_file_name);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}