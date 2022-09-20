package br.com.alura.ceep.ui;

import android.os.Bundle;
import android.widget.ListView;

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
        RecyclerView lista = findViewById(R.id.RecyclerView);
        NotaDAO dao = new NotaDAO();
        for (int i = 1; i < 10000; i++) {
            dao.insere(new Nota("Primeira nota" + i, "Olá mundo!!!"));
        }

        List<Nota> todos = dao.todos();
        lista.setAdapter(new ListaNotasAdapter(this, todos));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(linearLayoutManager);
    }
}