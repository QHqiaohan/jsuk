package com.jh.jsuk.entity.recu;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Category {

    private Integer id;

    private String name;

    private List<Category> children;

}
