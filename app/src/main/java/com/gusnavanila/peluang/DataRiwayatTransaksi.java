package com.gusnavanila.peluang;

public class DataRiwayatTransaksi {
    int _id,_jumlah;
    String _tipe, _kategori, _deskripsi;
    public DataRiwayatTransaksi(int id, String tipe, String kategori, int jumlah, String deskripsi) {
        this._id = id;
        this._tipe = tipe;
        this._kategori = kategori;
        this._jumlah = jumlah;
        this._deskripsi = deskripsi;
    }
}
