package com.liangrui.hadoop_disk;

import com.liangrui.hadoop_disk.config.hadoop.conn.HbaseConn;
import com.liangrui.hadoop_disk.util.*;
import org.apache.hadoop.hbase.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

public class test {
    @Test
    public void testdate()
    {
        RowkeyUtil rowkeyUtil=new RowkeyUtil();
        System.out.println(rowkeyUtil.getRowkey());
    }

    @Test
    public void testhbase()
    {
        RowkeyUtil rowkeyUtil=new RowkeyUtil();
        String rowKey=rowkeyUtil.getRowkey();
        try {
            String bytestream=FileChange.encodeBase64File("C:\\Users\\12493\\Downloads\\OpenOffice_Demo.rar");
            //将二进制流存入hbase
            HbaseUtil.insertHbaseData(rowKey,bytestream);

            String result=HbaseUtil.getData(rowKey);
            //将二进制流转换为文件
            FileChange.decoderBase64File(result,"C:\\Users\\12493\\Downloads\\Result_Demo.rar",
                    "C:\\Users\\12493\\Downloads\\");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void  testgethbase()
    {
        String rowKey="0cc120200201235612";
        String result= null;
        try {
            result = HbaseUtil.getData(rowKey);
            FileChange.decoderBase64File(result,"C:\\Users\\12493\\Downloads\\13-17年华师面试经验+真题.docx",
                    "C:\\Users\\12493\\Downloads\\");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void teststring()
    {

    }
    @Test public void dir()
    {
        String dir="/2020/2/2";

        try {
            HDFSUtil.searchDir(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
