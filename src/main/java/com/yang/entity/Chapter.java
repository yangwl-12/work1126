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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Chapter implements Serializable {
    @Id
    private String id;
    private String name;
    @Column(name="chapterSize")
    private String chapterSize;

    private String chapter_time;
    private String path;
    @Column(name="albumId")
    private String albumId;
    @JSONField(format = "yyyy-MM-dd")
    private Date fabushjian;
}
