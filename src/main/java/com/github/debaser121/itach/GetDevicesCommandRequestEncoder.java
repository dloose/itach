package com.github.debaser121.itach;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class GetDevicesCommandRequestEncoder extends MessageToByteEncoder<GetDevicesCommand.Request> {

    private static final byte[] COMMAND = "getdevices\r".getBytes( Charsets.US_ASCII );

    @Override
    protected void encode(final ChannelHandlerContext ctx,
                          final GetDevicesCommand.Request msg,
                          final ByteBuf out) throws Exception {
        out.writeBytes( COMMAND );
    }
}
