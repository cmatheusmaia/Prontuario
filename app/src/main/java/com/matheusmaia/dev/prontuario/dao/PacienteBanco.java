package com.matheusmaia.dev.prontuario.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by C Matheus Maia on 29/11/2015.
 */
public class PacienteBanco extends SQLiteOpenHelper {

    public static final int VERSAO = 1;
    public static final String TABELA = "paciente";
    public static final String DATABASE = "MEUPRONTUARIO.DB";

    public PacienteBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PacienteBanco(Context context){
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY, "
                + "nome TEXT, "
                + "endereco TEXT, "
                + "celular TEXT, "
                + "telefone TEXT, "
                + "email TEXT, "
                + "parenteCelular TEXT) ";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS "+ TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    public void inserirPaciente(PacienteBean paciente) {
        ContentValues valores = new ContentValues();

        valores.put("nome", paciente.getNome());
        valores.put("endereco", paciente.getEndereco());
        valores.put("celular", paciente.getCelular());
        valores.put("telefone", paciente.getTelefone());
        valores.put("email", paciente.getEmail());
        valores.put("parenteCelular", paciente.getParenteCelular());

        getWritableDatabase().insert(TABELA, null, valores);
    }

    public void removerRegistro(PacienteBean paciente){
        String [] args = {Integer.toString(paciente.getId())};

        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public void editarRegistro(PacienteBean paciente){
        ContentValues valores = new ContentValues();

        valores.put("nome", paciente.getNome());
        valores.put("endereco", paciente.getEndereco());
        valores.put("celular", paciente.getCelular());
        valores.put("telefone", paciente.getTelefone());
        valores.put("email", paciente.getEmail());
        valores.put("parenteCelular",paciente.getParenteCelular());

        String[] args = new String[]{Integer.toString(paciente.getId())};

        getWritableDatabase().update(TABELA, valores, "id=?", args);

    }

    public ArrayList<PacienteBean> consultarRegistros(){

        ArrayList<PacienteBean> pacienteList = new ArrayList<PacienteBean>();
        String sql = "Select * from paciente order by nome" ;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);


        try{
            while(cursor.moveToNext()){
                PacienteBean paciente = new PacienteBean();

                paciente.setId(cursor.getInt(0));
                paciente.setNome(cursor.getString(1));
                paciente.setEndereco(cursor.getString(2));
                paciente.setCelular(cursor.getString(3));
                paciente.setTelefone(cursor.getString(4));
                paciente.setEmail(cursor.getString(5));
                paciente.setParenteCelular(cursor.getString(6));

                pacienteList.add(paciente);
            }
        }catch(SQLException sqle){

        }finally{
            cursor.close();
        }

        return pacienteList;
    }

}
