package com.jh.jsuk.utils;


import java.io.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class WebhooksVerifyService {

    private static String pubKeyPath = "res/pingpp_public_key.pem";
    private static String eventPath = "res/webhooks_raw_post_data.json";
    private static String signPath = "res/signature.txt";

    /**
     * 验证 webhooks 签名，仅供参考
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        runDemos();
    }

    public static void runDemos() throws Exception {
        // 该数据请从 request 中获取原始 POST 请求数据, 以下仅作为示例
        String webhooksRawPostData = getStringFromFile(eventPath);
        System.out.println("------- POST 原始数据 -------");
        System.out.println(webhooksRawPostData);
        // 签名数据请从 request 的 header 中获取, key 为 X-Pingplusplus-Signature (请忽略大小写, 建议自己做格式化)
        String signature = getStringFromFile(signPath);
        System.out.println("------- 签名 -------");
        System.out.println(signature);
        boolean result = verifyData(webhooksRawPostData, signature, getPubKey());
        System.out.println("验签结果：" + (result ? "通过" : "失败"));
    }

    /**
     * 读取文件, 部署 web 程序的时候, 签名和验签内容需要从 request 中获得
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }

    /**
     * 获得公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey() throws Exception {
//        String pubKeyString = getStringFromFile(pubKeyPath);
//        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        String pubKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0AmdRdCNIrrMc1v8Z6wX\n" +
            "ozLzfDQThW0S/Rj03yYSuRxZDPHBdmYHtjNlAAzxbpZfeYBySrr2Q90zdEbscBUV\n" +
            "lvW0Hs0QOW91xhnaEj7xneehbkJ1+gwr7yl6qoN+GDubpULZUZDSqPLSz+8WGhqy\n" +
            "lg4iT3+JzcHSD2YN6TAq3msEoB7NH0QVmzDGOjY48+v9UKVfCWtTriFaGxcnUnyE\n" +
            "3ckFG4/aNNCFgZPT+D0s2R9JmNjSxcN/nt/BGJZ1Q1bUStIPKhtvw9tx8cDToUSK\n" +
            "B0O+LYlRQAQscpGRMM9za3+ebcOe8U587rstB4DfHDwGXvvB7KIS8qRzWTFquR6u\n" +
            "8wIDAQAB";
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    /**
     * 验证签名
     *
     * @param dataString
     * @param signatureString
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        return signature.verify(signatureBytes);
    }
}
 
