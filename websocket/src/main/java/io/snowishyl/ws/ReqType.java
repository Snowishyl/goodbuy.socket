package io.snowishyl.ws;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: feiwoscun
 * @date: 2024/9/29
 * @email: 2825097536@qq.com
 * @description:发送的消息的类型
 */
@AllArgsConstructor
@Slf4j
public enum ReqType {

    ACTIVITY(1, "活动");
    final Integer val;

    final String desc;

    private static final Map<Integer, ReqType> CACHE;

    static {
        CACHE = Arrays.stream(ReqType.values()).collect(Collectors.toMap(reqType -> reqType.val, Function.identity()));
    }

    public static ReqType getReqTypeByVal(Integer val) {
        ReqType reqType = CACHE.get(val);
        if (reqType == null) {
            log.error("cna not find ReqType ,because val == null");
            throw new RuntimeException("cna not find ReqType ,because val == null");
        }
        return reqType;
    }
}
