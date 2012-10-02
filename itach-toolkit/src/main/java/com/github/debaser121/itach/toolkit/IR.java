package com.github.debaser121.itach.toolkit;

import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;

import java.util.Arrays;
import java.util.Iterator;

import com.github.debaser121.itach.toolkit.IR.BurstPair;
import com.github.debaser121.util.Strings;
import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

public class IR implements Iterable<BurstPair> {

  public class BurstPair {
    private final int on;
    private final int off;

    public BurstPair(int on, int off) {
      this.on = on;
      this.off = off;
    }

    public int getOn() {
      return on;
    }

    public int getOff() {
      return off;
    }

    @Override
    public String toString() {
      return String.format( "BurstPair [%d, %d]", on, off );
    }
  }

  private final String[] parts;
  private final int id;
  private final int frequency;
  private final int repeat;
  private final int offset;
  private final int[] pairs;

  public IR(String[] parts, int id, int frequency, int repeat, int offset, int[] pairs) {
    this.parts = parts;
    this.id = id;
    this.frequency = frequency;
    this.repeat = repeat;
    this.offset = offset;
    this.pairs = pairs;
  }

  public String[] getParts() {
    return parts;
  }

  public int getId() {
    return id;
  }

  public int getFrequency() {
    return frequency;
  }

  public int getRepeat() {
    return repeat;
  }

  public int getOffset() {
    return offset;
  }

  // sendir,<module:connector>,<ID>,<frequency>,<repeat>,<offset>,<on1>,<off1>,<on2>,<off2>,….,<onN>,<offN>
  public static IR parse(String parts) {
    return parse( parts.split( "," ) );
  }

  public static IR parse(String[] parts) {

    int[] intParts =
        Ints.toArray( transform( asList( parts ).subList( 2, parts.length ), Strings.parseInt() ) );

    return new IR( parts,
                   intParts[0],
                   intParts[1],
                   intParts[2],
                   intParts[3],
                   copyOfRange( intParts, 4, intParts.length ) );
  }

  public static IR parse(String hex_ir, int connector, int id) {
    return parse( hex2Gc( hex_ir, connector, id ) );
  }

  public static IR parse(int[] hex_ir, int offset, int length, int connector, int id) {
    return parse( hex2Gc( hex_ir, offset, length, connector, id ) );
  }

  public BurstPair get(int index) {
    int partsIndex = 2 * index;
    return new BurstPair( pairs[partsIndex], pairs[partsIndex + 1] );
  }

  public int length() {
    return pairs.length / 2;
  }

  @Override
  public Iterator<BurstPair> iterator() {
    return new Iterator<BurstPair>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < pairs.length;
      }

      @Override
      public BurstPair next() {
        int on = pairs[index++];
        int off = pairs[index++];
        return new BurstPair( on, off );
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Översätter från CCF (Pronto hex) till GC
  // Se http://www.hifi-remote.com/infrared/IR-PWM.shtml
  public static String hex2Gc(String hexIr, int connector, int id) {
    String[] h = hexIr.trim().split( " " );

    StringBuilder str = new StringBuilder();

    if( !h[0].equals( "0000" ) ) {
      throw new RuntimeException( "Invalid HEX sequence!" );
    }

    double n = Integer.parseInt( h[1], 16 );
    int freq = (int) ( 1000000.0 / ( n * 0.241246 ) );

    // # of burst pairs (i.e. on/off-pairs) in preamable
    int bpS1 = Integer.parseInt( h[2], 16 );
    // # of burst pairs in sequence
    int bpS2 = Integer.parseInt( h[3], 16 );

    int offset = bpS1 + 1;

    str.append( "sendir,1:" ) // module always 1 for iTach devices
        .append( connector )
        .append( "," )
        .append( id )
        .append( "," )
        .append( freq )
        .append( ",2" ) // # repetitions (always twice)
        .append( "," )
        .append( offset );

    for( int i = 4; i < h.length; i++ ) {
      str.append( "," );
      str.append( Integer.parseInt( h[i], 16 ) );
    }

    return str.toString();
  }

  public static String hex2Gc(int[] hexIr, int offset, int length, int connector, int id) {
    String hex =
        Joiner.on( ' ' ).join( transform( asList( copyOfRange( hexIr, offset, offset + length ) ),
                                          Strings.format( "%04h" ) ) );
    return hex2Gc( hex, connector, id );
  }

  public static enum StringFormat {
    /** Full Gc sendir command */
    GC,
    /** Stripped Gc ir command */
    STRIPPED_GC,
    /** Hex, i.e. CCF or "Pronto Hex" */
    HEX
  }

  @Override
  public String toString() {
    return toString( StringFormat.STRIPPED_GC );
  }

  public String toString(StringFormat fmt) {
    switch( fmt )
    {
      case GC:
        return Joiner.on( "," ).join( parts );
      case STRIPPED_GC:
        return Joiner.on( "," ).join( Arrays.copyOfRange( parts, 3, parts.length ) );
      case HEX:
        break;
    }

    StringBuilder s = new StringBuilder();

    int f = (int) ( 4145146.4480240086882269550583222 / frequency );

    s.append( String.format( "0000 %04h %04h %04h", f, offset - 1, length() ) );
    for( BurstPair bp : this ) {
      s.append( String.format( " %04h %04h", bp.getOn(), bp.getOff() ) );
    }

    return s.toString();
  }
}
