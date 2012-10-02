package com.github.debaser121.itach.toolkit;

public class Transmission {
  private final Command request;
  private Command response;

  public Transmission(Command request) {
    this.request = request;
  }

  public Transmission(Command request, Command response) {
    this.request = request;
    this.response = response;
  }

  public Command getResponse() {
    return response;
  }

  public void setResponse(Command response) {
    this.response = response;
  }

  public Command getRequest() {
    return request;
  }
}
