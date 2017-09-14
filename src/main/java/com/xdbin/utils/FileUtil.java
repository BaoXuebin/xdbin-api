package com.xdbin.utils;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
public class FileUtil {

    public static String readBlogContent(String blogBasePath, String path) {
        return read(blogBasePath + path);
    }

    public static String writeBlogContent(String blogBasePath, String content) {
        String time = ConvertUtil.parseDatetoString(new Date());
        String contentUrl = time + File.separator  + ConvertUtil.getRandomStr() + ".md";
        String path = blogBasePath + time;
        try {
            Files.createDirectories(Paths.get(path));
            write(blogBasePath + File.separator + contentUrl, content);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return contentUrl;
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

    public static void write(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }

}
