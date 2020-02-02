package com.liangrui.hadoop_disk.config.hadoop.conn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

public class HdfsConn {
	private FileSystem fileSystem = null;
	private Configuration configuration = null;

	private static class SingletonHolder {
		private static final HdfsConn INSTANCE = new HdfsConn();
	}

	private HdfsConn() {
		try {
			configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://localhost:9000/");
			fileSystem = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileSystem getFileSystem() {
		return SingletonHolder.INSTANCE.fileSystem;
	}
	
	public static Configuration getConfiguration() {
		return SingletonHolder.INSTANCE.configuration;
	}

	public static void main(String[] args) {
		System.out.println(getFileSystem());
	}
}
