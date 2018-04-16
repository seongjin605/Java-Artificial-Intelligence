package com.java.chapter1;

public class CleaningData {

	public static void main(String[] args)throws Exception {
		CleaningData cleaningData = new CleaningData();
		String text = "ㄱㅇㅍㅊㅋㅂㅈㄷㄴㅇ러ㅏAjfklszjflasfjksldfs12434!#!@%!^&@&U*&";
		String cleanedText = cleaningData.cleanText(text);
		
		System.out.println(cleanedText);
	}

	public String cleanText(String text) {

		//ASCII 문자가 아닌 문자를 제거
		text = text.replaceAll("[^\\p{ASCII}]", "");

		//연속적인 공백을 하나의 공백으로 대치
		text = text.replaceAll("\\s+", " ");

		//모든 ASCII 제어 문자들을 지움
		text = text.replaceAll("\\p{Cntrl}", "");

		//네 번째 줄은 인쇄할 수 없는 ASCII 문자를 제거한
		text = text.replaceAll("[^\\p{Print}]", "");

		//유니코드로부터 출력할 수 없는 문자를 제거
		text = text.replaceAll("\\p{C}", "");

		return text;
	}
}
