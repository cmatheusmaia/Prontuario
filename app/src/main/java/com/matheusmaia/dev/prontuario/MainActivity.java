package com.matheusmaia.dev.prontuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.matheusmaia.dev.prontuario.dao.PacienteBean;
import com.matheusmaia.dev.prontuario.dao.PacienteBanco;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPacientes;
    private PacienteBean pacienteSelecionado;
    private List<PacienteBean> registrosPaciente;
    private ArrayAdapter<PacienteBean> adaptadorLista;
    private int adptadorLayout = android.R.layout.simple_list_item_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPacientes = (ListView) findViewById(R.id.listview_pacientes);

        PacienteBanco dao = new PacienteBanco(MainActivity.this);
        registrosPaciente = dao.consultarRegistros();

        if (registrosPaciente.isEmpty()) {

            PacienteBean paciente1 = new PacienteBean("Matheus Maia", "Pe Pedro, Fortaleza CE", "8532746722", "85987687443", "contato@matheusmaia.com", "999252972");
            PacienteBean paciente2 = new PacienteBean("Vera Lúcia", "1 Pe Pedro, Fortaleza CE", "8532746722", "85987687443", "contato@matheusmaia.com", "999252972");
            PacienteBean paciente3 = new PacienteBean("Carlos Augusto", "2 Pe Pedro, Fortaleza CE", "8532746722", "85987687443", "contato@matheusmaia.com", "999252972");
            PacienteBean paciente4 = new PacienteBean("Lucas Maia", "3 Pe Pedro, Fortaleza CE", "8532746722", "85987687443", "contato@matheusmaia.com", "999252972");
            PacienteBean paciente5 = new PacienteBean("Egidia Monteiro", "4 Pe Pedro, Fortaleza CE", "8532746722", "85987687443", "contato@matheusmaia.com", "999252972");

            dao.inserirPaciente(paciente1);
            dao.inserirPaciente(paciente2);
            dao.inserirPaciente(paciente3);
            dao.inserirPaciente(paciente4);
            dao.inserirPaciente(paciente5);

            registrosPaciente = dao.consultarRegistros();
        }


        adaptadorLista = new ArrayAdapter<PacienteBean>(this, adptadorLayout, registrosPaciente);

        listViewPacientes.setAdapter(adaptadorLista);
        registerForContextMenu(listViewPacientes);

        listViewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "Paciente: " + registrosPaciente.get(position).getNome(),
                        Toast.LENGTH_LONG).show();
            }
        });

        listViewPacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pacienteSelecionado = (PacienteBean) adaptadorLista.getItem(position);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_pacientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto, menu);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final PacienteBean paciente = (PacienteBean) lv.getItemAtPosition(acmi.position);
        String nome = paciente.getNome();
        menu.setHeaderTitle(nome);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.itemRemover:
                removerPaciente();
                break;

            case R.id.itemAlterar:
                altDados(intent);
                break;

            case R.id.itemLigar:
                ligarParente(intent);
                break;

            case R.id.itemSMS:
                enviarSMS(intent);
                break;

            case R.id.itemEmail:
                enviarEmail(intent);
                break;

            case R.id.itemDadosADD:
                informarDados(intent);
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void removerPaciente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Atendimento do: " + pacienteSelecionado.getNome()
                + " ?");
        builder.setIcon(R.drawable.logo_final);
        builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                PacienteBanco pacienteDAO = new PacienteBanco(MainActivity.this);
                pacienteDAO.removerRegistro(pacienteSelecionado);

                registrosPaciente.remove(pacienteSelecionado);
                registrosPaciente = pacienteDAO.consultarRegistros();
                pacienteSelecionado = null;
                adaptadorLista.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmar operação");
        dialog.show();
    }

    public void enviarEmail(Intent intent) {
        if (pacienteSelecionado.getEmail() == null) {
            msgErro();
        } else {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{pacienteSelecionado.getEmail()});
            intent.putExtra(Intent.EXTRA_SUBJECT, pacienteSelecionado.getNome() + " - Exames");
            intent.putExtra(Intent.EXTRA_TEXT, " ");
            startActivity(intent);
        }
    }

    public void enviarSMS(Intent intent) {
        if (pacienteSelecionado.getParenteCelular() == null) {
            msgErro();
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("Torpedo: " + pacienteSelecionado.getParenteCelular()));
            intent.putExtra("sms_body", "Olá, " + pacienteSelecionado.getNome() + ", precisa de sua presança no Hospital.");
            startActivity(intent);
        }
    }

    public void ligarParente(Intent intent) {
        if (pacienteSelecionado.getParenteCelular() == null) {
            msgErro();
        } else {
            intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel: " + pacienteSelecionado.getParenteCelular()));
            startActivity(intent);
        }
    }

    private void informarDados(Intent intent) {
        intent = new Intent(MainActivity.this, AdicionarDados.class);
        intent.putExtra("pacienteSelecionado", (Parcelable) pacienteSelecionado);
        startActivity(intent);
    }

    private void altDados(Intent intent) {
        intent = new Intent(MainActivity.this, AlterarDados.class);
        intent.putExtra("pacienteSelecionado", (Parcelable) pacienteSelecionado);
        startActivity(intent);
    }

    public void msgErro() {
        Context contexto = getApplicationContext();
        String texto = "Ainda não cadastrado";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto, duracao);
        toast.show();
    }

}
