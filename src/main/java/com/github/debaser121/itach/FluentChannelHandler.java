package com.github.debaser121.itach;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class FluentChannelHandler extends ChannelHandlerAdapter {

    public static FluentSimpleChannelInboundHandler<DatagramPacket> simpleDatagramChannelInboundHandler
            (OnChannelRead0<DatagramPacket> onChannelRead0) {
        return new FluentSimpleChannelInboundHandler<DatagramPacket>( DatagramPacket.class,
                                                                      onChannelRead0,
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty(),
                                                                      Optional.empty() );
    }

    public static class FluentSimpleChannelInboundHandler<I> extends SimpleChannelInboundHandler<I> {

        private final Class<I> channelClass;
        private final OnChannelRead0<I> onChannelRead0;
        private final Optional<OnChannelRegistered> onChannelRegistered;
        private final Optional<OnChannelUnregistered> onChannelUnregistered;
        private final Optional<OnChannelActive> onChannelActive;
        private final Optional<OnChannelInactive> onChannelInactive;
        private final Optional<OnChannelReadComplete> onChannelReadComplete;
        private final Optional<OnUserEventTriggered> onUserEventTriggered;
        private final Optional<OnChannelWritabilityChanged> onChannelWritabilityChanged;
        private final Optional<OnExceptionCaught> onExceptionCaught;
        private final Optional<OnHandlerAdded> onHandlerAdded;
        private final Optional<OnHandlerRemoved> onHandlerRemoved;

        private FluentSimpleChannelInboundHandler(final Class<I> channelClass,
                                                  final OnChannelRead0<I> onChannelRead0,
                                                  final Optional<OnChannelRegistered> onChannelRegistered,
                                                  final Optional<OnChannelUnregistered> onChannelUnregistered,
                                                  final Optional<OnChannelActive> onChannelActive,
                                                  final Optional<OnChannelInactive> onChannelInactive,
                                                  final Optional<OnChannelReadComplete> onChannelReadComplete,
                                                  final Optional<OnUserEventTriggered> onUserEventTriggered,
                                                  final Optional<OnChannelWritabilityChanged>
                                                          onChannelWritabilityChanged,
                                                  final Optional<OnExceptionCaught> onExceptionCaught,
                                                  final Optional<OnHandlerAdded> onHandlerAdded,
                                                  final Optional<OnHandlerRemoved> onHandlerRemoved) {
            super( requireNonNull( channelClass ) );
            this.channelClass = channelClass;
            this.onChannelRead0 = requireNonNull( onChannelRead0 );
            this.onChannelRegistered = requireNonNull( onChannelRegistered );
            this.onChannelUnregistered = requireNonNull( onChannelUnregistered );
            this.onChannelActive = requireNonNull( onChannelActive );
            this.onChannelInactive = requireNonNull( onChannelInactive );
            this.onChannelReadComplete = requireNonNull( onChannelReadComplete );
            this.onUserEventTriggered = requireNonNull( onUserEventTriggered );
            this.onChannelWritabilityChanged = requireNonNull( onChannelWritabilityChanged );
            this.onExceptionCaught = requireNonNull( onExceptionCaught );
            this.onHandlerAdded = requireNonNull( onHandlerAdded );
            this.onHandlerRemoved = requireNonNull( onHandlerRemoved );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelRegistered(OnChannelRegistered onChannelRegistered) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    Optional.ofNullable( onChannelRegistered ),
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelUnregistered(OnChannelUnregistered onChannelUnregistered) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    Optional.ofNullable( onChannelUnregistered ),
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelActive(OnChannelActive onChannelActive) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    Optional.ofNullable( onChannelActive ),
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelInactive(OnChannelInactive onChannelInactive) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    Optional.ofNullable( onChannelInactive ),
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelReadComplete(OnChannelReadComplete onChannelReadComplete) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    Optional.ofNullable( onChannelReadComplete ),
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onUserEventTriggered(OnUserEventTriggered onUserEventTriggered) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    Optional.ofNullable( onUserEventTriggered ),
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onChannelWritabilityChanged(OnChannelWritabilityChanged
                                                                                        onChannelWritabilityChanged) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    Optional.ofNullable( onChannelWritabilityChanged ),
                    onExceptionCaught,
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onExceptionCaught(OnExceptionCaught onExceptionCaught) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    Optional.ofNullable( onExceptionCaught ),
                    onHandlerAdded,
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onHandlerAdded(OnHandlerAdded onHandlerAdded) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    Optional.ofNullable( onHandlerAdded ),
                    onHandlerRemoved
            );
        }

        public FluentSimpleChannelInboundHandler<I> onHandlerRemoved(OnHandlerRemoved onHandlerRemoved) {
            return new FluentSimpleChannelInboundHandler<I>(
                    channelClass,
                    onChannelRead0,
                    onChannelRegistered,
                    onChannelUnregistered,
                    onChannelActive,
                    onChannelInactive,
                    onChannelReadComplete,
                    onUserEventTriggered,
                    onChannelWritabilityChanged,
                    onExceptionCaught,
                    onHandlerAdded,
                    Optional.of( onHandlerRemoved )
            );
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final I msg) throws Exception {
            onChannelRead0.channelRead0( ctx, msg );
        }

        @Override
        public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelRegistered.isPresent() ) {
                onChannelRegistered.get().channelRegistered( ctx );
            }
            else {
                super.channelRegistered( ctx );
            }
        }

        @Override
        public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelUnregistered.isPresent() ) {
                onChannelUnregistered.get().channelUnregistered( ctx );
            }
            else {
                super.channelUnregistered( ctx );
            }
        }

        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelActive.isPresent() ) {
                onChannelActive.get().channelActive( ctx );
            }
            else {
                super.channelActive( ctx );
            }
        }

        @Override
        public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelInactive.isPresent() ) {
                onChannelInactive.get().channelInactive( ctx );
            }
            else {
                super.channelInactive( ctx );
            }
        }

        @Override
        public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelReadComplete.isPresent() ) {
                onChannelReadComplete.get().channelReadComplete( ctx );
            }
            else {
                super.channelReadComplete( ctx );
            }
        }

        @Override
        public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
            if( onUserEventTriggered.isPresent() ) {
                onUserEventTriggered.get().userEventTriggered( ctx, evt );
            }
            else {
                super.userEventTriggered( ctx, evt );
            }
        }

        @Override
        public void channelWritabilityChanged(final ChannelHandlerContext ctx) throws Exception {
            if( onChannelWritabilityChanged.isPresent() ) {
                onChannelWritabilityChanged.get().channelWritabilityChanged( ctx );
            }
            else {
                super.channelWritabilityChanged( ctx );
            }
        }

        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            if( onExceptionCaught.isPresent() ) {
                onExceptionCaught.get().exceptionCaught( ctx, cause );
            }
            else {
                super.exceptionCaught( ctx, cause );
            }
        }

        @Override
        public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
            if( onHandlerAdded.isPresent() ) {
                onHandlerAdded.get().handlerAdded( ctx );
            }
            else {
                super.handlerAdded( ctx );
            }
        }

        @Override
        public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
            if( onHandlerRemoved.isPresent() ) {
                onHandlerRemoved.get().handlerRemoved( ctx );
            }
            else {
                super.handlerRemoved( ctx );
            }
        }
    }

    public static interface OnChannelRead0<I> {
        public void channelRead0(ChannelHandlerContext ctx, I msg);
    }

    public static interface OnChannelRegistered {
        public void channelRegistered(ChannelHandlerContext ctx);
    }

    public static interface OnChannelUnregistered {
        public void channelUnregistered(final ChannelHandlerContext ctx);
    }

    public static interface OnChannelActive {
        public void channelActive(final ChannelHandlerContext ctx);
    }

    public static interface OnChannelInactive {
        public void channelInactive(final ChannelHandlerContext ctx);
    }

    public static interface OnChannelReadComplete {
        public void channelReadComplete(final ChannelHandlerContext ctx);
    }

    public static interface OnUserEventTriggered {
        public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt);
    }

    public static interface OnChannelWritabilityChanged {
        public void channelWritabilityChanged(final ChannelHandlerContext ctx);
    }

    public static interface OnExceptionCaught {
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause);
    }

    public static interface OnHandlerAdded {
        public void handlerAdded(final ChannelHandlerContext ctx);
    }

    public static interface OnHandlerRemoved {
        public void handlerRemoved(final ChannelHandlerContext ctx);
    }
}
