package com.java.chapter1;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


public class TestUnivocity {

	public static void main(String[] args) {
		TestUnivocity testUnivocity = new TestUnivocity();
		testUnivocity.parseCSV("C:/Users/admin/Desktop/SPiDER_TM_tm.csv");
	}

	public void parseCSV(String fileName) {
		//CSV 파서 객체 선언
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setLineSeparatorDetectionEnabled(true);
		
		//파싱된 각 행을1 리스트에 저장하는 RowListProcessor 생성
		RowListProcessor rowListProcessor = new RowListProcessor();
		parserSettings.setRowProcessor(rowListProcessor);
		
		//파서가 입력에서 줄 구분자를 자동으로 인식하도록 설정
		parserSettings.setHeaderExtractionEnabled(true);
		
		CsvParser parser = new CsvParser(parserSettings);
		parser.parse(new File(fileName));

		String[] headers = rowListProcessor.getHeaders();

		List<String[]> rows = rowListProcessor.getRows();
		for (int i = 0; i <= rows.size(); i++) {
			System.out.println(Arrays.asList(rows.get(i)));
		}
	}
}
