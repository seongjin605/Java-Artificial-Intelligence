package org.apache.lucene.demo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.handler.IndexDocHandlerImpl;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {
	public static void main(String[] args) {
		IndexDocHandlerImpl indexHandler = new IndexDocHandlerImpl();

		String indexPath = "index";
		String docsPath = null;
		boolean create = true;
		for (int i = 0; i < args.length; i++) {
			if ("-index".equals(args[i])) {
				indexPath = args[i + 1];
				i++;
			} else if ("-docs".equals(args[i])) {
				docsPath = args[i + 1];
				i++;
			} else if ("-update".equals(args[i])) {
				create = false;
			}
		}
		final Path docDir = Paths.get(docsPath);
		Date start = new Date();
		try {
			System.out.println("Indexing to directory'" + indexPath + "'...");
			Directory dir = FSDirectory.open(Paths.get(indexPath));

			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			if (create) {
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			IndexWriter writer = new IndexWriter(dir, iwc);
			indexHandler.indexDocs(writer, docDir);
			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + "total milliseconds");
		} catch (IOException e) {
		}
	}
}
