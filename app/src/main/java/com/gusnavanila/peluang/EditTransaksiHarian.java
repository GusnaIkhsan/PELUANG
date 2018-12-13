package com.gusnavanila.peluang;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gusnavanila.peluang.Database.DatabaseHelper;

import java.util.ArrayList;

public class EditTransaksiHarian extends AppCompatActivity {
    Spinner _spinnerTipe, _spinnerKategori;
    EditText _jumlah,_deskripsi;
    DatabaseHelper _database;
    Button _simpan,_batal;
    int _idTipe,_idtransaksi,posisi;

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
                ArrayAdapter<String> adapterKategori = new ArrayAdapter<String>(EditTransaksiHarian.this,
                        android.R.layout.simple_spinner_item, spinnerKategori);
                adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _spinnerKategori.setAdapter(adapterKategori);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getDataFromBundle();

        _simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = _database.getIdTipe( _spinnerTipe.getSelectedItem().toString(),_spinnerKategori.getSelectedItem().toString());
                while (result.moveToNext()){
                    _idTipe = Integer.parseInt(result.getString(0));
                }
//                Toast.makeText(EditTransaksiHarian.this,String.valueOf(_idtransaksi)+" "
//                        +String.valueOf(_idTipe)+" "+_jumlah.getText().toString()+" "
//                        +_deskripsi.getText().toString(), Toast.LENGTH_SHORT).show();

                boolean editDataTransaksiHarian = _database.editTransaksiHarian(_idtransaksi,_idTipe,
                        Integer.parseInt(_jumlah.getText().toString()),_deskripsi.getText().toString());
                if (editDataTransaksiHarian){
                    Toast.makeText(EditTransaksiHarian.this,"Data berhasil diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditTransaksiHarian.this,"Data gagal diubah", Toast.LENGTH_SHORT).show();
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

    public void getDataFromBundle(){
        Intent intent = getIntent();
        if (getIntent().getExtras()!=null){
            final Bundle bundle = intent.getExtras();
            _idtransaksi = bundle.getInt("id");
            _jumlah.setText(String.valueOf(bundle.getInt("jumlah")));
            _deskripsi.setText(bundle.getString("deskripsi"));

            if (bundle.getString("tipe").equalsIgnoreCase("pemasukan")){
                _spinnerTipe.setSelection(0);
                _spinnerTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Cursor data = _database.getKategori(_spinnerTipe.getSelectedItem().toString());
                        ArrayList<String> spinnerKategori = new ArrayList<>();
                        while(data.moveToNext()){
                            spinnerKategori.add(data.getString(0));
                        }
                        ArrayAdapter<String> adapterKategori = new ArrayAdapter<String>(EditTransaksiHarian.this,
                                android.R.layout.simple_spinner_item, spinnerKategori);
                        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        _spinnerKategori.setAdapter(adapterKategori);
                        _spinnerKategori.setSelection(i+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }else{
                _spinnerTipe.setSelection(1);
                _spinnerTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Cursor data = _database.getKategori(_spinnerTipe.getSelectedItem().toString());
                        ArrayList<String> spinnerKategori = new ArrayList<>();
                        while(data.moveToNext()){
                            spinnerKategori.add(data.getString(0));
                        }
                        ArrayAdapter<String> adapterKategori = new ArrayAdapter<String>(EditTransaksiHarian.this,
                                android.R.layout.simple_spinner_item, spinnerKategori);
                        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        _spinnerKategori.setAdapter(adapterKategori);
                        _spinnerKategori.setSelection(i+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

        }
    }
}
