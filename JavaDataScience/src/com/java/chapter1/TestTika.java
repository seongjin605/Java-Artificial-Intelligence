package com.java.chapter1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class TestTika {
	public static void main(String args[]) throws Exception {

		TestTika testTika = new TestTika();
		testTika.convertPdf("C:/Users/admin/Desktop/testpdf.pdf");
	}

	public void convertPdf(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			//Apache Tika 제공 파서
			AutoDetectParser parser = new AutoDetectParser();
			
			/*BodyContentHandler
			 * 파일의 본문에 들어가있는 내용을 처리할 핸들러
			 * Apache Tika는 최대 100,000자까지만 처리할수있게끔 제한되있으므로,
			 * -1값을 매개변수로 넣어줌으로써 십만개 제약사항을 무시함.
			 * */
						
			BodyContentHandler handler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			
			/*
			 *  스트림 : 문서 스트림 (입력) 
			 *  handler : XHTML SAX 이벤트 용 처리기 (출력) 
			 *  metadata : 문서 메타 데이터 (입력 및 출력) 
			 *  context : 문맥 분석(컨텍스트 파싱)
			 */
			parser.parse(inputStream, handler, metadata, new ParseContext());
			System.out.println(handler.toString());
			//System.out.println("check");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Erorr Closing Stream");
				}
			}
		}
	}
}
