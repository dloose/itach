package com.github.debaser121.itach;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * A message decoder for iTach beacon-style messages. I'm calling these AMXB messages because of the prefix string. As
 * far as I know, this format is only used by iTach devices to broadcast their discovery beacon, but it can't hurt to
 * decompose the problem a bit, right?
 * <p>
 * An AMXB message is an ASCII string in the following format:
 * <pre>
 *     AMXB<-PROP1_NAME=PROP1_VALUE><-PROP2_NAME=PROP2_VALUE>...\n
 * </pre>
 * <p>
 * This decoder converts AMXB messages to a {@code Map<String, String>}.
 */
@ChannelHandler.Sharable
public class AmxbMessageDecoder extends MessageToMessageDecoder<ByteBufHolder> {

    public static final Pattern FORMAT = Pattern.compile( "<-([^=]+)=([^>]+)>" );

    private static final AmxbMessageDecoder INSTANCE = new AmxbMessageDecoder();

    public static final AmxbMessageDecoder getInstance() {
        return INSTANCE;
    }

    private AmxbMessageDecoder() {
        // singleton class
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx,
                          final ByteBufHolder msg,
                          final List<Object> out) throws Exception {

        final String content = msg.content().toString( Charsets.US_ASCII );

        if( content.startsWith( "AMXB" ) ) {
            // the format is simple enough to parse with a regex. so let's do that.
            final ImmutableMap.Builder<String, String> resultBuilder = ImmutableMap.builder();
            for( Matcher matcher = FORMAT.matcher( content ); matcher.find(); ) {
                resultBuilder.put( matcher.group( 1 ), matcher.group( 2 ) );
            }
            out.add( resultBuilder.build() );
        }
    }
}
