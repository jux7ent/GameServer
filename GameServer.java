package com.company.game;

import com.company.game.misc.DataProcessor;
import java.net.InetAddress;


public class GameServer extends SenderReciever {
    int maxClients;
    int numConnectedClients;

    boolean[] clientConnected;
    InetAddress[] clientAddress;

    GameServer(int maxClients, int port) {
        super(port);
        System.out.println("Server initialization");
        this.maxClients = maxClients;
        numConnectedClients = 0;

        clientConnected = new boolean[this.maxClients];
        clientAddress = new InetAddress[this.maxClients];
    }

    public void Start() {
        // TODO
        try {
            Recieve(1000, new DataProcessor() {
                @Override
                public void Process(String message) {
                    System.out.println(message);
                }
            });
        } catch (InterruptedException ex) {
            System.err.println("Recieve exception");
            ex.printStackTrace();
        }

    }

    private InetAddress _GetClientAddress(int clientIndex) {
        return clientAddress[clientIndex];
    }

    private boolean _IsClientConnected(int clientIndex) {
        return clientConnected[clientIndex];
    }

    private int _FindExistingClientIndex(final InetAddress address) {
        for (int i = 0; i < maxClients; ++i) {
            if (clientConnected[i] && clientAddress[i] == address) {
                return i;
            }
        }
        return -1;
    }

    private int _FindFreeClientIndex() {
        for (int i = 0; i < maxClients; ++i) {
            if (!clientConnected[i])
                return i;
        }

        return -1;
    }
}
