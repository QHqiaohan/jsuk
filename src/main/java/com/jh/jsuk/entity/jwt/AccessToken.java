package com.jh.jsuk.entity.jwt;

public class AccessToken {
    private Integer access_id;
    private String access_token;

    public Integer getAccess_id() {
        return access_id;
    }

    public void setAccess_id(Integer access_id) {
        this.access_id = access_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "access_id=" + access_id +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
