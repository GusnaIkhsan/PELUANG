package com.gusnavanila.peluang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gusnavanila.peluang.CustomClickListener.DataHarianOnClickListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DataTransaksiHarianAdapter extends RecyclerView.Adapter<DataTransaksiHarianAdapter.DataTransaksiHarianViewHolder> {
    LayoutInflater _inflater;
    ArrayList<DataTransaksiHarian> dataTransaksiHarian;
    DataHarianOnClickListener dataHarianOnClickListener;

    public DataTransaksiHarianAdapter(Context context, ArrayList<DataTransaksiHarian> dataTransaksiHarian, DataHarianOnClickListener dataHarianOnClickListener) {
        this._inflater = LayoutInflater.from(context);
        this.dataTransaksiHarian = dataTransaksiHarian;
        this.dataHarianOnClickListener = dataHarianOnClickListener;
    }

    @Override
    public DataTransaksiHarianViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = _inflater.inflate(R.layout.row_transaksi_dashboard, viewGroup,false);
        return new DataTransaksiHarianViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(DataTransaksiHarianViewHolder dataTransaksiHarianViewHolder, final int i) {
        final DataTransaksiHarian data = dataTransaksiHarian.get(i);
        dataTransaksiHarianViewHolder._kategori.setText(data._kategori);
        dataTransaksiHarianViewHolder._deskripsi.setText(data._deskripsi);
        dataTransaksiHarianViewHolder._transaksi.setText(String.valueOf("Rp. "+data._transaksiHarian));
        dataTransaksiHarianViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataHarianOnClickListener.onItemClicked(data._id,data._tipe,data._kategori,data._transaksiHarian,data._deskripsi,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTransaksiHarian.size();
    }

    public class DataTransaksiHarianViewHolder extends RecyclerView.ViewHolder {
        TextView _kategori, _deskripsi,_transaksi;
        DataTransaksiHarianAdapter _adapter;

        public DataTransaksiHarianViewHolder(View itemView, DataTransaksiHarianAdapter dataTransaksiHarianAdapter) {
            super(itemView);

            _kategori = itemView.findViewById(R.id.tvKategori);
            _deskripsi = itemView.findViewById(R.id.tvDeskripsi);
            _transaksi = itemView.findViewById(R.id.tvTransaksi);

            this._adapter = dataTransaksiHarianAdapter;
        }
    }
}
