package com.matheusmaia.dev.prontuario.dao;
import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by C Matheus Maia on 29/11/2015.
 */
public class PacienteBean implements Parcelable{

    private int id;
    private String nome;
    private String endereco;
    private String celular;
    private String telefone;
    private String email;
    private String parenteCelular;



    public PacienteBean() {

    }

    public PacienteBean(String nome,String endereco, String celular, String telefone, String email, String parenteCelular){
        this.nome = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.telefone = telefone;
        this.email = email;
        this.parenteCelular = parenteCelular;
    }

    public PacienteBean(String nome){
        this.nome = nome;
    }

    public String getParenteCelular() {
        return parenteCelular;
    }

    public void setParenteCelular(String parenteCelular) {
        this.parenteCelular = parenteCelular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }


    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public PacienteBean(Parcel in) {
        readFromParcelable(in);
    }

    private void readFromParcelable(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        endereco = in.readString();
        celular = in.readString();
        telefone = in.readString();
        email = in.readString();
        parenteCelular = in.readString();
    }


    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PacienteBean createFromParcel(Parcel in) {
            return new PacienteBean(in);
        }

        public PacienteBean[] newArray(int size) {
            return new PacienteBean[size];
        }
    };

    public String toString(){
        return "Paciente - "+nome;
    }

    @Override
    public int describeContents() {
        //não vai usar
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(endereco);
        dest.writeString(celular);
        dest.writeString(telefone);
        dest.writeString(email);
        dest.writeString(parenteCelular);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Parcelable.Creator getCREATOR() {
        return CREATOR;
    }

}