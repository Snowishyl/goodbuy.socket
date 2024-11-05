package io.snowishyl.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author: feiwoscun
 * @date: 2024/10/20
 * @email: 2825097536@qq.com
 * @description:
 */
public class DistributeReqFactory {

    private static final List<DoThingsByReqType> SomeImplements = new ArrayList<>();

    public static void AddImplement(DoThingsByReqType doThingsByReqType) {
        SomeImplements.add(doThingsByReqType);
    }

    public Set<DoThingsByReqType> getImplementByReqType(ReqType reqType) {
        return SomeImplements.stream().filter(t -> t.getReqType().val.equals(reqType.val)).collect(Collectors.toSet());
    }

    public void getImplementByReqTypeThen(ReqType reqType, Consumer<Set<DoThingsByReqType>> consumer) {
        Set<DoThingsByReqType> collect = SomeImplements.stream().filter(t -> t.getReqType().val.equals(reqType.val)).collect(Collectors.toSet());
        if (consumer == null) {
            collect.forEach(DoThingsByReqType::doThings);
            return;
        }
        consumer.accept(collect);
    }

}
