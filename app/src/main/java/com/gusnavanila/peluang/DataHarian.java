package com.gusnavanila.peluang;

public class DataHarian {
    public int pengeluaran;
    public int pemasukan;
    public String tanggal;

    public DataHarian(String tanggal, int pemasukan, int pengeluaran) {
        this.tanggal = tanggal;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
    }
}
