package com.xy.common.lang;

import lombok.Data;

/**
 * @program: vueblog
 * *
 * @author: XY
 * @create: 2022-02-26 17:59
 **/

@Data
public class Result {
    private int code;

    private String msg;

    private Object data;

}
