package io.snowishyl.ws;

/**
 * @author: feiwoscun
 * @date: 2024/10/1
 * @email: 2825097536@qq.com
 * @description:
 */
public class MsgUtil {
    //todo grpc
    public static User getUserByToken(String token) {
        return new User();
    }

    //todo grpc
    public static String getTokenByUser(User user) {
        return "feiwoscun";
    }

    public static Msg buildMsg(User userFrom, User userTo, String msg, ReqType reqType) {
        return Msg.builder().userFrom(userFrom)
                .reqType(reqType)
                .userTo(userTo)
                .msgInfo(msg)
                .build();
    }
}
