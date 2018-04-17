package com.java.chapter1;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTesting {

	public static void main(String[] args) {
		JsoupTesting test = new JsoupTesting();
		test.extractDataWithJsoup("http://www.naver.com");
	}

	public void extractDataWithJsoup(String href) {
		Document doc = null;
		try {
			/*
			 * Connection ignoreHttpErrors (boolean ignoreHttpErrors) HTTP 오류가
			 * 발생했을 때 예외를 throw하지 않도록 연결을 구성합니다. (4xx - 5xx, 예 : 404 또는 500).
			 * 기본적으로 false입니다. 에러가 발생하면 (자) IOException가 Throw됩니다. true로 설정하면
			 * 응답에 오류 본문이 채워지고 상태 메시지에 오류가 반영됩니다.
			 */

			doc = Jsoup.connect(href).timeout(10 * 1000).userAgent("wooyo").ignoreHttpErrors(true).get();

		} catch (IOException e) {

		}
		if (doc != null) {
			// 웹 사이트 제목 추출
			String title = doc.title();
			// 웹 페이지 본문 영역만 추출
			String text = doc.body().text();

			//Elements:Element의 리스트
			Elements links = doc.select("a[href]");
			/*
			 * HTML 요소는 태그 이름, 속성 및 자식 노드 (텍스트 노드 및 기타 요소 포함)로 구성됩니다. 
			 * 요소에서 데이터를 추출하고 노드 그래프를 탐색하고 HTML을 조작 할 수 있습니다.
			 * */
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				/*outerHTML()
				 *  현재 요소를 포함한 내부 html 전체를 반환한다.
				 * */
				String linkOuterHtml = link.outerHtml();

				String linkInnerHtml = link.html();
				
				/*
				 * 반환값 비교
				 * outerHtml()과 Element 동일
				 * text()와 html()과 반환값 동일
				 * */
				System.out.println("\n ▶linkHref\n"+linkHref + 
						"\n\n ▶linkText\n" + linkText + 
						"\n\n ▶linkOuterHtml\n" + linkOuterHtml + 
						"\n\n ▶link\n" + link + 
						"\n\n ▶linkInnerHtml\n" + linkInnerHtml);
			}
		}
	}
}
