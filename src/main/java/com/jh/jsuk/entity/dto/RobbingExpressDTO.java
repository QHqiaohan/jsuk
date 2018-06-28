package com.jh.jsuk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RobbingExpressDTO implements Serializable {

    private Integer userId;

    private Integer expressId;

}
