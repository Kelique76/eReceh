package com.kelique.chatlatian.uripuruppo.modeling;

/**
 * Created by kelique on 11/16/2017.
 */

public class Pedagang {
//data yang ditarik dari Firebase ini saja, sedangkan data yang akan dipakai untuk transaksi di TransaskiPedagangActivity hanya periode saja
    String photoUrl;
    String name;
    String alamat;
    String modal;
    String bghasil;
    String periode;

    public Pedagang() {
    }

    public Pedagang( String photoUrl, String name, String alamat, String modal, String bghasil, String periode) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.alamat = alamat;
        this.modal = modal;
        this.bghasil = bghasil;
        this.periode = periode;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getBghasil() {
        return bghasil;
    }

    public void setBghasil(String bghasil) {
        this.bghasil = bghasil;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}
