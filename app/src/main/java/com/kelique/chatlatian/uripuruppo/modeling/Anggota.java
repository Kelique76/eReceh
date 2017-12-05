package com.kelique.chatlatian.uripuruppo.modeling;

/**
 * Created by kelique on 5/9/2017.
 */

public class Anggota {
//TODO: kemungkinan tidak terpakai
    public String nama_lengkap;
    public String alamat;
    public String nama_ibu_kandung;
    public String nama_pasangan;
    public String no_ktp;
    public String no_hp;
    public String hp_pasangan;
    public String permodalan;
    public String bagi_hasil;
    public String periode;

    public Anggota() {
    }

    public Anggota(String nama_lengkap, String alamat, String nama_ibu_kandung,
                   String nama_pasangan, String no_ktp, String no_hp,
                   String hp_pasangan, String permodalan,
                   String bagi_hasil, String periode) {
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.nama_ibu_kandung = nama_ibu_kandung;
        this.nama_pasangan = nama_pasangan;
        this.no_ktp = no_ktp;
        this.no_hp = no_hp;
        this.hp_pasangan = hp_pasangan;
        this.permodalan = permodalan;
        this.bagi_hasil = bagi_hasil;
        this.periode = periode;
    }
}
