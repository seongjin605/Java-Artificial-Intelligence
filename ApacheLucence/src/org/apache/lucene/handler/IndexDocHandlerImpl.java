package org.apache.lucene.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

public class IndexDocHandlerImpl implements IndexDocHandler {
	
	/*
	 * indexDocs Method(주어진 파일의 인덱싱을 생성 
	 * 만약 인수로 디렉토리가 주어지면, 이 메소드는 해당 디렉토리 하위에 있는
	 * 모든 디렉토리와 파일들을 재귀 호출을 통해서 반복 처리
	 * 이 메소드는 입력 파일 하나당 문서(document)로 인덱싱한다.
	 */
	@Override
	public void indexDocs(final IndexWriter writer, Path path) throws IOException {
		
		/*
		 * File.isDirectory(path)
		 * 파일이 디렉토리인지 여부를 테스트합니다.
		 */
		if (Files.isDirectory(path)) {
			/*
			 * 파일 트리를 탐색합니다.
			 * 
			 * 이 메소드는 호출하는 것처럼 표현식을 평가하는 것과 같습니다.
			 * 
			 * walkFileTree (start, EnumSet.noneOf (FileVisitOption.class),
			 * Integer.MAX_VALUE, 방문자)
			 * 
			 * 즉, 심볼릭 링크를 따르지 않고 파일 트리의 모든 레벨을 방문합니다.
			 */
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				/*
				 * BasicFileAttributes 인터페이스
				 * 파일 시스템의 파일과 연관된 기본 속성.
				 * 
				 * 기본 파일 속성은 많은 파일 시스템에 공통적 인 속성이며 이 인터페이스에서 정의한 필수 및 선택적 파일 속성으로
				 * 구성됩니다.
				 */
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

					try {
						/*
						 * attrs.lastModifiedTime().toMillis()
						 * 마지막으로 수정 한 시간을 밀리초 단위로 반환합니다.
						 * 
						 */
						indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException e) {
					}
					/*
					 * preVisitDirectory(디렉토리에 접근했을때 동작하는 메소드)
					 * FileVisitResult.CONTINUE : 디렉토리의 항목들을 계속 탐색
					 */
					return FileVisitResult.CONTINUE;
				};
			});
		} 
		/*
		 * 파일이 디렉토리가 아닌 파일이 맞을 경우 처리
		 */
		else {
			indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
		}
	}

	/*
	 * 실제로 문서하나를 인덱싱 처리하는 메소드  
	 */
	@Override
	public void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {

		try (InputStream stream = Files.newInputStream(file)) {
			
			Document doc = new Document();
			
			/*
			 * 필드는 인덱스되거나 검색이 가능하게 설정
			 * 필드를 토큰화 시키거나, 용어의 빈도나 위치 정보를 인덱싱X
			 * Filed.Store.Yes : 원래 필드 값을 인덱스에 저장
			 */
			Field pathFiled = new StringField("path", file.toString(), Field.Store.YES);
			doc.add(pathFiled);
			doc.add(new LongPoint("modified", lastModified));
			doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
			
			/*
			 * IndexWriterConfig.OpenMode 옵션은 새 인덱스를 만들지 또는 기존 인덱스를 열지 여부를 결정합니다. 
			 * 색인을 사용하는 동안에도 IndexWriterConfig.OpenMode.CREATE로 색인을 열 수 있습니다. 
			 */
			
			// 하나의 프로세스가 색인을 진행중에 또 다른 스레드가 색인을 동시다발적으로 실행하게되면
			// 파일이 실시간적으로 추가되는걸 로그로 띄워주고
			// 문서를 실시간적으로 추가해줌
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				System.out.println("adding " + file);
				writer.addDocument(doc);
			} else {
				System.out.println("updating " + file);
				writer.updateDocument(new Term("path", file.toString()), doc);
			}
		}
	}

}
