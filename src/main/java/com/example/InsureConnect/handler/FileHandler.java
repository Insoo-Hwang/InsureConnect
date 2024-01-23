package com.example.InsureConnect.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileHandler {
    private final ResourceLoader resourceLoader;

    public String uploadFile(MultipartFile file, String serverPath) throws IOException {
        // 변경된 저장할 파일 경로
        Path storagePath = resourceLoader.getResource(serverPath).getFile().toPath().resolve(file.getOriginalFilename());

        // 파일을 저장
        Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);

        // 파일명 반환
        return storagePath.toString();
    }

    public List<String> uploadFiles(List<MultipartFile> files, String serverPath) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // 변경된 저장할 파일 경로
            Path storagePath = resourceLoader.getResource(serverPath)
                    .getFile().toPath().resolve(file.getOriginalFilename());

            // 파일을 저장
            Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 경로를 리스트에 추가
            fileUrls.add(storagePath.toString());
        }

        return fileUrls;
    }

    public void deleteFile(String filePath) throws IOException {
        // 파일 삭제
        Path path = Path.of(filePath);

        Files.deleteIfExists(path);
    }

}

