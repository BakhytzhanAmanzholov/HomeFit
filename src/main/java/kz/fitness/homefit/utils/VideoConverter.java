package kz.fitness.homefit.utils;


import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

public class VideoConverter {
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
