package com.example.ujikompetensidts;

public class Note {
    private int id;
    private String nama;
    private String ttl;
    private String jkelamin;
    private String alamat;
    private String date;
    public Note() {}
    public Note(int id, String nama, String ttl, String jkelamin, String alamat, String date) {
        this.id = id;
        this.nama = nama;
        this.ttl = ttl;
        this.jkelamin= jkelamin;
        this.alamat = alamat;
        this.date = date;
    }
    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return nama; }
    public void setName(String nama) { this.nama = nama; }
    public String getBornDate() { return ttl; }
    public void setBornDate(String ttl) { this.ttl = ttl; }
    public String getGender() { return jkelamin; }
    public void setGender(String jkelamin) { this.jkelamin = jkelamin; }
    public String getAddress() { return alamat; }
    public void setAddress(String alamat) { this.alamat = alamat; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}