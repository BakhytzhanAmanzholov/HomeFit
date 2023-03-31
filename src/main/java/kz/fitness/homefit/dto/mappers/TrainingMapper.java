package kz.fitness.homefit.dto.mappers;

import kz.fitness.homefit.dto.response.TrainingHistoryDto;
import kz.fitness.homefit.models.Training;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class TrainingMapper {
    public static TrainingHistoryDto toResponseDto(Training training){

        File tempFile = new File(training.getLocation());
        String encodedString = null;

        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(tempFile.toPath());
        } catch (Exception e) {
            // TODO: handle exception
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = Base64.getEncoder().encodeToString(bytes);


        TrainingHistoryDto dto = TrainingHistoryDto.builder()
                .id(training.getId())
                .video(encodedString)
                .build();
        return dto;
    }
}
