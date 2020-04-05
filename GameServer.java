package com.company.game;

import com.company.game.misc.DataToSend;

import java.net.InetAddress;
import java.util.HashMap;


public class GameServer extends SenderReciever {
    int maxClients;
    int numConnectedClients;

    HashMap<InetAddress, DataToSend> clientsData;
    HashMap<InetAddress, Integer> addressPortMap;

    GameServer(int maxClients, int port) {
        super(port);
        System.out.println("Server initialization");
        this.maxClients = maxClients;
        numConnectedClients = 0;

        clientsData = new HashMap<InetAddress, DataToSend>();
        addressPortMap = new HashMap<InetAddress, Integer>();
    }

    public void Start() {
        // TODO
        try {
            Listen(1000, (message, datagramPacket) -> {
                System.out.println(message);
                if (message.equals(MessageType.CONNECT_REQUEST)) {
                    if (clientsData.get(datagramPacket.getAddress()) == null) {
                        clientsData.put(datagramPacket.getAddress(), new DataToSend());
                        addressPortMap.put(datagramPacket.getAddress(), datagramPacket.getPort());
                    }
                    Send(MessageType.CONNECT_RESPONSE, datagramPacket.getAddress(), datagramPacket.getPort());
                } else {
                    clientsData.put(datagramPacket.getAddress(), DataToSend.ParseData(message));
                }
            });
        } catch (InterruptedException ex) {
            System.err.println("Recieve exception");
            ex.printStackTrace();
        }

    }
}
