package com.company.game;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static javafx.application.Platform.exit;

public class GameServer {
    int m_maxClients;
    int m_numConnectedClients;
    boolean[] m_clientConnected;
    InetAddress[] m_clientAddress;
    DatagramSocket m_datagramSocket;

    GameServer(int maxClients, int port) {
        System.out.println("Server initialization");
        m_maxClients = maxClients;
        m_numConnectedClients = 0;

        m_clientConnected = new boolean[m_maxClients];
        m_clientAddress = new InetAddress[m_maxClients];

        try {
            m_datagramSocket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.err.println("Socket exception");
            ex.printStackTrace();
            exit();
        }
    }

    public void Start() {
        // TODO
        m_datagramSocket.close();
    }

    private InetAddress _GetClientAddress(int clientIndex) {
        return m_clientAddress[clientIndex];
    }

    private boolean _IsClientConnected(int clientIndex) {
        return m_clientConnected[clientIndex];
    }

    private int _FindExistingClientIndex(final InetAddress address) {
        for (int i = 0; i < m_maxClients; ++i) {
            if (m_clientConnected[i] && m_clientAddress[i] == address) {
                return i;
            }
        }
        return -1;
    }

    private int _FindFreeClientIndex() {
        for (int i = 0; i < m_maxClients; ++i) {
            if (!m_clientConnected[i])
                return i;
        }

        return -1;
    }
}
