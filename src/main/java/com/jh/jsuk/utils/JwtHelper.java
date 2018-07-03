package com.jh.jsuk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.jwt.AccessToken;
import com.jh.jsuk.entity.jwt.JwtParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtHelper {

    private String profiles = "jsuk";

    public String createAccessToken(Integer userId,Integer userType) throws Exception {
        AccessToken token = new AccessToken();
        JwtParam jwtParam = new JwtParam();
        jwtParam.setUserId(userId);
        jwtParam.setLoginTime(new Date());
        jwtParam.setLoginType(userType);
        String subject = JwtHelper.generalSubject(jwtParam);
        String jwt = createJWT(Constant.JWT_ID, subject);
        token.setAccess_id(userId);
        token.setAccess_token(jwt);
        return token.getAccess_token();
    }

    public static void main(String[] args) throws Exception {
        Claims claims = new JwtHelper().parseJWT
                ("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MjU3NDQ4OTcsInN1YiI6IntcInVzZXJJZFwiOjI3LFwibG9naW5UaW1lXCI6MTUyNTc0NDg5NzI0OSxcImxvZ2luVHlwZVwiOjN9In0.wNrzO7wyVExJMRuP4V1T44imuuFWiW0g5xt0p6eFFIw");
        String subject = claims.getSubject();
        ObjectMapper objectMapper = new ObjectMapper();
        JwtParam jwtParam = null;
        jwtParam = objectMapper.readValue(subject, JwtParam.class);
        System.out.println(jwtParam);
    }

    /**
     * 生成subject信息
     *
     * @param jwtParam
     * @return
     */
    public static String generalSubject(JwtParam jwtParam) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(jwtParam);
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = profiles + Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public String createJWT(String id, String subject) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
