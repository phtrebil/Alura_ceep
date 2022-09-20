package br.com.alura.ceep.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.RecycleView.ListaNotasAdapter;

public class ListaNotasAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todos = notasDeExemplo();
        configuraRecyclerView(todos);
    }

    private void configuraRecyclerView(List<Nota> todos) {
        RecyclerView lista = findViewById(R.id.RecyclerView);
        configuraAdapter(todos, lista);
        configuraLayout(lista);
    }

    private void configuraLayout(RecyclerView lista) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(linearLayoutManager);
    }

    private void configuraAdapter(List<Nota> todos, RecyclerView lista) {
        lista.setAdapter(new ListaNotasAdapter(this, todos));
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota", "Olá mundo!!!"));
        return dao.todos();
    }
}