package com.github.debaser121.itach;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeaconListener {
    private static final Logger LOG = LoggerFactory.getLogger( BeaconListener.class );

    public static void main(String[] args) throws InterruptedException, SocketException {

        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final NetworkInterface networkInterface = NetworkInterface.getByName( "en1" );
            LOG.error( "networkInterface = {}", networkInterface );

            final Bootstrap bootstrap = new Bootstrap()
                    .group( workerGroup )
                    .channel( NioDatagramChannel.class )
                    .option( ChannelOption.IP_MULTICAST_IF, networkInterface )
                    .option( ChannelOption.SO_REUSEADDR, true )
                    .localAddress( 9131 )
                    .remoteAddress( new InetSocketAddress( NetUtil.LOCALHOST4, 9131 ) )
                    .handler( new ITachBeaconChannelHandler() );

            final DatagramChannel channel = (DatagramChannel) bootstrap.bind().sync().channel();

            final InetSocketAddress groupAddress = new InetSocketAddress( "239.255.250.250", 9131 );
            channel.joinGroup( groupAddress, networkInterface ).sync();

            final boolean result = channel.closeFuture().await( 30, TimeUnit.SECONDS );
            channel.leaveGroup( groupAddress, networkInterface );
            if( !result ) {
                LOG.info( "timeout" );
            }
            else {
                LOG.info( "SUCCESS!!" );
            }
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }

    private static class ITachBeaconChannelHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        private static final Logger LOG = LoggerFactory.getLogger( ITachBeaconChannelHandler.class );

        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            LOG.error( "error", cause );
            ctx.close();
        }

        @Override
        public void channelRead0(final ChannelHandlerContext ctx, final DatagramPacket msg)
                throws Exception {
            final String content = msg.content().toString( Charsets.US_ASCII );
            LOG.info( "read msg with content: {}", content );
        }
    }
}
