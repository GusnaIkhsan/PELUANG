package com.gusnavanila.peluang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DataRiwayatTransaksiAdapter extends RecyclerView.Adapter<DataRiwayatTransaksiAdapter.DataRiwayatTransaksiViewHolder> {
    LayoutInflater _inflater;
    ArrayList<DataRiwayatTransaksi> dataRiwayatTransaksi;

    public DataRiwayatTransaksiAdapter(Context context,ArrayList<DataRiwayatTransaksi> dataRiwayatTransaksi) {
        this._inflater = LayoutInflater.from(context);
        this.dataRiwayatTransaksi = dataRiwayatTransaksi;
    }

    @Override
    public DataRiwayatTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = _inflater.inflate(R.layout.row_riwayat, viewGroup,false);
        return new DataRiwayatTransaksiAdapter.DataRiwayatTransaksiViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(DataRiwayatTransaksiViewHolder dataRiwayatTransaksiViewHolder, int i) {
        DataRiwayatTransaksi data = dataRiwayatTransaksi.get(i);
        dataRiwayatTransaksiViewHolder._tipe.setText(data._tipe);
        dataRiwayatTransaksiViewHolder._kategori.setText(data._kategori);
        dataRiwayatTransaksiViewHolder._deskripsi.setText(data._deskripsi);
        dataRiwayatTransaksiViewHolder._nominal.setText("Rp. "+String.valueOf(data._jumlah));
    }

    @Override
    public int getItemCount() {
        return dataRiwayatTransaksi.size();
    }

    public class DataRiwayatTransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView _tipe, _kategori, _deskripsi, _nominal;
        DataRiwayatTransaksiAdapter _adapter;
        public DataRiwayatTransaksiViewHolder(View itemView, DataRiwayatTransaksiAdapter dataRiwayatTransaksiAdapter) {
            super(itemView);

            _tipe = itemView.findViewById(R.id.tvRiwayatTipe);
            _kategori = itemView.findViewById(R.id.tvRiwayatKategori);
            _deskripsi = itemView.findViewById(R.id.tvRiwayatDeskripsi);
            _nominal = itemView.findViewById(R.id.tvNominal);

            this._adapter = dataRiwayatTransaksiAdapter;
        }
    }
}
