package com.github.debaser121.itach.spoofer;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.debaser121.itach.toolkit.EndPoint.IpEndPoint;
import com.github.debaser121.itach.toolkit.Itach.IpItach;

public class SpooferItach extends IpItach {

  private static final Logger logger = LoggerFactory.getLogger( SpooferItach.class );

  private int messageCount = 0;

  public SpooferItach(String endPoint) throws MalformedURLException {
    super( new IpEndPoint( endPoint ) );
  }

  @Override
  public String transceive(String data) {
    // Command request = Command.parse( data );
    String responseData = super.transceive( data );
    // Command response = Command.parse( responseData );
    // Transmission transmission = new Transmission( request, response );
    logger.info( "{}: {}", messageCount++, data );
    return responseData;
  }
}
