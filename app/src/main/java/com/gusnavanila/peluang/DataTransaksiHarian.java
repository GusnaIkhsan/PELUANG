package com.gusnavanila.peluang;

public class DataTransaksiHarian {
    String _kategori, _deskripsi, _tipe;
    int _transaksiHarian,_id;

    public DataTransaksiHarian(int id, String tipe, String kategori, String deskripsi, int transaksiHarian) {
        this._id = id;
        this._tipe = tipe;
        this._kategori = kategori;
        this._deskripsi = deskripsi;
        this._transaksiHarian = transaksiHarian;
    }
}
