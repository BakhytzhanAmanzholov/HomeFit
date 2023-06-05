package kz.fitness.homefit.dto.mappers;

import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.dto.response.TrainingHistoryDto;
import kz.fitness.homefit.dto.response.TrainingIdDto;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.utils.VideoConverter;

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
            inputStream = Files.newInputStream(Paths.get(training.getLocation()));
        } catch (Exception e) {
            e.printStackTrace();
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
                .dateTime(training.getDateTime().toString())
                .exercise(training.getExercise().getName())
                .video(encodedString)
                .build();
        if(training.getExercise().getName().equals("Squats")){
            dto.setSrc("assets/images/history/squats.jpg");
        }
        else if (training.getExercise().getName().equals("Dumbells")){
            dto.setSrc("assets/images/history/curls.jpg");
        }
        else if (training.getExercise().getName().equals("Pushups")){
            dto.setSrc("assets/images/history/pushups.jpg");
        }

        return dto;
    }

    public static TrainingHistoryDto toResponseH264(Training training) {
        String encodedString = null;
        InputStream inputStream = null;
        String path = VideoConverter.convertBase64ToMp4(training.getLocation());

        try {
            inputStream = Files.newInputStream(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
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
                .dateTime(training.getDateTime().toString())
                .exercise(training.getExercise().getName())
                .video(encodedString)
                .build();
        if(training.getExercise().getName().equals("Squats")){
            dto.setSrc("assets/images/history/squats.jpg");
        }
        else if (training.getExercise().getName().equals("Dumbells")){
            dto.setSrc("assets/images/history/curls.jpg");
        }
        else if (training.getExercise().getName().equals("Pushups")){
            dto.setSrc("assets/images/history/pushups.jpg");
        }

        return dto;
    }

    public static TrainingIdDto toResponseDtoHistory(Training training) {
        TrainingIdDto dto = TrainingIdDto.builder()
                .id(training.getId())
                .exercise(training.getExercise().getName())
                .dateTime(training.getDateTime().toString())
                .build();
        if(training.getExercise().getName().equals("Squats")){
            dto.setSrc("assets/images/history/squats.jpg");
        }
        else if (training.getExercise().getName().equals("Dumbells")){
            dto.setSrc("assets/images/history/curls.jpg");
        }
        else if (training.getExercise().getName().equals("Pushups")){
            dto.setSrc("assets/images/history/pushups.jpg");
        }

        return dto;
    }
}
