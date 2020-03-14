package com.liangrui.hadoop_disk;

import java.io.File;

public class Demo2 {

    public static void main(String[] args) {
        File dir=new File("file");
        removeDir(dir);

    }

    private static void removeDir(File dir) {
        File[] files=dir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                removeDir(file);
            }else{
                System.out.println(file+":"+file.delete());
            }
        }
        System.out.println(dir+":"+dir.delete());
    }

}

