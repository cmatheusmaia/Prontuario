package com.matheusmaia.dev.prontuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.matheusmaia.dev.prontuario.dao.PacienteBanco;

import com.matheusmaia.dev.prontuario.dao.PacienteBean;

public class AlterarDados extends AppCompatActivity {

    private PacienteBean pacienteSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);

        pacienteSelecionado = getIntent().getExtras().getParcelable("pacienteSelecionado");

        TextView txtNome = (TextView) findViewById(R.id.textNome);
        EditText txtEndereco = (EditText) findViewById(R.id.editTextEndereco);
        EditText txtCelular = (EditText) findViewById(R.id.editTextCelular);
        EditText txtEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText txtTelefone = (EditText) findViewById(R.id.editTextTelefone);
        EditText txtParenteCelular = (EditText) findViewById(R.id.editTextParenteCelular);
        txtNome.setText(pacienteSelecionado.getNome());
        txtEndereco.setText(pacienteSelecionado.getEndereco());
        txtCelular.setText(pacienteSelecionado.getCelular());
        txtTelefone.setText(pacienteSelecionado.getTelefone());
        txtEmail.setText(pacienteSelecionado.getEmail());
        txtParenteCelular.setText(pacienteSelecionado.getParenteCelular());
    }

    public void CliqueConfirmarDados(View view){
        TextView txtNome = (TextView) findViewById(R.id.textNome);
        EditText txtEndereco = (EditText) findViewById(R.id.editTextEndereco);
        EditText txtCelular = (EditText) findViewById(R.id.editTextCelular);
        EditText txtEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText txtTelefone = (EditText) findViewById(R.id.editTextTelefone);
        EditText txtParenteCelular = (EditText) findViewById(R.id.editTextParenteCelular);
        pacienteSelecionado.setNome(txtNome.getText().toString());
        pacienteSelecionado.setEndereco(txtEndereco.getText().toString());
        pacienteSelecionado.setTelefone(txtTelefone.getText().toString());
        pacienteSelecionado.setCelular(txtCelular.getText().toString());
        pacienteSelecionado.setEmail(txtEmail.getText().toString());
        pacienteSelecionado.setParenteCelular(txtParenteCelular.getText().toString());

        PacienteBanco dao = new PacienteBanco(AlterarDados.this);
        dao.editarRegistro(pacienteSelecionado);
        dao.close();

        Intent intent = new Intent(AlterarDados.this, MainActivity.class);
        startActivity(intent);
    }

}
