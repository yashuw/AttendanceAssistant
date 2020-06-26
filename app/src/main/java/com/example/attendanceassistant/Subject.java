package com.example.attendanceassistant;

public class Subject {
    private String subname;
    private int attended;
    private int conducted;

    public Subject() {
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(Integer attended) {
        this.attended = attended;
    }

    public int getConducted() {
        return conducted;
    }

    public void setConducted(Integer conducted) {
        this.conducted = conducted;
    }
}
