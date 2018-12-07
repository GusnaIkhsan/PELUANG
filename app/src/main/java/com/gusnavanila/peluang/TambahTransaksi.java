package com.gusnavanila.peluang;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gusnavanila.peluang.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahTransaksi extends AppCompatActivity {
    Spinner _spinnerTipe, _spinnerKategori;
    EditText _jumlah,_deskripsi;
    DatabaseHelper _database;
    Button _simpan,_batal;
    String  _des,_tanggal,_tipe,_kategori;
    int _jum,_idTipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);

        _spinnerTipe = findViewById(R.id.spinnerTipe);
        _spinnerKategori = findViewById(R.id.spinnerKategori);
        _jumlah = findViewById(R.id.editText);
        _deskripsi = findViewById(R.id.editText2);
        _simpan = findViewById(R.id.btn_simpan);
        _batal = findViewById(R.id.btn_batal);

        _database = new DatabaseHelper(this);

        String[] arraySpinnerTipe = new String[] {
                "Pemasukan", "Pengeluaran"
        };

        ArrayAdapter<String> adapterTipe = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerTipe);
        adapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _spinnerTipe.setAdapter(adapterTipe);

        _spinnerTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor data = _database.getKategori(_spinnerTipe.getSelectedItem().toString());
                ArrayList<String> spinnerKategori = new ArrayList<>();
                while(data.moveToNext()){
                    spinnerKategori.add(data.getString(0));
                }
                ArrayAdapter<String> adapterKategori = new ArrayAdapter<String>(TambahTransaksi.this,
                        android.R.layout.simple_spinner_item, spinnerKategori);
                adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _spinnerKategori.setAdapter(adapterKategori);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        _simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_jumlah.getText().toString().equalsIgnoreCase("")&&_deskripsi.getText().toString().equalsIgnoreCase("")){
                    toastMessage("Jumlah dan Deskripsi tidak boleh kosong");
                }else{
                    Cursor result = _database.getIdTipe( _spinnerTipe.getSelectedItem().toString(),_spinnerKategori.getSelectedItem().toString());
                    while (result.moveToNext()){
                        _idTipe = Integer.parseInt(result.getString(0));
                    }
                    _jum = Integer.parseInt(_jumlah.getText().toString());
                    _des = _deskripsi.getText().toString();
                    Calendar cal = Calendar.getInstance();
                    _tanggal = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));

                    boolean insertData = _database.addData(_spinnerTipe.getSelectedItem().toString(),_idTipe,_jum,_des,_tanggal);
//                    toastMessage(_spinnerTipe.getSelectedItem().toString()+" "+_spinnerKategori.getSelectedItem().toString()+" "+_idTipe+" "+_jum+" "+_des+" "+_tanggal);

                    if (insertData){
                        toastMessage("Data berhasil ditambahkan");
                        finish();
                    }else {
                        toastMessage("Data gagal ditambahkan");
                    }

                }
            }
        });

        _batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
