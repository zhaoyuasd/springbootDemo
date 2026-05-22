package rabbitmq;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author: dongli
 * @since: 2026/5/22 13:52
 * @description:
 */

public class FrisoOrderFileUtil {
    public static void main(String[] args) throws Exception {
        String tag = "SalesOrder:";
        String filePath = "C:\\Users\\dongli\\OneDrive\\Desktop\\frisoOrder.txt";
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath),
                        StandardCharsets.UTF_8
                ));
        String line =  reader.readLine();
        int index = line.indexOf(tag);
        if (index == -1 ) {
            System.out.println(line);
        } else {
            System.out.println(line.substring(index + tag.length()));
        }
    }
}
