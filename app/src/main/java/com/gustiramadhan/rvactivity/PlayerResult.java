package com.gustiramadhan.rvactivity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerResult {

    @SerializedName(value = "player", alternate ={"players"} )

    private ArrayList<Player>player;

    public PlayerResult(ArrayList<Player> player) {
        this.player = player;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }
}
