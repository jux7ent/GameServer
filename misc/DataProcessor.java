package com.company.game.misc;

import java.net.DatagramPacket;

public interface DataProcessor {
    void Process(String message, DatagramPacket datagramPacket);
}
