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

        @Override
        public int hashCode() {
            return address.hashCode();
        }
    }

    protected static class MessageType {
        public static final String CONNECT_REQUEST = "HI";
        public static final String CONNECT_RESPONSE = "HI_OK";
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

    protected void Listen(int delayMills, DataProcessor processor) throws InterruptedException {
        byte[] recieve_buff = new byte[1 << 16]; // 2 bytes
        DatagramPacket datagramPacket = new DatagramPacket(recieve_buff, recieve_buff.length);

        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
                processor.Process(Misc.BytesToString(recieve_buff), datagramPacket);
            } catch (IOException ex) {
                System.err.println("Send error");
                ex.printStackTrace();
            }
        }
    }

    protected void SendLoop(Object message, RecieverData[] recievers, int delayMills) throws InterruptedException {
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

    protected void Send(Object message, RecieverData reciever) {
        Send(message, reciever.address, reciever.port);
    }

    protected void Send(Object message, InetAddress address, int port) {
        byte[] send_buff = Misc.StringToBytes(message.toString());
        DatagramPacket datagramPacket = new DatagramPacket(send_buff, send_buff.length, address, port);

        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException ex) {
            System.err.println("Send error");
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        datagramSocket.close();
    }
}
