package io.snowishyl.ws;

/**
 * @author: feiwoscun
 * @date: 2024/10/20
 * @email: 2825097536@qq.com
 * @description:活动通知类型
 */
public class DoSendActivity implements DoThingsByReqType {

    private ReqType reqType;
    private static volatile DoThingsByReqType doSendActivity;

    private static final Object LOCK = new Object();

    static {
        DistributeReqFactory.AddImplement(getInstance());
    }

    public DoSendActivity() {
    }

    public DoSendActivity(ReqType reqType) {
        this.reqType = reqType;
    }

    private static DoThingsByReqType getInstance() {
        if (doSendActivity == null) {
            synchronized (LOCK) {
                if (doSendActivity == null) {
                    doSendActivity = new DoSendActivity();
                }
            }
        }
        return doSendActivity;
    }

    @Override
    public ReqType getReqType() {
        return reqType;
    }

    @Override
    public void doThings() {

    }

    public Object readSolve() {
        return doSendActivity;
    }
}
