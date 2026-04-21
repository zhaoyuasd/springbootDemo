package caom.laozao.springbootdemo.salableinventory;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class FrisoHttpUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param url
     * @param paramsData
     * @return
     * @throws Exception
     */
    public static FrisoResponse execute (String url, Map<String, Object> paramsData) throws Exception {
        Long longUUID = CommonUtils.getLongUUID();
        System.out.println(String.format("[%s] [美素数据推送请求] :%s ",longUUID, JSON.toJSONString(paramsData)));
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setEntity(new StringEntity(JSON.toJSONString(paramsData), Consts.UTF_8));
            httpClient = new SSLClient();
            response = httpClient.execute(httpPost);
            if (response == null) {
                System.out.println("error in FrisoHttpUtil ---> execute() : response is null");

            }
            String stringEntity = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println(String.format("error in FrisoHttpUtil ---> execute() : response statusCode is %s, stringEntity is %s", response.getStatusLine().getStatusCode(), stringEntity));

            }
            System.out.println(String.format("[%s] [美素数据推送响应] :%s ",longUUID, stringEntity));
            FrisoResponse frisoResponse = OBJECT_MAPPER.readValue(stringEntity, FrisoResponse.class);
            System.out.println(String.format("[%s] [美素数据推送结果] :%s ",longUUID, JSON.toJSONString(frisoResponse)));
            return frisoResponse;
        } catch (Exception e) {
            System.out.println(String.format("[%s] [美素数据推送出错] :%s ",longUUID, e.getMessage()));
            throw e;
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @ToString
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FrisoResponse {

        private String code;

        private String msg;
    }




}
