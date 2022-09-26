package br.com.alura.ceep.ui;

import static br.com.alura.ceep.ui.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.RecycleView.ListaNotasAdapter;
import br.com.alura.ceep.ui.adapter.RecycleView.Listener.OnItemClickListener;

public class ListaNotasAtivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todos = getNotas();
        configuraRecyclerView(todos);
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView textoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        vaiParaFormularioNotaActivity(textoInsereNota);
    }

    private void vaiParaFormularioNotaActivity(TextView textoInsereNota) {
        textoInsereNota.setOnClickListener(view -> {
            Intent abreFormulario = new Intent(ListaNotasAtivity.this, FormularioNotaActivity.class);
            abreActivity.launch(abreFormulario);
        });
    }

    private List<Nota> getNotas() {
        NotaDAO dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(
                    new Nota("Título " + (i + 1),
                            "Descrição " + (i + 1)));
        }
        return dao.todos();
    }

    ActivityResultLauncher<Intent> abreActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (ehUmResultadoComNota(result, data)) {
                    adicionaNota(data);
                }
            }
    );

    private void adicionaNota(Intent data) {
        Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
        adapter.adiciona(notaRecebida);
    }

    private boolean ehUmResultadoComNota(ActivityResult result, Intent data) {
        return verificaCodigoDeResultadoInsereNota(result) && verificaSeTemNota(data);
    }

    private boolean verificaSeTemNota(Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean verificaCodigoDeResultadoInsereNota(ActivityResult result) {
        return result.getResultCode() == CODIGO_RESULTADO_NOTA_CRIADA;
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
        adapter.setOnItemClickListener(nota ->
                Toast.makeText(ListaNotasAtivity.this,
                nota.getTitulo(),
                Toast.LENGTH_SHORT).show());
    }


}