package kz.fitness.homefit.utils;


import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;
import ws.schild.jave.info.VideoSize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class VideoConverter {
    public static String convertVideoToBase64(String base64Data) {
        try {
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Data);

            Path tempInputFile = Files.createTempFile("input", ".webm");
            Files.write(tempInputFile, decodedBytes);

            Path tempOutputFile = Files.createTempFile("output", ".mp4");

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");
            audio.setBitRate(64000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            VideoAttributes video = new VideoAttributes();
            video.setCodec("h264");
            video.setX264Profile(X264_PROFILE.BASELINE);
            video.setBitRate(160000);
            video.setFrameRate(15);
            video.setSize(new VideoSize(400, 300));

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(tempInputFile.toFile()), tempOutputFile.toFile(), attrs);

            byte[] encodedBytes = Files.readAllBytes(tempOutputFile);

            Files.delete(tempInputFile);
            Files.delete(tempOutputFile);

            return java.util.Base64.getEncoder().encodeToString(encodedBytes);
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String convertBase64ToMp4(String outputFilePath)  {
        File target = new File(outputFilePath);
        String name = UUID.randomUUID() + ".mp4";
        File output = new File("ml-server\\" + name);


        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");
        video.setX264Profile(X264_PROFILE.BASELINE);
        video.setBitRate(160000);
        video.setFrameRate(15);
        video.setSize(new VideoSize(400, 300));

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp4");
        attrs.setInputFormat("mp4");
        attrs.setVideoAttributes(video);

        try {
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(target), output, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.getPath();
    }
}
