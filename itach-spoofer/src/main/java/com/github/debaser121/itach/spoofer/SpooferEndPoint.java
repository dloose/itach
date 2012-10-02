package com.github.debaser121.itach.spoofer;

import com.github.debaser121.itach.toolkit.EndPoint.VirtualEndPoint;
import com.github.debaser121.itach.toolkit.Itach;

public class SpooferEndPoint extends VirtualEndPoint {

  private final String endPoint;

  public SpooferEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  @Override
  public Itach iTachFactory() throws Exception {
    return new SpooferItach( endPoint );
  }

}
