package com.liangrui.hadoop_disk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 文件与base64的互相转换操作
 */
public class FileChange{

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return *
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String targetPath,String catalogue)
            throws Exception {
        File file = new File(catalogue);
        if(file.exists()==false){
            file.mkdirs();
        }
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    public static String encodeBase64File(MultipartFile mfile) throws Exception {
        String fileName= mfile.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf('\\')+1);
        File file= new File(fileName);
        FileUtils.copyInputStreamToFile(mfile.getInputStream(),file);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        File del = new File(file.toURI());
        del.delete();
        return new BASE64Encoder().encode(buffer);
    }
}