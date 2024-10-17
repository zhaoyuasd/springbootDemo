

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongli
 * @create 2024/1/4 14:03
 * @desc
 */

public class ChannelCompare {

    public static void main(String[] args) throws Exception {
        Map<String, ChannelEntity> omsChannelInfo = generateChannelInfo("oms.txt");
        Map<String, ChannelEntity> wmsChannelInfo = generateChannelInfo("wms.txt");

        System.out.println("wms ====> oms");
        for (Map.Entry<String, ChannelEntity> entry : wmsChannelInfo.entrySet()) {
            ChannelEntity omsChannel = omsChannelInfo.get(entry.getKey());
            if (omsChannel == null) {
                System.out.println("oms 数据不存在：" + JSON.toJSONString(entry.getValue()));
                continue;
            }
            if (!Objects.equals(omsChannel.getType(), entry.getValue().getType())) {
                System.out.println("wms oms 类型不一致 wms:" + JSON.toJSONString(entry.getValue()) + " oms:" + JSON.toJSONString(omsChannel));
            }
        }

        System.out.println("oms ====> wms");
        for (Map.Entry<String, ChannelEntity> entry : omsChannelInfo.entrySet()) {
            ChannelEntity wmsChannel = wmsChannelInfo.get(entry.getKey());
            if (wmsChannel == null) {
                System.out.println("wms 数据不存在：" + JSON.toJSONString(entry.getValue()));
                continue;
            }
            if (!Objects.equals(wmsChannel.getType(), entry.getValue().getType())) {
                System.out.println("wms oms 类型不一致 wms:" + JSON.toJSONString(entry.getValue()) + " oms:" + JSON.toJSONString(wmsChannel));
            }
        }
    }

    private static Map<String,ChannelEntity> generateChannelInfo(String file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Map<String,ChannelEntity> entityMap = new HashMap<>();
        String line = reader.readLine();
        while (Objects.nonNull(line) && !Objects.equals(line, "")) {
            String[] ss = line.split("   ");
            ChannelEntity entity = new ChannelEntity();
            entity.setBrandId(ss[0]);
            entity.setCode(ss[1]);
            entity.setName(ss[2]);
            entity.setType(ss[3]);
            entityMap.put(ss[0] + "-" + ss[1], entity);
            line = reader.readLine();
        }
        return entityMap;
    }
}
