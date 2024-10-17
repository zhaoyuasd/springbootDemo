package ktt;

import com.alibaba.fastjson.JSON;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddKttGoodsQueryListRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddKttGoodsQuerySingleRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddKttOrderGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddKttGoodsQueryListResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddKttGoodsQuerySingleResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddKttOrderGetResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongli
 * @create 2023/10/27 13:43
 * @desc
 */

public class KttOAuthProvider {
    public static final String SERVICE_URL = "https://gw-api-test.pinduoduo.com/api/router";


    private String appKey = "08758f1641fa4233ba9b8b55aa0db250";

    private String appSecret = "49bf3572b8208dbb4fdc987fc16316dfa159725e";

    private String accessToken = "5c745fae0a734cebbb7cbc4cabb6f7b5ee9bfa2f";
    private void getProductByTime() throws Exception {
        PddKttGoodsQueryListRequest request = new PddKttGoodsQueryListRequest();
        request.setPage(1);
        request.setSize(100);
        PopClient client = new PopHttpClient(appKey, appSecret);
        PddKttGoodsQueryListResponse response = client.syncInvoke(request, accessToken);
        System.out.println(JSON.toJSONString(response));
    }

    private void getProductByGoodsId(Long goodsId) throws Exception {
        PopClient client = new PopHttpClient(appKey, appSecret);
        PddKttGoodsQuerySingleRequest request = new PddKttGoodsQuerySingleRequest();
        request.setGoodsId(goodsId);
        PddKttGoodsQuerySingleResponse response = client.syncInvoke(request, accessToken);
        System.out.println(JSON.toJSONString(response));
    }

    public void getOrderByCode(String code) throws Exception {
        PopClient client = new PopHttpClient(appKey, appSecret);
        PddKttOrderGetRequest request = new PddKttOrderGetRequest();
        request.setOrderSn(code);
        PddKttOrderGetResponse response = client.syncInvoke(request, accessToken);
        System.out.println(JSON.toJSONString(response));
    }


    public static void main(String[] args) throws Exception {
        KttOAuthProvider provider = new KttOAuthProvider();
        provider.getProductByTime();
        // provider.getProductByGoodsId(540604973528L);
        //provider.getOrderByCode("")
    }
}
