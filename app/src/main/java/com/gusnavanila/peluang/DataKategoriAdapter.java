package com.gusnavanila.peluang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gusnavanila.peluang.CustomClickListener.MyOnClickListener;

import java.util.ArrayList;

public class DataKategoriAdapter extends RecyclerView.Adapter<DataKategoriAdapter.DataKategoriViewHolder> {
    LayoutInflater _inflater;
    ArrayList<DataKategori> dataKategori;
    Context context;
    MyOnClickListener _myonclick;

    public DataKategoriAdapter(Context context, ArrayList<DataKategori> dataKategori, MyOnClickListener myOnClickListener) {
        this._inflater = LayoutInflater.from(context);
        this.dataKategori = dataKategori;
        this.context = context;
        this._myonclick = myOnClickListener;
    }

    @Override
    public DataKategoriViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = _inflater.inflate(R.layout.row_kategori, viewGroup,false);
        return new DataKategoriViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(DataKategoriViewHolder dataKategoriViewHolder, final int i) {
        final DataKategori data = dataKategori.get(i);
        dataKategoriViewHolder._tipe.setText(data.tipe);
        dataKategoriViewHolder._kategori.setText(data.kategori);
        dataKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,String.valueOf(i), Toast.LENGTH_SHORT).show();
                _myonclick.onItemClicked(data.id_kategori, data.tipe, data.kategori);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataKategori.size();
    }

    public class DataKategoriViewHolder extends RecyclerView.ViewHolder {
        TextView _tipe, _kategori;
        DataKategoriAdapter _adapter;

        public DataKategoriViewHolder(View itemView, DataKategoriAdapter dataKategoriAdapter) {
            super(itemView);

            _tipe = itemView.findViewById(R.id.tvTipe);
            _kategori = itemView.findViewById(R.id.tvKategori);

            this._adapter = dataKategoriAdapter;
        }
    }
}
