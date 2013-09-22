package org.minetweak.entity.player;

import com.google.gson.annotations.Expose;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlayerInfo {
    @Expose
    private String lastLogin;

    private String username;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");


    public PlayerInfo(String username) {
        this.username = username;
        this.lastLogin = Calendar.getInstance().getTime().toString();
    }

    public Date getLastLogin() {
        try {
            return dateFormat.parse(lastLogin);
        } catch (ParseException e) {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }
}
