package com.jh.jsuk.utils.wx;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by LUTAO on 2018/1/17.
 */
public class HttpUtil {

    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            URLConnection conn = realUrl.openConnection();
            //设置请求编码格式
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //out = new PrintWriter(conn.getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            out.print(param);
            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = in.readLine()) != null)
            {
                //String line;
                result = result + new String(line.getBytes(),"utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendWXPost(String url, String param) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        /**
         *此处要改
         *wxconfig.SSLCERT_PATH : 指向你的证书的绝对路径，带着证书去访问
         */
        //C:\Users\SharPei\Desktop\apiclient_cert.p12
        FileInputStream instream = new FileInputStream(new File(WxPay.P12FILE));//P12文件目录
        try {
            /**
             * 此处要改
             *
             * 下载证书时的密码、默认密码是你的MCHID mch_id
             * */
            keyStore.load(instream,WxPay.MCHID.toCharArray());//这里写密码
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        /**
         * 此处要改
         * 下载证书时的密码、默认密码是你的MCHID mch_id
         * */
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore,WxPay.MCHID.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(param, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
