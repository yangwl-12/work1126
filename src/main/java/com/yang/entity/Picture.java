package com.yang.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements Serializable {
    @Id
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("name")
    private String name;
    @ExcelIgnore
    private String path;
    @ExcelProperty("introduction")
    private String introduction;
    @ExcelProperty("href")
    private String href;
    @ExcelProperty("status")
    private String status;
    @ExcelProperty("create-time")
    @JSONField(format = "yyyy-MM-dd")
    private Date create_time;
}
