package com.github.debaser121.itach.toolkit;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Iterables.tryFind;
import static java.util.Arrays.asList;

public class ApiFunction {
  public static enum Mnemonic {
    UNKNOWN( -1 ),
    NOP( 0 ),
    SENDIR( 1 ),
    GETVERSION( 2 ),
    COMPLETEIR( 3 ),
    STOPIR( 4 );

    private final int value;

    private Mnemonic(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public final ApiFunction.Mnemonic mnemonic;
  public final IR ir;

  public ApiFunction(ApiFunction.Mnemonic mnemonic, IR ir) {
    this.mnemonic = mnemonic;
    this.ir = ir;
  }

  public static ApiFunction parse(String data) {
    String[] parts = data.split( "," );
    return parse( parts );
  }

  public static ApiFunction parse(String[] parts) {
    ApiFunction.Mnemonic mnemonic = Mnemonic.NOP;
    if( parts != null && parts.length != 0 ) {
      String value = parts[0].trim().toUpperCase();
      mnemonic =
          tryFind( asList( Mnemonic.values() ), compose( equalTo( value ), toStringFunction() ) )
              .or( Mnemonic.UNKNOWN );
    }
    IR ir = mnemonic == Mnemonic.SENDIR ? IR.parse( parts ) : null;
    return new ApiFunction( mnemonic, ir );
  }
}