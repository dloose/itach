package com.github.debaser121.itach.toolkit;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Tube implements Closeable {
  public static class ConnectionLostException extends RuntimeException
  {
    public ConnectionLostException() {
      super( "Connection Lost!" );
    }
  }

  protected abstract InputStream getInputStream();

  protected abstract OutputStream getOutputStream();

  public abstract boolean isConnected();

  public void send(String data) {
    OutputStream stream = getOutputStream();
    stream.write( data );
    stream.write( "\r" );
    stream.flush();
  }

  public String receive() {
    StringBuilder str = new StringBuilder();
    char c;

    while( true ) {
      c = (char) read();
      if( c == '\r' ) {
        break;
      }
      str.append( c );
    }

    return str.toString();
  }

  // TODO: Implement internal buffer to read more than 1 byte at a time
  private byte read()  {
      byte[] b = new byte[1];
      synchronized(this)
      {
        AsyncCallback cbDone = new AsyncCallback() {
          public callback(IAsyncResult r) {
            lock (this)
            Monitor.Pulse(this);
          }
        };

        IAsyncResult ar = getInputStream().read();
        while (!ar.IsCompleted)          {
              // if wait returned before timeout without being completed,
              // then we are very likely to have lost the connection
              if (Monitor.Wait(this, 2000) && !ar.IsCompleted) {
                throw new ConnectionLostException();
              }
          }
          return b[0];
      }
  }
}
