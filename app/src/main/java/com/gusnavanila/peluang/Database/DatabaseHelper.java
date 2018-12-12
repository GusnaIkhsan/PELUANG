package com.gusnavanila.peluang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "peluang_db";
    private static final int DATABASE_VERSION = 1;

//    kolom tabel transaksi
    public static final String TABEL_TRANSAKSI = "transaksi";
    public static final String ID_TRANSAKSI = "id_transaksi";
    public static final String ID_KATEGORI_T = "id_kategori";
    public static final String NOMINAL = "nominal";
    public static final String DESKRIPSI = "deskripsi";
    public static final String TANGGAL = "tanggal";
//    kolom tabel tipe
    public static final String TABEL_TIPE = "tipe_kategori";
    public static final String ID_KATEGORI = "id_kategori";
    public static final String TIPE = "tipe";
    public static final String KATEGORI = "kategori";

    public static final String CREATE_TRANSAKSI_TABLE = "CREATE TABLE "
            + TABEL_TRANSAKSI + "("
            + ID_TRANSAKSI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_KATEGORI_T + " INT, "
            + NOMINAL + " INT, "
            + DESKRIPSI + " TEXT, "
            + TANGGAL + " TEXT, "
            + "FOREIGN KEY(" + ID_KATEGORI_T + ") REFERENCES "
            + TABEL_TIPE + "("+ ID_KATEGORI +") " + ")";

    public static final String CREATE_TIPE_TABLE = "CREATE TABLE "
            + TABEL_TIPE + "("
            + ID_KATEGORI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TIPE + " TEXT, "
            + KATEGORI + " TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRANSAKSI_TABLE);
        db.execSQL(CREATE_TIPE_TABLE);
        db.execSQL("INSERT INTO tipe_kategori"+
                "(id_kategori,tipe,kategori)"+
                " VALUES "+
                "(0,'Pemasukan','Gaji')");

        db.execSQL("INSERT INTO tipe_kategori"+
                "(id_kategori,tipe,kategori)"+
                " VALUES "+
                "(1,'Pemasukan','Hasil Usaha')");

        db.execSQL("INSERT INTO tipe_kategori"+
                "(id_kategori,tipe,kategori)"+
                " VALUES "+
                "(2,'Pengeluaran','Makanan')");

        db.execSQL("INSERT INTO tipe_kategori"+
                "(id_kategori,tipe,kategori)"+
                " VALUES "+
                "(3,'Pengeluaran','Minuman')");

        db.execSQL("INSERT INTO tipe_kategori"+
                "(id_kategori,tipe,kategori)"+
                " VALUES "+
                "(4,'Pengeluaran','Jajanan')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop = "DROP IF TABLE EXISTS ";
        db.execSQL(drop+ TABEL_TRANSAKSI);
        db.execSQL(drop+ TABEL_TIPE);
        onCreate(db);
    }

    public boolean addData(String tipe, int kategori,int jumlah, String deskripsi, String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result;

        cv.put(ID_KATEGORI_T,kategori);
        cv.put(NOMINAL,jumlah);
        cv.put(DESKRIPSI,deskripsi);
        cv.put(TANGGAL,tanggal);

        result = db.insert(TABEL_TRANSAKSI,null,cv);


        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addDataKategori(String tipe, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result;

        cv.put(TIPE,tipe);
        cv.put(KATEGORI,kategori);

        result = db.insert(TABEL_TIPE,null,cv);


        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllTipe(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM  'tipe_kategori'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIdTipe(String tipe, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABEL_TIPE +" WHERE tipe = '"+tipe+"' AND kategori = '"+kategori+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getKategori(String tipe){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT kategori FROM " + TABEL_TIPE +" WHERE tipe = '"+tipe+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTransaksiHarian(String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT tipe,kategori,deskripsi,nominal FROM transaksi JOIN tipe_kategori"
                +" ON transaksi.id_kategori = tipe_kategori.id_kategori"
                +" WHERE tanggal = '"+tanggal+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTotal(String tipe, String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sum(nominal) FROM transaksi JOIN tipe_kategori"
                +" ON transaksi.id_kategori = tipe_kategori.id_kategori"
                +" WHERE tipe_kategori.tipe = '"+tipe+"'"+" AND tanggal = '"+tanggal+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTotalHarian(String tipe){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sum(nominal) FROM transaksi JOIN tipe_kategori"
                +" ON transaksi.id_kategori = tipe_kategori.id_kategori"
                +" WHERE tipe_kategori.tipe = '"+tipe+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTotalKemarin(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT tanggal, sum(nominal) AS jumlah FROM transaksi JOIN tipe_kategori"
                +" ON transaksi.id_kategori = tipe_kategori.id_kategori"
                +" GROUP BY tanggal";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean editKategori(int id, String tipe, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE tipe_kategori SET tipe = '" + tipe +"', kategori = '"+kategori+"' WHERE id_kategori = '"+id+"'";
//        db.execSQL(query);
        ContentValues cv = new ContentValues();
        cv.put(TIPE,tipe);
        cv.put(KATEGORI,kategori);
        String where = "id_kategori = '"+id+"'";
        long result;
        result = db.update(TABEL_TIPE,cv,where,null);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteKategori(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "id_kategori = '"+id+"'";
        long result;
        result = db.delete(TABEL_TIPE,where,null);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

}
