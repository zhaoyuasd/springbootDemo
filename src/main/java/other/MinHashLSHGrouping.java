package other;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MinHashLSHGrouping {

    private final int numHashes;
    private final int shingleSize;
    private final double similarityThreshold;

    public MinHashLSHGrouping(int numHashes, int shingleSize, double similarityThreshold) {
        this.numHashes = numHashes;
        this.shingleSize = shingleSize;
        this.similarityThreshold = similarityThreshold;
    }

    /**
     * 对文档集合进行分组
     */
    public List<List<String>> groupSimilarDocuments(List<String> documents) {
        // 1. 为每个文档生成MinHash签名
        List<int[]> signatures = documents.stream()
                .map(this::computeMinHashSignature)
                .collect(Collectors.toList());

        // 2. 使用LSH进行分组
        return performLSHGrouping(documents, signatures);
    }

    /**
     * 计算文档的MinHash签名
     */
    private int[] computeMinHashSignature(String document) {
        int[] minHashes = new int[numHashes];
        Arrays.fill(minHashes, Integer.MAX_VALUE);

        // 生成shingle
        Set<String> shingles = createShingles(document);

        // 为每个shingle计算哈希并更新MinHash
        for (String shingle : shingles) {
            for (int i = 0; i < numHashes; i++) {
                HashFunction hashFunction = Hashing.murmur3_32(i);
                int hash = hashFunction.hashUnencodedChars(shingle).asInt();
                if (hash < minHashes[i]) {
                    minHashes[i] = hash;
                }
            }
        }

        return minHashes;
    }

    /**
     * 创建shingle集合
     */
    private Set<String> createShingles(String text) {
        Set<String> shingles = new HashSet<>();
        String normalizedText = StringUtils.normalizeSpace(text.toLowerCase());
        String[] words = normalizedText.split("\\s+");

        // 生成所有可能的shingle
        for (int i = 0; i <= words.length - shingleSize; i++) {
            StringBuilder shingle = new StringBuilder();
            for (int j = 0; j < shingleSize; j++) {
                if (j > 0) shingle.append(" ");
                shingle.append(words[i + j]);
            }
            shingles.add(shingle.toString());
        }

        return shingles;
    }

    /**
     * 执行LSH分组
     */
    private List<List<String>> performLSHGrouping(List<String> documents, List<int[]> signatures) {
        List<List<String>> groups = new ArrayList<>();
        boolean[] assigned = new boolean[documents.size()];

        for (int i = 0; i < documents.size(); i++) {
            if (!assigned[i]) {
                List<String> group = new ArrayList<>();
                group.add(documents.get(i));
                assigned[i] = true;

                for (int j = i + 1; j < documents.size(); j++) {
                    if (!assigned[j]) {
                        double similarity = estimateJaccardSimilarity(signatures.get(i), signatures.get(j));
                        if (similarity >= similarityThreshold) {
                            group.add(documents.get(j));
                            assigned[j] = true;
                        }
                    }
                }

                groups.add(group);
            }
        }

        return groups;
    }

    /**
     * 估计Jaccard相似度
     */
    private double estimateJaccardSimilarity(int[] sig1, int[] sig2) {
        int equalHashes = 0;
        for (int i = 0; i < numHashes; i++) {
            if (sig1[i] == sig2[i]) {
                equalHashes++;
            }
        }
        return (double) equalHashes / numHashes;
    }

    public static void main(String[] args) {
        // 示例文档集合
        List<String> documents = Arrays.asList(
                "The quick***brown fox jumps over the lazy dog",
                "The quick brown fox leaps over the lazy dog",
                "The fast brown fox**jumps over the lazy dog",
                "I enjoy eating apples and bananas",
                "Apples and bananas are my favorite fruits",
                "The weather is nice today",
                "Today the weather is beautiful",
                "Programming in Java is fun",
                "Java programming can be enjoyable"
        );

        // 创建MinHashLSH分组器
        MinHashLSHGrouping minHashLSH = new MinHashLSHGrouping(
                128,   // 哈希函数数量
                3,      // shingle大小
                0.5     // 相似度阈值
        );

        // 执行分组
        List<List<String>> groups = minHashLSH.groupSimilarDocuments(documents);

        // 打印结果
        System.out.println("分组结果:");
        for (int i = 0; i < groups.size(); i++) {
            System.out.printf("组 %d: %s%n", i + 1, groups.get(i));
        }
    }
}