package com.company.util;

import lombok.Data;
import org.mp4parser.IsoFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//TODO add dependency to pom.xml
/*        <dependency>
            <groupId>org.mp4parser</groupId>
            <artifactId>isoparser</artifactId>
            <version>1.9.41</version>
         </dependency>
        */

@Data
@Component
public class VideoUtil {

    public Long getDuration(MultipartFile file) throws IOException {
        final IsoFile isoFile = new IsoFile(convert(file));
        return isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
    }


    private File convert(MultipartFile file) {
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
