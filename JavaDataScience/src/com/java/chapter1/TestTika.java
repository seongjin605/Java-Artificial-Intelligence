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
		System.out.println("");
	}

	public void convertPdf(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			AutoDetectParser parser = new AutoDetectParser();
			BodyContentHandler handler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			parser.parse(inputStream, handler, metadata, new ParseContext());
			System.out.println(handler.toString());
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
