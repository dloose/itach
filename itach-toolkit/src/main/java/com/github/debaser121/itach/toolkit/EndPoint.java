package com.github.debaser121.itach.toolkit;

import java.net.MalformedURLException;
import java.net.URL;

public interface EndPoint {

  public Itach iTachFactory() throws Exception;

  public static class IpEndPoint implements EndPoint {
    public final String host;
    public final int port;

    public IpEndPoint(String host, int port)
    {
      this.host = host;
      this.port = port;
    }

    public IpEndPoint(URL url) {
      this( url.getHost(), url.getPort() );
    }

    public IpEndPoint(String url) throws MalformedURLException {
      this( new URL( url ) );
    }

    @Override
    public Itach iTachFactory() {
      return new Itach.IpItach( this );
    }
  }

  public static class VirtualEndPoint implements EndPoint {
    @Override
    public Itach iTachFactory() throws Exception {
      return new Itach.VirtualItach();
    }
  }
}
