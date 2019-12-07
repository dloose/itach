package com.github.debaser121.itach;

import java.util.List;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetDevicesCommandResponseDecoder extends ByteToMessageDecoder {

    private static final Logger LOG = LoggerFactory.getLogger( GetDevicesCommandResponseDecoder.class );

    private static final byte CR = "\r".getBytes( Charsets.US_ASCII )[ 0 ];

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
        do {
            final int toRead = in.bytesBefore( CR );
            if( toRead == -1 ) {
                break;
            }

            final String deviceString = in.toString( in.readerIndex(), toRead, Charsets.US_ASCII );
            // consume all of the bytes and the CR
            in.skipBytes( toRead + 1 );

            out.add( deviceString );
            LOG.info( "found a string: {}", deviceString );
        }
        while( true );

    }
}
