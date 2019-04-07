package com.xiaofeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/6 17:04
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Accessors(chain=true)
public class User implements Serializable {
    private Integer id;
    private Integer toUserId;
    private String content;
    private String type;
}
