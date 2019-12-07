package com.github.debaser121.itach;

import static com.google.common.base.Objects.toStringHelper;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

public abstract class GetDevicesCommand {
    private GetDevicesCommand() {
        throw new UnsupportedOperationException( "uninstantiable class" );
    }

    public static class Request {

    }

    public static class Response {
        private final ImmutableList<Device> devices;

        public Response(final List<Device> devices) {
            this.devices = ImmutableList.copyOf( devices );
        }

        public ImmutableList<Device> getDevices() {
            return devices;
        }

        @Override
        public String toString() {
            return toStringHelper( this ).add( "devices", devices ).toString();
        }
    }

    public static class Device {
        private final int moduleAddress;
        private final ModuleType moduleType;

        public Device(final int moduleAddress, final ModuleType moduleType) {
            this.moduleAddress = moduleAddress;
            this.moduleType = Objects.requireNonNull( moduleType );
        }

        public int getModuleAddress() {
            return moduleAddress;
        }

        public ModuleType getModuleType() {
            return moduleType;
        }

        @Override
        public String toString() {
            return toStringHelper( this )
                    .add( "moduleAddress", moduleAddress )
                    .add( "moduleType", moduleType )
                    .toString();
        }
    }

    public static enum ModuleType {
        WIFI( "WIFI" ),
        ETHERNET( "ETHERNET" ),
        RELAY_3( "3 RELAY" ),
        IR_3( "3 IR" ),
        SERIAL_1( "1 SERIAL" );

        private final String typeName;

        ModuleType(final String typeName) {
            this.typeName = typeName;
        }

        public String typeName() {
            return typeName;
        }

        public static ModuleType findByTypeName(final String typeName) {
            return EnumSet.allOf( ModuleType.class ).stream()
                          .filter( moduleType -> moduleType.typeName().equals( typeName ) )
                          .findAny()
                          .get();
        }
    }
}
