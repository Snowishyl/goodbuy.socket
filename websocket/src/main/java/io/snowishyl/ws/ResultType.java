package io.snowishyl.ws;

import lombok.AllArgsConstructor;

/**
 * @author: feiwoscun
 * @date: 2024/10/1
 * @email: 2825097536@qq.com
 * @description:
 */
@AllArgsConstructor
public enum ResultType {
    success(1, "成功发送信息"),
    failure(1, "发送信息失败"),
    ;
    private final Integer id;

    private final String desc;

}
