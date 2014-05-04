package com.github.debaser121.itach;

import static com.github.debaser121.itach.FluentChannelHandler.simpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
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
                    .handler( new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(final Channel ch) throws Exception {
                            ch.pipeline()
                              .addLast( AmxbMessageDecoder.getInstance() )
                              .addLast( BeaconDecoder.getInstance() )
                              .addLast( simpleChannelInboundHandler( Beacon.class, (ctx, msg) -> {
                                  LOG.info( "Got a beacon: {}", msg );
                              } ) );
                        }
                    } );

            final DatagramChannel channel = (DatagramChannel) bootstrap.bind().sync().channel();

            final InetSocketAddress groupAddress = new InetSocketAddress( "239.255.250.250", 9131 );
            channel.joinGroup( groupAddress, networkInterface ).sync();

            channel.closeFuture().await();
            channel.leaveGroup( groupAddress, networkInterface );
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
