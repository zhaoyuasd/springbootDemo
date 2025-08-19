package DBSCAN;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.*;

public class TfIdfCalculator {
    private final Analyzer analyzer;
    private final Directory index;
    private final Map<String, Integer> docFreq;

    public TfIdfCalculator() {
        this.analyzer = new StandardAnalyzer();
        this.index = new RAMDirectory();
        this.docFreq = new HashMap<>();
    }

    public List<Map<String, Double>> calculateTfIdf(List<String> documents) throws IOException {
        // Index documents
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);
        int  j = 1;
        for (String doc : documents) {
            Document luceneDoc = new Document();
            luceneDoc.add(new StringField("id", j+"", Field.Store.YES));
            luceneDoc.add(new TextField("content", doc, Field.Store.YES));
            writer.addDocument(luceneDoc);
            j ++;
        }
        writer.commit();
        writer.close();

        // Calculate document frequency for each term
        IndexReader reader = DirectoryReader.open(index);
        for (int i = 0; i < reader.maxDoc(); i++) {
            Terms terms = reader.getTermVector(i, "content");
            if (terms != null) {
                TermsEnum termsEnum = terms.iterator();
                Set<String> uniqueTerms = new HashSet<>();
                while (termsEnum.next() != null) {
                    String term = termsEnum.term().utf8ToString();
                    uniqueTerms.add(term);
                }
                for (String term : uniqueTerms) {
                    docFreq.put(term, docFreq.getOrDefault(term, 0) + 1);
                }
            }
        }

        // Calculate TF-IDF vectors
        List<Map<String, Double>> tfIdfVectors = new ArrayList<>();
        for (int i = 0; i < reader.maxDoc(); i++) {
            Terms terms = reader.getTermVector(i, "content");
            Map<String, Double> tfIdfVector = new HashMap<>();
            if (terms != null) {
                double docLength = terms.size();
                TermsEnum termsEnum = terms.iterator();
                while (termsEnum.next() != null) {
                    String term = termsEnum.term().utf8ToString();
                    long termFreq = termsEnum.totalTermFreq();

                    // TF
                    double tf = termFreq / docLength;

                    // IDF
                    double idf = Math.log((double)documents.size() / docFreq.get(term));

                    // TF-IDF
                    tfIdfVector.put(term, tf * idf);
                }
            }
            tfIdfVectors.add(tfIdfVector);
        }
        reader.close();

        return tfIdfVectors;
    }
}