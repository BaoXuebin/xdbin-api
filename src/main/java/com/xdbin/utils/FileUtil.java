package com.xdbin.utils;

import com.xdbin.config.PathProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Component
public class FileUtil {

    public static String readBlogContent(String blogBasePath, String path) {
        return read(blogBasePath + path);
    }

    public static String read(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(line -> stringBuilder.append(line + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
