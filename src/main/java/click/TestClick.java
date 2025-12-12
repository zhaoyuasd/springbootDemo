package click;

import com.clickhouse.client.api.ClientConfigProperties;
import com.clickhouse.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author dongli
 * @create 2025/12/11 15:02
 * @desc
 */

public class TestClick {
    public static void main(String[] args) {
        String url = "jdbc:clickhouse://localhost:8123/default";
        String user = "default";
        String password = "default"; // 如果没设置密码，可以留空 ""

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("ClickHouse connected successfully!");

            // 查询示例
            String sql = "SELECT version()";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ClickHouse version: " + rs.getString(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
