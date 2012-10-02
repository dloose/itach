package com.github.debaser121.itach.toolkit;

import java.io.Closeable;

public abstract class Itach implements Closeable {

  public abstract void send(String data);

  public abstract String receive();

  public String transceive(String data) {
    send( data );
    return receive();
  }

  public boolean isConnected() {
    return true;
  }

  @Override
  public void close() {
    // no default impl
  }

  public String version() {
    return transceive( "getversion" );
  }

  public static class VirtualItach extends Itach {
    private String data = "";

    @Override
    public void send(String data) {
      this.data = data.trim();
    }

    @Override
    public String receive() {
      String res;

      if( data.startsWith( "getversion" ) ) {
        res = getType().Name;
      }
      else if( data.startsWith( "sendir," ) ) {
        String[] p = data.split( "," );
        res = "completeir," + p[1] + "," + p[2];
      }
      else if( data.startsWith( "stopir" ) ) {
        res = data;
      }
      else {
        res = "ERR_0:0,001"; // unknown command
      }

      data = "";
      return res;
    }
  }

  public static class IpItach extends Itach {
    private Tube tube;

    public IpItach(EndPoint.IpEndPoint endPoint) {
      tube = TubeDispatcher.itachTube( endPoint.host, endPoint.port );
    }

    @Override
    public boolean isConnected() {
      return tube != null && tube.isConnected();
    }

    @Override
    public void send(String data) {
      tube.send( data );
    }

    @Override
    public String receive() {
      return tube.receive();
    }

    @Override
    public void close() {
      if( tube != null && tube.isConnected() ) {
        tube.close();
      }
    }

  }
}
