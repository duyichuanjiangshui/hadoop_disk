package com.liangrui.hadoop_disk.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.liangrui.hadoop_disk.config.hadoop.conn.HdfsConn;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class HDFSUtil {
    /**
     * 按路径上传文件到hdfs
     * @param conf
     * @param local
     * @param remote
     * @throws IOException
     */
    public static void copyFile(Configuration conf , String uri , String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        System.out.println("copy from: " + local + " to " + remote);
        fs.close();
    }

    /**
     * 按路径下载hdfs上的文件
     * @param conf
     * @param uri
     * @param remote
     * @param local
     * @throws IOException
     */
    public static void download(Configuration conf , String uri ,String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from" + remote + " to " + local);
        fs.close();
    }

    /**
     * File对象上传到hdfs
     * hdfsPath是上传的目录
     * @throws IOException
     */
    public static void createFile(MultipartFile mfile, String hdfsPath) throws IOException {
        InputStream in = null;
        RowkeyUtil rowkeyUtil=new RowkeyUtil();
        //用rowkey作为唯一表示
        String fileName = mfile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(suffix);
        String localPath="D:\\uploadtemp\\"+rowkeyUtil.getRowkey()+'.'+suffix;
        mfile.transferTo(new File(localPath));//将文件传入服务器
        HDFSUtil.searchDir(hdfsPath);
        String aimpath=hdfsPath+"\\"+rowkeyUtil.getRowkey()+'.'+suffix;
        FileSystem fs=HdfsConn.getFileSystem();
        fs.copyFromLocalFile(new Path(localPath),new Path(aimpath));
        //3 关闭资源
        fs.close();
        System.out.println("over");
        File del = new File(localPath);//将本地文件删除
        del.delete();
    }
    /*
    * hdfs是否存在文件夹
    *
    * */
    public static void searchDir(String stringDir) throws IOException {
        FileSystem dfs = HdfsConn.getFileSystem();
        Path dir=new Path(stringDir);
        //查找目录
        if(!dfs.exists(dir)){
            System.out.println("目录不存在，正在准备创建！");
            dfs.mkdirs(dir);
            System.out.println("创建目录成功！");
        }else{
            System.out.println("目录已存在！");
        }
    }

}