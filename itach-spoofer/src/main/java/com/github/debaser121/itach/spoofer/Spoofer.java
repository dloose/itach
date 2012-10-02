package com.github.debaser121.itach.spoofer;

import static java.util.Arrays.asList;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.github.debaser121.itach.toolkit.EndPoint;

public class Spoofer {

  public static void main(String[] args) {
    OptionParser parser = new OptionParser();
    ArgumentAcceptingOptionSpec<String> localEndpointSpec =
        parser
            .acceptsAll( asList( "l", "lep", "local-endpoint" ),
                         "The host name of the local end point" )
            .withRequiredArg().ofType( String.class ).required();
    ArgumentAcceptingOptionSpec<String> itachEndpointSpec =
        parser
            .acceptsAll( asList( "i", "iep", "itach-endpoint" ),
                         "The host name of the iTach end point" )
            .withRequiredArg().ofType( String.class ).required();

    OptionSet options = parser.parse( args );

    EndPoint localEndPoint = new EndPoint.IpEndPoint( options.valueOf( localEndpointSpec ) );
    EndPoint itachEndPoint = new SpooferEndPoint( options.valueOf( itachEndpointSpec ) );

    new Proxy( localEndPoint, itachEndPoint, true ).start();
  }

}
