package com.liangrui.hadoop_disk.util;

public class SortFileUtil {
    public static int  sortFile(String type)
    {
        String[] allowtype={"doc,docx,ppt,pptx,xls,xlsx,java,php,py,md,js,css,txt",
                "jpg,jpeg,png,gif","mp3","flv,mp4"};
        for(int i=0;i<=3;i++)
        {
            String[] filetypes=allowtype[i].split("[,]");
            for(String s1:filetypes)
            {
                if(s1.equals(type))
                {
                    return i;
                }
            }
        }
        return 4;
    }
}
