package com.github.debaser121.itach.toolkit;

import java.net.SocketException;
import java.util.Date;

public class TubeDispatcher
{
    private static Date ServerUpSince = new Date();
    private static volatile int ConnectionTurnover = 0;
    private static volatile int RequestTurnover = 0;

    public delegate void OnRequestCallback(Tube tube);

    private static class SocketTube extends Tube    {
        protected TcpClient client;
        protected int port = 0;

        public SocketTube(int port)
            : base()
        {
            port = port;
        }

        public SocketTube(TcpClient client)
        {
            this.client = client;
        }

        public override bool IsConnected
        {
            get { return client != null && client.Connected; }
        }

        protected override System.IO.Stream Stream
        {
            get { return IsConnected ? client.GetStream() : null; }
        }

        public override void Disconnect()
        {
            if (IsConnected) {
              client.Close();
            }
        }
    }

    public static Tube itachTube(String host, int port)
    {
        var t = new SocketClientTube(host, port);
        t.Connect();
        return t;
    }

    private class SocketClientTube : SocketTube
    {
        private string host;

        public SocketClientTube(string host, int port)
            : base(port)
        {
            this.host = host;
        }

        public SocketClientTube(int port)
            : this("127.0.0.1", port)
        {
        }

        public bool Connect()
        {
            client = new TcpClient(host, port);
            return IsConnected;
        }
    }

    private TcpListener _listener;
    private Thread _worker;
    private MonitoredThreadSafeQueue<Tube> connections;
    private MonitoredThreadSafeQueue<int> sps;

    private int maxClients;

    private EndPoint.IpEndPoint endPoint;

    public TubeDispatcher(EndPoint.IpEndPoint endPoint, int maxClients)
    {
        this.endPoint = endPoint;
        this.maxClients = maxClients;

        sps = new MonitoredThreadSafeQueue<int>(maxClients);

        for (int i = 0; i < maxClients; i++)
        {
            sps.Enqueue(1);
        }

        connections = new MonitoredThreadSafeQueue<Tube>(maxClients);
    }

    public TubeDispatcher(EndPoint.IpEndPoint endPoint)
        : this(endPoint, 8)
    {
    }

    public Tube WaitForConnection()
    {
        Tube t = connections.Dequeue();
        ConnectionTurnover++;
        return t;
    }

    public virtual void Return(Tube tube)
    {
        tube.Disconnect();
        sps.Enqueue(1);
    }

    public void Start()
    {
        System.Net.IPAddress hostIP = System.Net.Dns.GetHostEntry(endPoint.Host).AddressList[0];
        _listener = new TcpListener(new System.Net.IPEndPoint(hostIP, endPoint.Port));
        _worker = new Thread(_workerproc);
        _worker.Start();
    }

    public void Stop()
    {
        _listener.Stop();
        _worker.Join();
    }

    private void _workerproc(object objState)
    {
    Thread.CurrentThread.Name = GetType().Name;

        _listener.Start(maxClients);

        while (true)
        {
            sps.Dequeue();

            try
            {
                connections.Enqueue(new SocketTube(_listener.AcceptTcpClient()));
            }
            catch (SocketException)
            {
                // listener has been stopped so we stop
                break;
            }
        }

        // signal stop
        connections.Enqueue(null);
    }
}
