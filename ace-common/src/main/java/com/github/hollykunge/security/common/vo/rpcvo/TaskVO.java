package com.github.hollykunge.security.common.vo.rpcvo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskVO {
    private String id;

    private String type;

    private String name;

    private String date;

    private String color;

    private String time;
}
