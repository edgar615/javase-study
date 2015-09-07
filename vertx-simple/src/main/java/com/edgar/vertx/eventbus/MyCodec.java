package com.edgar.vertx.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * Created by Administrator on 2015/8/31.
 */
public class MyCodec implements MessageCodec<String, MyPojo> {
    @Override
    public void encodeToWire(Buffer buffer, String s) {

    }

    @Override
    public MyPojo decodeFromWire(int i, Buffer buffer) {
        return null;
    }

    @Override
    public MyPojo transform(String s) {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public byte systemCodecID() {
        return 0;
    }
}
