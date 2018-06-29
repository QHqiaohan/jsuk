package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问答Q&A表
 * </p>
 *
 * @author lpf
 * @since 2018-06-29
 */
@TableName("js_qa")
public class Qa extends Model<Qa> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 问题
     */
    private String quest;
    /**
     * 答案
     */
    private String answer;
    /**
     * 数值越大越靠前
     */
    private Integer sort;
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String QUEST = "quest";

    public static final String ANSWER = "answer";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Qa{" +
        "id=" + id +
        ", quest=" + quest +
        ", answer=" + answer +
        ", sort=" + sort +
        ", createTime=" + createTime +
        "}";
    }
}
