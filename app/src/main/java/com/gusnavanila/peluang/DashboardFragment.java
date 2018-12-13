package com.gusnavanila.peluang;

//import android.app.Fragment;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gusnavanila.peluang.CustomClickListener.DataHarianOnClickListener;
import com.gusnavanila.peluang.CustomClickListener.RiwayatDataOnClickListener;
import com.gusnavanila.peluang.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class DashboardFragment extends Fragment {
    RecyclerView _recycleView, _recycleView2;
    TextView _totalPemasukan,_totalPengeluaran,_tanggal;
    DataHarianAdapter _dataHarianAdapter;
    DataTransaksiHarianAdapter _dataTransaksiHarianAdapter;
    ArrayList<DataHarian> _dataHarian = new ArrayList<DataHarian>();
    ArrayList<DataTransaksiHarian> _dataTransaksiHarian = new ArrayList<DataTransaksiHarian>();
    Button _tambah, _hapusTransaksiHarian,_editTransaksiHarian;
    DatabaseHelper _database;
    String _tanggalHariIni;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        _database = new DatabaseHelper(getActivity());
        _tambah = rootView.findViewById(R.id.button);
        _totalPemasukan = rootView.findViewById(R.id.totalPemasukan);
        _totalPengeluaran = rootView.findViewById(R.id.totalPengeluaran);
        _tanggal = rootView.findViewById(R.id.tanggalHariIni);
        Calendar cal = Calendar.getInstance();
        _tanggalHariIni = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
        _tanggal.setText("Hari ini, "+_tanggalHariIni);

        Cursor totalPengeluaran = _database.getTotal("Pengeluaran",_tanggalHariIni);
        while(totalPengeluaran.moveToNext()){
            _totalPengeluaran.setText("Rp. "+String.valueOf(totalPengeluaran.getInt(0)));
        }

        Cursor totalPemasukan = _database.getTotal("Pemasukan",_tanggalHariIni);
        while(totalPemasukan.moveToNext()){
            _totalPemasukan.setText("Rp. "+String.valueOf(totalPemasukan.getInt(0)));
        }

        Cursor dataTransaksi = _database.getTransaksiHarian(_tanggalHariIni);
        while (dataTransaksi.moveToNext()){
            _dataTransaksiHarian.add(new DataTransaksiHarian(dataTransaksi.getInt(0),dataTransaksi.getString(1),
                    dataTransaksi.getString(2),dataTransaksi.getString(3), dataTransaksi.getInt(4)));
        }
//        _dataTransaksiHarian.add(new DataTransaksiHarian("Makanan","Makan Siang",15000));

        _recycleView2 = rootView.findViewById(R.id.recyclerView2);
        _dataTransaksiHarianAdapter = new DataTransaksiHarianAdapter(getActivity(), _dataTransaksiHarian, new DataHarianOnClickListener() {
            @Override
            public void onItemClicked(int id, String tipe, String kategori, int jumlah, String deskripsi, int posisi) {
                hapusAtauEdit(id,tipe,kategori,jumlah,deskripsi,posisi);
            }
        });
        _recycleView2.setAdapter(_dataTransaksiHarianAdapter);
        _recycleView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(_recycleView2.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        _recycleView2.addItemDecoration(horizontalDecoration);

        Cursor dataKemarin = _database.getTotalKemarin();
        while (dataKemarin.moveToNext()){
            _dataHarian.add(new DataHarian(dataKemarin.getString(0),dataKemarin.getInt(1),100000));
        }

        _recycleView = rootView.findViewById(R.id.recyclerView);
        _dataHarianAdapter = new DataHarianAdapter(getActivity(), _dataHarian, new RiwayatDataOnClickListener() {
            @Override
            public void onItemClicked(String tanggal) {
                dataRiwayat(tanggal);
            }
        });
        _recycleView.setAdapter(_dataHarianAdapter);
        _recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        _tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambahTransaksi = new Intent(getActivity(),TambahTransaksi.class);
                startActivity(tambahTransaksi);
            }
        });

        return rootView;
    }

    public void hapusAtauEdit(final int id, final String tipe, final String kategori, final int jumlah, final String deskripsi, final int posisi){
        AlertDialog.Builder mBuilderHapusEdit = new AlertDialog.Builder(getActivity());
        View mViewHapusEdit = getLayoutInflater().inflate(R.layout.alert_dialog_persetujuan, null);

        _hapusTransaksiHarian = mViewHapusEdit.findViewById(R.id.btnHapus);
        _editTransaksiHarian = mViewHapusEdit.findViewById(R.id.btnEdit);

        mBuilderHapusEdit.setView(mViewHapusEdit);
        final AlertDialog dialog = mBuilderHapusEdit.create();
        dialog.show();

        _hapusTransaksiHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),String.valueOf(id)+" "+String.valueOf(posisi)
//                        +" "+deskripsi, Toast.LENGTH_SHORT).show();
                Boolean deleteData = _database.deleteTransaksiHarian(id);
                if (deleteData){
                    Toast.makeText(getActivity(),"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    _dataTransaksiHarian.remove(posisi);
                    _dataTransaksiHarianAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(),"Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _editTransaksiHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),String.valueOf(id)+" "+tipe
//                        +" "+kategori+" "+String.valueOf(jumlah)+" "+deskripsi+" "+posisi, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("tipe",tipe);
                bundle.putString("kategori", kategori);
                bundle.putInt("jumlah", jumlah);
                bundle.putString("deskripsi", deskripsi);
                bundle.putInt("posisi", posisi);

                Intent editTransaksi = new Intent(getActivity(),EditTransaksiHarian.class);
                editTransaksi.putExtras(bundle);
                startActivity(editTransaksi);
            }
        });
    }

    public void dataRiwayat(String tanggal){
        Bundle bundle = new Bundle();
        bundle.putString("tanggal",tanggal);
//        Toast.makeText(getActivity(),tanggal, Toast.LENGTH_SHORT).show();
        Intent riwayat = new Intent(getActivity(),RiwayatTransaksi.class);
        riwayat.putExtras(bundle);
        startActivity(riwayat);
    }
}
