package com.gookkis.latihan3firebase;

import java.io.Serializable;

public class MahasiswaModel implements Serializable {
    private String id, nama, prodi;

    public MahasiswaModel() {
    }

    public MahasiswaModel(String id, String nama, String prodi) {
        this.id = id;
        this.nama = nama;
        this.prodi = prodi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
}
