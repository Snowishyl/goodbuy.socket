package io.snowishyl.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: feiwoscun
 * @date: 2024/10/1
 * @email: 2825097536@qq.com
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Msg {

    private User userFrom;
    private User userTo;
    private String msgInfo;
    private ReqType reqType;
}
