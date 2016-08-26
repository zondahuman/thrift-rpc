package com.abin.lee.thrift.rpc.common;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 16-4-18
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientUtil {
    private static CloseableHttpClient httpsClient = null;
    private static CloseableHttpClient httpClient = null;

    static {
        httpClient = getHttpClient();
        httpsClient = getHttpsClient();
    }

    public static CloseableHttpClient getHttpClient() {
        try {
            httpClient = HttpClients.custom()
                    .setConnectionManager(PoolManager.getHttpPoolInstance())
                    .setConnectionManagerShared(true)
                    .setDefaultRequestConfig(requestConfig())
                    .setRetryHandler(retryHandler())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClient;
    }


    public static CloseableHttpClient getHttpsClient() {
        try {
            //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            //Implementation of a trust manager for X509 certificates
            TrustManager x509TrustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{x509TrustManager}, null);
            //首先设置全局的标准cookie策略
//            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
            ConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(ctx, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", connectionSocketFactory).build();
            // 设置连接池
            httpsClient = HttpClients.custom()
                    .setConnectionManager(PoolsManager.getHttpsPoolInstance(socketFactoryRegistry))
                    .setConnectionManagerShared(true)
                    .setDefaultRequestConfig(requestConfig())
                    .setRetryHandler(retryHandler())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpsClient;
    }

    // 配置请求的超时设置
    //首先设置全局的标准cookie策略
    public static RequestConfig requestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setConnectionRequestTimeout(20000)
                .setConnectTimeout(20000)
                .setSocketTimeout(20000)
                .build();
        return requestConfig;
    }

    public static HttpRequestRetryHandler retryHandler(){
        //请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        return httpRequestRetryHandler;
    }



    //创建HostnameVerifier
    //用于解决javax.net.ssl.SSLException: hostname in certificate didn't match: <123.125.97.66> != <123.125.97.241>
    static HostnameVerifier hostnameVerifier = new NoopHostnameVerifier(){
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return super.verify(s, sslSession);
        }
    };


    public static class PoolManager {
        public static PoolingHttpClientConnectionManager clientConnectionManager = null;
        private static int maxTotal = 200;
        private static int defaultMaxPerRoute = 100;

        private PoolManager(){
            clientConnectionManager.setMaxTotal(maxTotal);
            clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        }

        private static class PoolManagerHolder{
            public static  PoolManager instance = new PoolManager();
        }

        public static PoolManager getInstance() {
            if(null == clientConnectionManager)
                clientConnectionManager = new PoolingHttpClientConnectionManager();
            return PoolManagerHolder.instance;
        }

        public static PoolingHttpClientConnectionManager getHttpPoolInstance() {
            PoolManager.getInstance();
//            System.out.println("getAvailable=" + clientConnectionManager.getTotalStats().getAvailable());
//            System.out.println("getLeased=" + clientConnectionManager.getTotalStats().getLeased());
//            System.out.println("getMax=" + clientConnectionManager.getTotalStats().getMax());
//            System.out.println("getPending="+clientConnectionManager.getTotalStats().getPending());
            return PoolManager.clientConnectionManager;
        }


    }

    public static class PoolsManager {
        public static PoolingHttpClientConnectionManager clientConnectionManager = null;
        private static int maxTotal = 200;
        private static int defaultMaxPerRoute = 100;

        private PoolsManager(){
            clientConnectionManager.setMaxTotal(maxTotal);
            clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        }

        private static class PoolsManagerHolder{
            public static  PoolsManager instance = new PoolsManager();
        }

        public static PoolsManager getInstance(Registry<ConnectionSocketFactory> socketFactoryRegistry) {
            if(null == clientConnectionManager)
                clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            return PoolsManagerHolder.instance;
        }

        public static PoolingHttpClientConnectionManager getHttpsPoolInstance(Registry<ConnectionSocketFactory> socketFactoryRegistry) {
            PoolsManager.getInstance(socketFactoryRegistry);
//            System.out.println("getAvailable=" + clientConnectionManager.getTotalStats().getAvailable());
//            System.out.println("getLeased=" + clientConnectionManager.getTotalStats().getLeased());
//            System.out.println("getMax=" + clientConnectionManager.getTotalStats().getMax());
//            System.out.println("getPending="+clientConnectionManager.getTotalStats().getPending());
            return PoolsManager.clientConnectionManager;
        }

    }


}
