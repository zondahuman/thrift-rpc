package com.abin.lee.thrift.rpc.controller.test;


import com.abin.lee.thrift.rpc.common.util.HttpClientUtil;
import com.google.common.collect.Maps;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
*
*/
public class HttpRpcTest {
//    private static final String httpURL = "http://localhost:7200/load/platform";
//    private static final String httpURL = "http://172.16.2.133:9000/load/platform";
    private static final String httpURL = "http://localhost:7100/load/userInfo";

    @Test
    public void testHttpsLoad() throws IOException {
        CloseableHttpClient httpclient = HttpClientUtil.getHttpClient();
        try {
            HttpPost httpPost = new HttpPost(httpURL);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("param", "123456789"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
    }

}


