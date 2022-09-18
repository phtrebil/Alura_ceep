package br.com.alura.ceep.ui;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;

public class ListaNotasAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        ListView lista = findViewById(R.id.listView);
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota", "Olá mundo!!!"));

        List<Nota> todos = dao.todos();

        lista.setAdapter(new ListaNotasAdapter(this, todos));
    }
}