package com.example.thulai;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "nameMH")
public class MatHang implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameMH;
    private String GiaTienMH;
    private String SoLuongMh;

    public MatHang() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameMH() {
        return nameMH;
    }

    public void setNameMH(String nameMH) {
        this.nameMH = nameMH;
    }

    public String getGiaTienMH() {
        return GiaTienMH;
    }

    public void setGiaTienMH(String giaTienMH) {
        GiaTienMH = giaTienMH;
    }

    public String getSoLuongMh() {
        return SoLuongMh;
    }

    public void setSoLuongMh(String soLuongMh) {
        SoLuongMh = soLuongMh;
    }

    public MatHang(String nameMH, String giaTienMH, String soLuongMh) {
        this.id = id;
        this.nameMH = nameMH;
        this.GiaTienMH = giaTienMH;
        this.SoLuongMh = soLuongMh;
    }
}
