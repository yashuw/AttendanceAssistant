package com.example.attendanceassistant;

public class SubjectData {
    private String subjectname;
    private int attended;
    private int conducted;


    public SubjectData() {
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getConducted() {
        return conducted;
    }

    public void setConducted(int conducted) {
        this.conducted = conducted;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
}
