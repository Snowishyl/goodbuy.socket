package io.snowishyl.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: feiwoscun
 * @date: 2024/9/29
 * @email: 2825097536@qq.com
 * @description:
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class WSBaseReq {

    String token;
    String fromId;
    ReqType reqtype;
    String targetId;

}
