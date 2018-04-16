package com.java.chapter1;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileListApacheCommonsIO {

	public static void main(String[] args) {
		FileListApacheCommonsIO fileListing=new FileListApacheCommonsIO();
		fileListing.listFile("C:/Users/admin/Desktop/rootpath");
		System.out.println("check");
	}
	public void listFile(String rootDir){
		File dir=new File(rootDir);
		List<File> files=(List<File>)FileUtils.listFiles(dir, TrueFileFilter.INSTANCE,TrueFileFilter.INSTANCE);
		for(File file:files){
			System.out.println("file: "+file.getAbsolutePath());
		}
	}
}
