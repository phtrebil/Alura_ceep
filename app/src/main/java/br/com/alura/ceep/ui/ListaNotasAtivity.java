package br.com.alura.ceep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.RecycleView.ListaNotasAdapter;

public class ListaNotasAtivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        todos = notasDeExemplo();
        configuraRecyclerView(todos);

        TextView textoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        textoInsereNota.setOnClickListener(view -> {
            Intent abreFormulario = new Intent(ListaNotasAtivity.this, FormularioNotaActivity.class);
            abreActivity.launch(abreFormulario);
        });
    }

    ActivityResultLauncher<Intent>  abreActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (result.getResultCode() == 2 && data.hasExtra("nota")) {
                    Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
                    adapter.adiciona(notaRecebida);
                }
            }
    );

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void configuraRecyclerView(List<Nota> todos) {
        RecyclerView lista = findViewById(R.id.RecyclerView);
        configuraAdapter(todos, lista);
        configuraLayout(lista);
    }

    private void configuraLayout(RecyclerView lista) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        lista.setLayoutManager(staggeredGridLayoutManager);
    }

    private void configuraAdapter(List<Nota> todos, RecyclerView lista) {
        adapter = new ListaNotasAdapter(this, todos);
        lista.setAdapter(adapter);
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota", "Olá mundo!!!"));
        dao.insere(new Nota("Primeira nota", "Olá mundo555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555!!!"));
        dao.insere(new Nota("Primeira nota", "Olá mundo2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222!!!"));
        dao.insere(new Nota("Primeira nota", "Olá mundooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!"));
        return dao.todos();
    }
}