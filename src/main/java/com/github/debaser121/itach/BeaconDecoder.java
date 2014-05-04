package com.github.debaser121.itach;

import java.util.List;
import java.util.Map;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * A message decoder for iTach device discovery beacons. This decoder accepts a {@code Map<String, String>} and outputs
 * a {@link Beacon} object.
 */
@ChannelHandler.Sharable
public class BeaconDecoder extends MessageToMessageDecoder<Map<String, String>> {

    public static final String UUID_PROP = "UUID";
    public static final String SDK_CLASS_PROP = "SDKClass";
    public static final String MAKE_PROP = "Make";
    public static final String MODEL_PROP = "Model";
    public static final String REVISION_PROP = "Revision";
    public static final String PKG_LEVEL_PROP = "Pkg_Level";
    public static final String CONFIG_URL_PROP = "Config-URL";
    public static final String PCB_PN_PROP = "PCB_PN";
    public static final String STATUS_PROP = "Status";

    private static BeaconDecoder INSTANCE = new BeaconDecoder();

    public static BeaconDecoder getInstance() {
        return INSTANCE;
    }

    private BeaconDecoder() {
        // singleton
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx,
                          final Map<String, String> msg,
                          final List<Object> out) throws Exception {
        out.add( new Beacon( msg.get( UUID_PROP ),
                             msg.get( SDK_CLASS_PROP ),
                             msg.get( MAKE_PROP ),
                             msg.get( MODEL_PROP ),
                             msg.get( REVISION_PROP ),
                             msg.get( PKG_LEVEL_PROP ),
                             msg.get( CONFIG_URL_PROP ),
                             msg.get( PCB_PN_PROP ),
                             msg.get( STATUS_PROP ) ) );
    }
}
