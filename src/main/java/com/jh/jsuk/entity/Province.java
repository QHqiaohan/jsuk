package com.jh.jsuk.entity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Province {

    public Province(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    private List<City> cities;

}
