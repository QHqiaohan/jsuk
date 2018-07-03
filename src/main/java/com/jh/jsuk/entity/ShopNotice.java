package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 商家端-提醒通知
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@TableName("js_shop_notice")
public class ShopNotice extends Model<ShopNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer managerId;
    /**
     * 1=接收提醒,0=否
     */
    private Integer isNotice;
    /**
     * 1=新消息通知,0=不接收
     */
    private Integer isNewNotice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    public Integer getIsNewNotice() {
        return isNewNotice;
    }

    public void setIsNewNotice(Integer isNewNotice) {
        this.isNewNotice = isNewNotice;
    }

    public static final String ID = "id";

    public static final String MANAGER_ID = "manager_id";

    public static final String IS_NOTICE = "is_notice";

    public static final String IS_NEW_NOTICE = "is_new_notice";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopNotice{" +
        "id=" + id +
        ", managerId=" + managerId +
        ", isNotice=" + isNotice +
        ", isNewNotice=" + isNewNotice +
        "}";
    }
}
