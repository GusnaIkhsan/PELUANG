package com.gusnavanila.peluang;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.gusnavanila.peluang.Database.DatabaseHelper;

import java.util.ArrayList;

public class RiwayatTransaksi extends AppCompatActivity {
    TextView _tanggal, _pemasukan,_pengeluaran;
    RecyclerView _rvRiwayat;
    DatabaseHelper _database;
    ArrayList<DataRiwayatTransaksi> _dataRiwayat = new ArrayList<DataRiwayatTransaksi>();
    DataRiwayatTransaksiAdapter _dataRiwayatTransaksiAdapter;
    int _totalPemasukan=0,_totalPengeluaran = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_riwayat_transaksi);
        String tanggal;
        _database = new DatabaseHelper(this);
        _tanggal = findViewById(R.id.tvTanggal);
        _rvRiwayat = findViewById(R.id.rvRiwayat);
        _pemasukan = findViewById(R.id.tvPemasukan);
        _pengeluaran = findViewById(R.id.tvPengeluaran);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tanggal = bundle.getString("tanggal");
        _tanggal.setText(tanggal);

        Cursor dataRiwayat = _database.getTransaksiHarian(tanggal);
        while (dataRiwayat.moveToNext()){
            _dataRiwayat.add(new DataRiwayatTransaksi(dataRiwayat.getInt(0), dataRiwayat.getString(1),dataRiwayat.getString(2),
                    dataRiwayat.getInt(4),dataRiwayat.getString(3)));
        }

        _dataRiwayatTransaksiAdapter = new DataRiwayatTransaksiAdapter(this, _dataRiwayat);
        _rvRiwayat.setAdapter(_dataRiwayatTransaksiAdapter);
        _rvRiwayat.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i<_dataRiwayat.size(); i++){
            if (_dataRiwayat.get(i)._tipe.equalsIgnoreCase("pemasukan")){
                _totalPemasukan += _dataRiwayat.get(i)._jumlah;
            }else {
                _totalPengeluaran += _dataRiwayat.get(i)._jumlah;
            }
        }
        _pemasukan.setText("Rp. "+String.valueOf(_totalPemasukan));
        _pengeluaran.setText("Rp. "+String.valueOf(_totalPengeluaran));
    }
}
