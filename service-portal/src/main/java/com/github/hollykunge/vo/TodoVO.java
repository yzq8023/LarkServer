package com.github.hollykunge.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TodoVO {
    private String id;

    private String name;

    private String tag;

    private String date;

    private String time;
}
