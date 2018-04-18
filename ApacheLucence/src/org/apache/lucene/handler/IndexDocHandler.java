package org.apache.lucene.handler;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.lucene.index.IndexWriter;

public interface IndexDocHandler {

	public void indexDocs(final IndexWriter writer,Path path)throws IOException;
	
	public void indexDoc(IndexWriter writer, Path file, long lastModified)throws IOException;
}
