package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.UserAuthenticationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 实名认证
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Getter
@Setter
@ToString
@TableName("js_user_authentication")
public class UserAuthentication extends Model<UserAuthentication> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手持身份证
     */
    private String idCardImg;
    /**
     * 身份证正面
     */
    private String idCardImgZ;
    /**
     * 身份证反面
     */
    private String idCardImgF;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 审核状态 1=通过 0=审核中 -1=未通过 -2=未认证
     */
    private UserAuthenticationStatus status;
//    private Integer status;
    /**
     * 审核原因
     */
    private String desc;

    public static final String ID = "id";

    public static final String REAL_NAME = "real_name";

    public static final String ID_CARD = "id_card";

    public static final String ID_CARD_IMG = "id_card_img";

    public static final String ID_CARD_IMG_Z = "id_card_img_z";

    public static final String ID_CARD_IMG_F = "id_card_img_f";

    public static final String USER_ID = "user_id";

    public static final String STATUS = "status";

    public static final String DESC = "desc";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
