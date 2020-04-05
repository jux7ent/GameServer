package com.company.game.misc;


public class DataToSend {
    Vector3 pos;
    Vector3 sightDirection;

    private static final Character DATA_DIVIDER = '&';

    public DataToSend() {
        pos = new Vector3();
        sightDirection = new Vector3();
    }

    public DataToSend(Vector3 pos, Vector3 dir) {
        this.pos = pos;
        this.sightDirection = dir;
    }

    @Override
    public String toString() {
        return pos.toString() + DATA_DIVIDER + sightDirection.toString();
    }

    public static DataToSend ParseData(String strData) {
        String[] strings = strData.split(DATA_DIVIDER.toString());
        return new DataToSend(Vector3.ParseVector(strings[0]), Vector3.ParseVector(strings[1]));
    }
}
