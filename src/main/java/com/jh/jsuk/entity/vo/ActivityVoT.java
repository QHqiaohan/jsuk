package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/14 13:05
 */
public class ActivityVoT extends Activity {

    private User user;
    private Integer commentCount;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
