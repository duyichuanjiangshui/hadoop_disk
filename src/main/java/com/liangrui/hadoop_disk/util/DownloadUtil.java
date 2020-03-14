package com.liangrui.hadoop_disk.util;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadUtil {

    public static boolean fileDownload(HttpServletResponse response, String filePath, String fileName)
            throws UnsupportedEncodingException {
        File file = new File(filePath);//创建下载的目标文件，参数为文件的真实路径
        if (file.exists()) { //判断文件是否存在
            String type = new MimetypesFileTypeMap().getContentType(fileName);
            // 设置contenttype，即告诉客户端所发送的数据属于什么类型
            response.setHeader("Content-type",type);
            // 设置编码
            String hehe = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            File del = new File(file.toURI());
            del.delete();
        }
        return true;
    }
}