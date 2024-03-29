package com.yang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Count implements Serializable {
    @Id
    private String id;
    private String name;
    private Integer count;
    private Date createTime;
    private Integer userId;
    private Integer classworkId;


}
