package com.java.chapter1;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/*
 * 
 * p31 예제 코드
 * 
 * */
public class TestRecursiveDirectoryTraversal {

	public static void main(String[] args) {

		System.out.println("파일 리스트");
		System.out.println(listFiles(new File("C:/Users/admin/Desktop/rootpath")));
	}

	public static Set<File> listFiles(File rootDir) {
		Set<File> fileSet = new HashSet<File>();

		if (rootDir == null || rootDir.listFiles() == null) {
			return fileSet;
		}
		for (File fileOrDir : rootDir.listFiles()) {
			// 파일 경로 체크
			if (fileOrDir.isFile()) {
				fileSet.add(fileOrDir);
				System.out.println("This is File = " + fileOrDir);
			}
			// 파일이아닌 디렉토리 모두 fileOrDir에 저장
			else {
				fileSet.addAll(listFiles(fileOrDir));
				System.out.println("This is Directory = " + fileOrDir);
			}
		}
		return fileSet;
	}
}