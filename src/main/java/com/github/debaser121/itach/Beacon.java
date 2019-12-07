package com.github.debaser121.itach;

import static com.google.common.base.Objects.toStringHelper;
import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Objects;

/**
 * Represents an iTach discovery beacon message.
 */
public class Beacon {

    private final String uuid;
    private final String sdkClass;
    private final String make;
    private final String model;
    private final String revision;
    private final String pkgLevel;
    private final URI configUrl;
    private final String pcbPn;
    private final String status;

    /**
     * Creates a beacon message object.
     *
     * @param uuid
     *         the device's UUID in the form {@code GlobalCache_<MAC Address>}. Non-null.
     * @param sdkClass
     *         the device's SDK class. The only known value for this is {@code UTILITY}. Non-null.
     * @param make
     *         the device's manufacturer. Non-null.
     * @param model
     *         the device's model. Non-null.
     * @param revision
     *         the device's revision number. Non-null.
     * @param pkgLevel
     *         The device's package level? Non-null.
     * @param configUrl
     *         the device's configuration URL. Non-null.
     * @param pcbPn
     *         the device's PCB PN whatever that is. Non-null.
     * @param status
     *         the device's status. The only known value is {@code Ready}. Non-null.
     */
    public Beacon(final String uuid,
                  final String sdkClass,
                  final String make,
                  final String model,
                  final String revision,
                  final String pkgLevel,
                  final URI configUrl,
                  final String pcbPn,
                  final String status) {
        this.uuid = requireNonNull( uuid );
        this.sdkClass = requireNonNull( sdkClass );
        this.make = requireNonNull( make );
        this.model = requireNonNull( model );
        this.revision = requireNonNull( revision );
        this.pkgLevel = requireNonNull( pkgLevel );
        this.configUrl = requireNonNull( configUrl );
        this.pcbPn = requireNonNull( pcbPn );
        this.status = requireNonNull( status );
    }

    public String getUuid() {
        return uuid;
    }

    public String getSdkClass() {
        return sdkClass;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getRevision() {
        return revision;
    }

    public String getPkgLevel() {
        return pkgLevel;
    }

    public URI getConfigUrl() {
        return configUrl;
    }

    public String getPcbPn() {
        return pcbPn;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public boolean equals(final Object o) {
        if( this == o ) {
            return true;
        }

        if( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Beacon beacon = (Beacon) o;

        return Objects.equals( configUrl, beacon.configUrl ) &&
               Objects.equals( make, beacon.make ) &&
               Objects.equals( model, beacon.model ) &&
               Objects.equals( pcbPn, beacon.pcbPn ) &&
               Objects.equals( pkgLevel, beacon.pkgLevel ) &&
               Objects.equals( revision, beacon.revision ) &&
               Objects.equals( sdkClass, beacon.sdkClass ) &&
               Objects.equals( status, beacon.status ) &&
               Objects.equals( uuid, beacon.uuid );
    }

    @Override
    public int hashCode() {
        return Objects.hash( uuid, sdkClass, make, model, revision, pkgLevel, configUrl, pcbPn, status );
    }

    @Override
    public String toString() {
        return toStringHelper( this )
                .add( "uuid", uuid )
                .add( "sdkClass", sdkClass )
                .add( "make", make )
                .add( "model", model )
                .add( "revision", revision )
                .add( "pkgLevel", pkgLevel )
                .add( "configUrl", configUrl )
                .add( "pcbPn", pcbPn )
                .add( "status", status )
                .toString();
    }
}
