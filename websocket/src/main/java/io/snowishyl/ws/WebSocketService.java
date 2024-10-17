package io.snowishyl.ws;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author: feiwoscun
 * @date: 2024/9/29
 * @email: 2825097536@qq.com
 * @description:
 */
public class WebSocketService {
    /**
     * <channel ,token></>
     */
    private static final Cache<Channel, String> CHANNEL_CACHE;
    private static final Cache<String, List<Channel>> USER_CACHE;

    static {
        CHANNEL_CACHE = Caffeine.newBuilder()
                .initialCapacity(200)
                .expireAfterAccess(30, TimeUnit.MINUTES).build();

        USER_CACHE = Caffeine.newBuilder().
                initialCapacity(200).
                expireAfterAccess(30, TimeUnit.MINUTES).build();
    }

    public void removed(Channel channel) {
        CHANNEL_CACHE.asMap().remove(channel);
    }

    /**
     * 保存连接的channel
     *
     * @param channel
     */
    public void connect(Channel channel) {

        String tokenIfPresent = CHANNEL_CACHE.getIfPresent(channel);
        String token = NettyUtil.getAttr(channel, NettyUtil.TOKEN);
        if (tokenIfPresent == null) {
            //使用两个cache的目的是帮助心跳检测的时候快速判断，感觉也很有问题。。
            CHANNEL_CACHE.put(channel, token);
        }
        List<Channel> userChannelsIfPresent =
                USER_CACHE.getIfPresent(token);
        if (userChannelsIfPresent == null) {
            ArrayList<Channel> channels = new ArrayList<>();
            channels.add(channel);
            USER_CACHE.put(token, channels);
        }
    }

    /**
     * 给用户 登录的方法，现在暂时用不到
     *
     * @param channel
     * @param wsAuthorize
     */
    public void authorize(Channel channel, WSAuthorize wsAuthorize) {

    }

    /**
     * 发送消息执行回调函数
     *
     * @param msg
     * @param consumer
     * @return
     */
    public static synchronized ResultType sendMsg(Msg msg, Consumer<ResultType> consumer) {

        String to = MsgUtil.getTokenByUser(msg.getUserTo());
        List<Channel> userChannels = getUserChannels(to);
        ResultType resultType = ResultType.success;
        try {
            for (Channel userChannel : userChannels) {
                sendMsg(userChannel, msg);
            }
        } catch (Exception ignored) {
            resultType = ResultType.failure;
        }

        //执行回调函数
        if (consumer != null && resultType.equals(ResultType.success)) {
            AsyncTool.doAsync(consumer, ResultType.success);
        } else {
            AsyncTool.doAsync(consumer, ResultType.failure);
        }
        return ResultType.success;
    }

    private static List<Channel> getUserChannels(String to) {
        return USER_CACHE.getIfPresent(to);
    }

    private static ResultType sendMsg(Channel channel, Msg msg) throws RuntimeException {
        try {
            channel.writeAndFlush(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResultType.success;

    }
}
