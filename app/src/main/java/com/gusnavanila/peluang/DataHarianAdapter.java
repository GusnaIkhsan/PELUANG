package com.gusnavanila.peluang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataHarianAdapter extends RecyclerView.Adapter<DataHarianAdapter.DataHarianViewHolder> {

    LayoutInflater _inflater;
    ArrayList<DataHarian> dataHarian;

    public DataHarianAdapter(Context context, ArrayList<DataHarian> dataHarian) {
        this._inflater = LayoutInflater.from(context);
        this.dataHarian = dataHarian;
    }

    @NonNull
    @Override
    public DataHarianViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = _inflater.inflate(R.layout.row_dasboard, viewGroup, false);
        return new DataHarianViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(DataHarianViewHolder dataHarianViewHolder, int i) {
        DataHarian data = dataHarian.get(i);
        dataHarianViewHolder._tanggal.setText(data.tanggal);
        dataHarianViewHolder._pemasukan.setText("Rp. "+String.valueOf(data.pemasukan));
        dataHarianViewHolder._pengeluaran.setText("Rp. "+String.valueOf(data.pengeluaran));
    }

    @Override
    public int getItemCount() {
        return dataHarian.size();
    }

    class DataHarianViewHolder extends RecyclerView.ViewHolder {
        TextView _tanggal, _pemasukan, _pengeluaran;
        DataHarianAdapter _adapter;

        public DataHarianViewHolder(View itemView, DataHarianAdapter dataHarianAdapter) {
            super(itemView);

            _tanggal = itemView.findViewById(R.id.tanggal);
            _pemasukan = itemView.findViewById(R.id.tvPemasukan);
            _pengeluaran = itemView.findViewById(R.id.tvPengeluaran);

            this._adapter = dataHarianAdapter;
        }
    }
}
