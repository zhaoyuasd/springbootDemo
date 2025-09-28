package canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import java.util.List;

public class TestCanal {
    public static void main(String[] args) {
        // 连接 Canal Server (单机模式)
//        CanalConnector connector = CanalConnectors.newSingleConnector(
//                new InetSocketAddress("127.0.0.1", 11111), // canal server 地址 + 端口
//                "example",  // destination，要和 canal 配置里的 instanceName 一致
//                "",         // 用户名（一般为空）
//                ""          // 密码（一般为空）
//        );

//                CanalConnector connector = CanalConnectors.newSingleConnector(
//                new InetSocketAddress("192.168.0.200", 11111), // canal server 地址 + 端口
//                "kunshu",  // destination，要和 canal 配置里的 instanceName 一致
//                "",         // 用户名（一般为空）
//                ""          // 密码（一般为空）
//        );

        // zk集群模式
        CanalConnector connector = CanalConnectors.newClusterConnector(
                "localhost:2187",// canal server 地址 + 端口
                "example",  // destination，要和 canal 配置里的 instanceName 一致
                "",         // 用户名（一般为空）
                ""          // 密码（一般为空）
        );



        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");  // 订阅所有库表
            connector.rollback();

            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    Thread.sleep(1000);
                } else {
                    printEntry(message.getEntries());
                }
                connector.ack(batchId); // 确认提交
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<com.alibaba.otter.canal.protocol.CanalEntry.Entry> entries) {
        for (com.alibaba.otter.canal.protocol.CanalEntry.Entry entry : entries) {
            try {
                if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    CanalEntry.EventType eventType = rowChange.getEventType();

                    System.out.printf("Binlog[%s:%s] , Schema[%s] , Table[%s] , EventType : %s%n",
                            entry.getHeader().getLogfileName(),
                            entry.getHeader().getLogfileOffset(),
                            entry.getHeader().getSchemaName(),
                            entry.getHeader().getTableName(),
                            eventType);

                    rowChange.getRowDatasList().forEach(rowData -> {
                        if (eventType == CanalEntry.EventType.DELETE) {
                            rowData.getBeforeColumnsList().forEach(col ->
                                    System.out.printf("删除 -> %s : %s%n", col.getName(), col.getValue()));
                        } else if (eventType == CanalEntry.EventType.INSERT) {
                            rowData.getAfterColumnsList().forEach(col ->
                                    System.out.printf("新增 -> %s : %s%n", col.getName(), col.getValue()));
                        } else {
                            rowData.getBeforeColumnsList().forEach(col ->
                                    System.out.printf("更新前 -> %s : %s%n", col.getName(), col.getValue()));
                            rowData.getAfterColumnsList().forEach(col ->
                                    System.out.printf("更新后 -> %s : %s%n", col.getName(), col.getValue()));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
