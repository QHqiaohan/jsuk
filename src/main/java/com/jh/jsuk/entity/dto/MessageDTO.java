package com.jh.jsuk.entity.dto;

import com.jh.jsuk.envm.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private UserType userType;

    private String id;

    private Integer userId;

    private String content;

    private String title;


}
