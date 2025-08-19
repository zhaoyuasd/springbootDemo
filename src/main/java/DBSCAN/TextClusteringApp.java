package DBSCAN;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextClusteringApp {
    public static void main(String[] args) {
        // Sample documents
        List<String> documents = Arrays.asList(
                "The quick brown fox jumps over the lazy dog",
                "A quick brown dog outpaces a quick fox",
                "The lazy dog sleeps all day",
                "The fox is quick and brown",
                "Programming in Java is fun",
                "Java developers love Maven",
                "Spring framework is popular in Java"
        );

        try {
            // Step 1: Calculate TF-IDF vectors
            TfIdfCalculator tfIdfCalculator = new TfIdfCalculator();
            List<Map<String, Double>> tfIdfVectors = tfIdfCalculator.calculateTfIdf(documents);

            // Step 2: Perform clustering
            TextClusterer clusterer = new TextClusterer(0.5, 2); // Adjust eps and minPts as needed
            List<List<String>> clusters = clusterer.cluster(documents, tfIdfVectors);

            // Print results
            System.out.println("Found " + clusters.size() + " clusters:");
            for (int i = 0; i < clusters.size(); i++) {
                System.out.println("\nCluster " + (i + 1) + ":");
                for (String doc : clusters.get(i)) {
                    System.out.println("- " + doc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
