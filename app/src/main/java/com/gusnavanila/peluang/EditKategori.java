package com.gusnavanila.peluang;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gusnavanila.peluang.CustomClickListener.MyOnClickListener;
import com.gusnavanila.peluang.Database.DatabaseHelper;

import java.io.StringReader;
import java.util.ArrayList;

public class EditKategori extends AppCompatActivity {
    Button _dialogTambahKategori, _TambahKategori, _hapusKategori, _editKategori;
    Spinner _spinnerTipeKategori;
    EditText _kategori;
    RecyclerView _rvKategori;
    DatabaseHelper _database;
    DataKategoriAdapter _dataKategoriAdapter;
    ArrayList<DataKategori> _dataKategori = new ArrayList<DataKategori>();
    String[] arraySpinnerTipeKategori = new String[] {
            "Pemasukan", "Pengeluaran"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kategori);

        _rvKategori = findViewById(R.id.rvKategori);
        _dialogTambahKategori = findViewById(R.id.tambahKategori);
        _database = new DatabaseHelper(this);

        Cursor data = _database.getAllTipe();
        while (data.moveToNext()){
            _dataKategori.add(new DataKategori(data.getInt(0),data.getString(1),data.getString(2)));
        }

        _rvKategori.findViewById(R.id.rvKategori);
        _dataKategoriAdapter = new DataKategoriAdapter(this, _dataKategori, new MyOnClickListener() {
            @Override
            public void onItemClicked(int id, String tipe,String kategori, int posisi) {
                persetujuanHapusAtauEdit(id, tipe, kategori, posisi);
            }
        });
        _rvKategori.setAdapter(_dataKategoriAdapter);
        _rvKategori.setLayoutManager(new LinearLayoutManager(this));

        _dialogTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahKategori();
            }
        });

    }

    public void tambahKategori(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditKategori.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog_kategori, null);
        _kategori = mView.findViewById(R.id.etKategori);
        _spinnerTipeKategori = mView.findViewById(R.id.spTipe);
        _TambahKategori = mView.findViewById(R.id.btnTambahKategori);

        ArrayAdapter<String> adapterTipeKategori = new ArrayAdapter<String>
                (EditKategori.this,android.R.layout.simple_spinner_item, arraySpinnerTipeKategori);
        adapterTipeKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerTipeKategori.setAdapter(adapterTipeKategori);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        _TambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_kategori.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(EditKategori.this,"Kategori Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    boolean addDataKategori = _database.addDataKategori(_spinnerTipeKategori.getSelectedItem().toString(),
                            _kategori.getText().toString());
                    if (addDataKategori){
                        Toast.makeText(EditKategori.this,"Data Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }else{
                        Toast.makeText(EditKategori.this,"Data Gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void persetujuanHapusAtauEdit(final int id, final String tipe, final String kategori, final int posisi){
//        Toast.makeText(EditKategori.this,String.valueOf(pos)+" "+tipe+" "+kategori, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder mBuilderHapusEdit = new AlertDialog.Builder(EditKategori.this);
        View mViewHapusEdit = getLayoutInflater().inflate(R.layout.alert_dialog_persetujuan, null);

        _hapusKategori = mViewHapusEdit.findViewById(R.id.btnHapus);
        _editKategori = mViewHapusEdit.findViewById(R.id.btnEdit);

        mBuilderHapusEdit.setView(mViewHapusEdit);
        final AlertDialog dialog = mBuilderHapusEdit.create();
        dialog.show();

        _hapusKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(EditKategori.this,String.valueOf(id)+" "+String.valueOf(posisi), Toast.LENGTH_SHORT).show();
                Boolean deleteData = _database.deleteKategori(id);

                if (deleteData){
                    Toast.makeText(EditKategori.this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    _dataKategori.remove(posisi);
                    _dataKategoriAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(EditKategori.this,"Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _editKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                editKategori(id, tipe, kategori, posisi);
            }
        });

    }

    public void editKategori(final int id, final String tipe, final String kategori, final int posisi){
//        Toast.makeText(EditKategori.this,String.valueOf(pos)+" "+tipe+" "+kategori, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder mBuilderEdit = new AlertDialog.Builder(EditKategori.this);
        View mViewEdit = getLayoutInflater().inflate(R.layout.alert_dialog_kategori, null);
        _kategori = mViewEdit.findViewById(R.id.etKategori);
        _spinnerTipeKategori = mViewEdit.findViewById(R.id.spTipe);
        _TambahKategori = mViewEdit.findViewById(R.id.btnTambahKategori);
        _kategori.setText(kategori);

        ArrayAdapter<String> adapterTipeKategori = new ArrayAdapter<String>
                (EditKategori.this,android.R.layout.simple_spinner_item, arraySpinnerTipeKategori);
        adapterTipeKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerTipeKategori.setAdapter(adapterTipeKategori);
        if (tipe.equalsIgnoreCase("pemasukan")){
            _spinnerTipeKategori.setSelection(0);
        }else{
            _spinnerTipeKategori.setSelection(1);
        }

        mBuilderEdit.setView(mViewEdit);
        final AlertDialog dialog = mBuilderEdit.create();
        dialog.show();

        _TambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                        Toast.makeText(EditKategori.this,String.valueOf(pos)+" "+
//                                _spinnerTipeKategori.getSelectedItem().toString()+" "+_kategori.getText().toString(),
//                                Toast.LENGTH_SHORT).show();
                if (_kategori.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(EditKategori.this,"Kategori Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean editData = _database.editKategori(id,_spinnerTipeKategori.getSelectedItem().toString(),
                            _kategori.getText().toString());
                    if (editData){
                        Toast.makeText(EditKategori.this,"Data Berhasil Diedit", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }else {
                        Toast.makeText(EditKategori.this,"Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
