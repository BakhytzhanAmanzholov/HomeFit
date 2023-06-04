package kz.fitness.homefit.dto.mappers;

import kz.fitness.homefit.dto.response.TrainingHistoryDto;
import kz.fitness.homefit.dto.response.TrainingIdDto;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Training;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

public class TrainingMapper {
    public static TrainingHistoryDto toResponseDto(Training training) {
        String encodedString = null;

        InputStream inputStream = null;
        try {
            System.out.println(training.getLocation());
            inputStream = Files.newInputStream(Paths.get(training.getLocation()));
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


        return TrainingHistoryDto.builder()
                .id(training.getId())
                .dateTime(training.getDateTime().toString())
                .exercise(training.getExercise().getName())
                .video(encodedString)
                .build();
    }

    public static TrainingIdDto toResponseDtoHistory(Training training) {
        return TrainingIdDto.builder()
                .id(training.getId())
                .exercise(training.getExercise().getName())
                .dateTime(training.getDateTime().toString())
                .build();
    }
}
