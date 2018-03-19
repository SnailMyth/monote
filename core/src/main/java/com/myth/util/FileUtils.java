package com.myth.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    public static String ROOT = "E:/WebRepo/";
    public static String ROOT_IMAGE = "E:/WebRepo/image/";

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        fileName = ROOT + fileName;
        StringUtils.printValue(FileUtils.class, "fileName", fileName);
        StringBuffer sb = new StringBuffer();
        File file = new File(fileName);
        InputStreamReader reader = null;
        BufferedReader bf = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            bf = new BufferedReader(reader);
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new FileReader(file);
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = bf.readLine()) != null) {
                sb.append("<p>").append(tempString).append("<p>");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    public static byte[] readImageFile(String fileName) {
        File file = new File(ROOT_IMAGE + fileName);
        FileInputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[(int) file.length()];
            inputStream.read(data);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null && inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return data;
    }

    public static void saveFile(String username, String content) {
        File file = new File(ROOT + username);
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {

                file.createNewFile();

            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // ReadUtils.readFileByChars(fileName);
        // ReadUtils.readFileByLines(fileName);
        // ReadUtils.readFileByRandomAccess(fileName);
        FileUtils.saveFile("text.txt", "<p>个黑u如果和<p>");
    }

    public static String getProjectPath() {
        return ROOT;
    }

}
