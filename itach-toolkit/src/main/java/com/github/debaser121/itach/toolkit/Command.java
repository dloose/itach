package com.github.debaser121.itach.toolkit;

import java.util.Date;

import com.google.common.base.Objects;

public class Command {

  private final ApiFunction function;
  private final int module;
  private final int connector;
  private final Date timestamp;

  public Command(ApiFunction function, int module, int connector, Date timestamp) {
    this.function = function;
    this.module = module;
    this.connector = connector;
    this.timestamp = timestamp;
  }

  public static Command parse(String data) {
    String[] parts = data.split( "," );
    return parse( parts );
  }

  public static Command parse(String[] parts) {
    ApiFunction function = ApiFunction.parse( parts );

    int module = -1, connector = -1;
    if( parts.length >= 2 ) {
      parts = parts[1].split( ":" );
      if( parts.length == 2 && parts[0].matches( "\\d+" ) && parts[1].matches( "\\d+" ) ) {
        module = Integer.parseInt( parts[0] );
        connector = Integer.parseInt( parts[1] );
      }
    }
    return new Command( function, module, connector, new Date() );
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public ApiFunction getFunction() {
    return function;
  }

  public int getModule() {
    return module;
  }

  public int getConnector() {
    return connector;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode( getFunction(), getModule(), getConnector(), getTimestamp() );
  }

  @Override
  public boolean equals(Object obj) {
    if( this == obj ) {
      return true;
    }
    if( obj == null || getClass() != obj.getClass() ) {
      return false;
    }
    Command other = (Command) obj;
    return getFunction() == other.getFunction() && getModule() == other.getModule() &&
        getConnector() == other.getConnector() && getTimestamp().equals( other.getTimestamp() );
  }

}
