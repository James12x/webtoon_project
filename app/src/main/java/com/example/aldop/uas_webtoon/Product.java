package com.example.aldop.uas_webtoon;

/**
 * Created by andre on 5/1/2017.
 */

public class Product {
    private String nama;
    private int id;
    private int harga;
    private String deskripsi;

    public Product(String nama, int id, int harga, String deskripsi) {
        this.setNama(nama);
        this.setId(id);
        this.setHarga(harga);
        this.setDeskripsi(deskripsi);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
