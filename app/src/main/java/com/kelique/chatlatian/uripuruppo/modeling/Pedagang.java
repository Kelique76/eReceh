package com.kelique.chatlatian.uripuruppo.modeling;

/**
 * Created by kelique on 11/16/2017.
 */

public class Pedagang {
//data yang ditarik dari Firebase ini saja, sedangkan data yang akan dipakai untuk transaksi di TransaskiPedagangActivity hanya periode saja
    String key;
    private String alamatUrl;
    private String nama;
    private String alamat;
    private String permodalan;
    private String bagi_hasil;
    private String periode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getalamatUrl() {
        return alamatUrl;
    }

    public void setalamatUrl(String alamatUrl) {
        this.alamatUrl = alamatUrl;
    }

    public String getnama() {
        return nama;
    }

    public void setnama(String name) {
        this.nama = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getpermodalan() {
        return permodalan;
    }

    public void setpermodalan(String modal) {
        this.permodalan = modal;
    }

    public String getbagi_hasil() {
        return bagi_hasil;
    }

    public void setbag_hasil(String bghasil) {
        this.bagi_hasil = bghasil;
    }

    public String getperiode() {
        return periode;
    }

    public void setperiode(String periode) {
        this.periode = periode;
    }
    //    public Pedagang() {
//    }
//
//    public Pedagang( String alamatUrl, String name, String alamat, String modal, String bghasil, String periode) {
//        this.alamatUrl = alamatUrl;
//        this.name = name;
//        this.alamat = alamat;
//        this.modal = modal;
//        this.bghasil = bghasil;
//        this.periode = periode;
//    }
//
//
//    public String getalamatUrl() {
//        return alamatUrl;
//    }
//
//    public void setalamatUrl(String alamatUrl) {
//        this.alamatUrl = alamatUrl;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAlamat() {
//        return alamat;
//    }
//
//    public void setAlamat(String alamat) {
//        this.alamat = alamat;
//    }
//
//    public String getModal() {
//        return modal;
//    }
//
//    public void setModal(String modal) {
//        this.modal = modal;
//    }
//
//    public String getBghasil() {
//        return bghasil;
//    }
//
//    public void setBghasil(String bghasil) {
//        this.bghasil = bghasil;
//    }
//
//    public String getPeriode() {
//        return periode;
//    }
//
//    public void setPeriode(String periode) {
//        this.periode = periode;
//    }
}
