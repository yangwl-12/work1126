package com.yang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    @Id
    private String id;
    private String title;
    private String content;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    private String cover;
    @Column(name = "guruId")
    private Integer guruId;
    private String status;
}
