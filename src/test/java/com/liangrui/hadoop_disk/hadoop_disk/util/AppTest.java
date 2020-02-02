package com.liangrui.hadoop_disk.hadoop_disk.util;


import com.liangrui.hadoop_disk.config.hadoop.conn.HdfsConn;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    /**
     *   HDFS获取文件系统
     */
    public void initHDFS() throws Exception{
// 1 创建配置信息对象
        Configuration configuration = new Configuration();
// 2 获取文件系统
        FileSystem fs = FileSystem.get(configuration);
// 3 打印文件系统
        System.out.println(fs.toString());
    }
    @Test
    /**
     * HDFS文件上传(测试参数优先级)
     */
    public void testCopyFromLocalFIle() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        //configuration.set("dfs.replication","2");
        FileSystem fs = HdfsConn.getFileSystem();
        //2 上传文件
        fs.copyFromLocalFile(new Path("F:\\java 工具64位.zip"),new Path("/java 工具64位.zip"));
        //3 关闭资源
        fs.close();
        System.out.println("over");
    }

    /**
     * HDFS文件的下载
     */
    @Test
    public void testCopyToLocalFile() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"12493");
        // 2 执行下载操作
        //boolean delSrc 指是否将原文件删除
        //Path src 指要下载的文件路径
        //Path dst 指将文件下载到的路径
        //boolean useRawLocalFileSystem 是否开启文件校验
        fs.copyToLocalFile(false,new Path("/java 工具64位.zip"),new Path("D:/java 工具64位.zip"),true);
        //3 关闭资源
        fs.close();
        System.out.println("over");
    }
    @Test
    /**
     * HDFS文件目录的创建
     */
    public void  testMkdir() throws URISyntaxException, IOException, InterruptedException {
        //1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"12493");
        //2.创建目录
        fs.mkdirs(new Path("/0906/zhangsan"));
        //3.关闭资源
        fs.close();
    }

    /**
     * HDFS文件的删除
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException, URISyntaxException, InterruptedException {
        //1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"12493");

        //2.执行删除
        fs.delete(new Path("/ava 工具64位.zip"),true);
        //3.关闭资源
        fs.close();
    }

    /**
     * HDFS 文件名的修改
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testRename() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"root");
        //2 修改文件名称
        fs.rename(new Path("/hello11.txt"),new Path("/Im.txt"));
        //3. 关闭资源
        fs.close();
    }
    @Test
    public void testListFiles() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"12493");
        //2 获取文件详情
        /**
         * 思考:为什么返回迭代器不是list之类的容器
         * 集合占内存多 一次全部拿取
         * 迭代器一次拿一个
         */
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"),true);

        while (listFiles.hasNext()){
            LocatedFileStatus status = listFiles.next();
            //输出详情
            //文件名称
            System.out.println("文件名:"+status.getPath().getName());
            //长度
            System.out.println("文件大小"+status.getLen());
            //获取组
            System.out.println("所属组:"+status.getGroup());
            //权限
            System.out.println("权限:"+status.getPermission());

            //获取存储的块信息
            BlockLocation[] blockLocations = status.getBlockLocations();

            for (BlockLocation blockLocation:blockLocations){
                //获取块的存储的主机节点
                String[] hosts = blockLocation.getHosts();
                for (String host:hosts){
                    System.out.println("获取块的存储的主机节点:"+host);
                }
            }
            System.out.println("-------------------------");
        }

        fs.close();
    }

    /**
     * HDFS文件和文件夹的判断
     */
    @Test
    public void testListStatus() throws URISyntaxException, IOException, InterruptedException {
        //1 获取文件配置信息
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"root");
        //2 判断是文件还是文件夹
        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        for (FileStatus fileStatus:listStatus){
            //如果是文件
            if (fileStatus.isFile()){
                System.out.println("文件:"+fileStatus.getPath().getName());
            }else {
                System.out.println("文件夹:"+fileStatus.getPath().getName());
            }
        }
        //关闭资源
        fs.close();
    }
}
