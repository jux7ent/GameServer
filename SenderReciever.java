package com.company.game;

import com.company.game.misc.DataProcessor;
import com.company.game.misc.Misc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static java.lang.Thread.sleep;
import static javafx.application.Platform.exit;

public class SenderReciever {
    protected DatagramSocket datagramSocket;

    protected static class RecieverData {
        InetAddress address;
        int port;
    }

    SenderReciever(int port) {
        System.out.println("SenderReciever initialization");

        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.err.println("Socket exception");
            ex.printStackTrace();
            exit();
        }
    }

    protected void Recieve(int delayMills, DataProcessor processor) throws InterruptedException {
        byte[] recieve_buff = new byte[1 << 16]; // 2 bytes
        DatagramPacket datagramPacket = new DatagramPacket(recieve_buff, recieve_buff.length);

        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
                processor.Process(Misc.BytesToString(recieve_buff));
            } catch (IOException ex) {
                System.err.println("Send error");
                ex.printStackTrace();
            }
        }
    }

    protected void Send(Object message, RecieverData[] recievers, int delayMills) throws InterruptedException {
        while (true) {
            byte[] send_buff = Misc.StringToBytes(message.toString());
            DatagramPacket datagramPacket = new DatagramPacket(send_buff, send_buff.length);

            try {
                for (RecieverData reciever : recievers) {
                    datagramPacket.setAddress(reciever.address);
                    datagramPacket.setPort(reciever.port);
                    datagramSocket.send(datagramPacket);
                }
            } catch (IOException ex) {
                System.err.println("Send error");
                ex.printStackTrace();
            }

            sleep(delayMills);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        datagramSocket.close();
    }
}
