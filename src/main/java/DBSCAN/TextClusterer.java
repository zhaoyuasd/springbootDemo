package DBSCAN;

import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.*;
import java.util.stream.Collectors;

public class TextClusterer {
    private final double eps;
    private final int minPts;

    public TextClusterer(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    public List<List<String>> cluster(List<String> documents, List<Map<String, Double>> tfIdfVectors) {
        // Get all unique terms
        Set<String> vocabulary = tfIdfVectors.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());
        List<String> terms = new ArrayList<>(vocabulary);

        // Convert TF-IDF vectors to double arrays
        List<DoublePoint> points = new ArrayList<>();
        for (Map<String, Double> vector : tfIdfVectors) {
            double[] values = new double[terms.size()];
            for (int i = 0; i < terms.size(); i++) {
                values[i] = vector.getOrDefault(terms.get(i), 0.0);
            }
            points.add(new DoublePoint(values));
        }

        // Perform DBSCAN clustering
        DBSCANClusterer<DoublePoint> dbscan = new DBSCANClusterer<>(eps, minPts, new EuclideanDistance());
        List<org.apache.commons.math3.ml.clustering.Cluster<DoublePoint>> clusters = dbscan.cluster(points);

        // Group documents by cluster
        List<List<String>> result = new ArrayList<>();
        for (org.apache.commons.math3.ml.clustering.Cluster<DoublePoint> cluster : clusters) {
            List<String> clusterDocs = new ArrayList<>();
            for (DoublePoint point : cluster.getPoints()) {
                int index = points.indexOf(point);
                clusterDocs.add(documents.get(index));
            }
            result.add(clusterDocs);
        }

        // Add noise points as separate clusters
        List<DoublePoint> clusteredPoints = clusters.stream()
                .flatMap(c -> c.getPoints().stream())
                .collect(Collectors.toList());

        for (DoublePoint point : points) {
            if (!clusteredPoints.contains(point)) {
                int index = points.indexOf(point);
                result.add(Collections.singletonList(documents.get(index)));
            }
        }

        return result;
    }
}
