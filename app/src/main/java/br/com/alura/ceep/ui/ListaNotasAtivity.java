package br.com.alura.ceep.ui;

import static br.com.alura.ceep.ui.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;
import static br.com.alura.ceep.ui.NotaActivityConstantes.POSICAO_INVALIDA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.RecycleView.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemThouchHelperCallback;

public class ListaNotasAtivity extends AppCompatActivity {

    public static final String APPBAR_TITLE = "Notas";
    private ListaNotasAdapter adapter;

    ActivityResultLauncher<Intent> abreActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (ehUmResultadoComNota(result, data)) {
                    adicionaNota(data);
                }

            }
    );

    ActivityResultLauncher<Intent> editaActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                alteraNota(result, data);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(APPBAR_TITLE);
        List<Nota> todos = getNotas();
        configuraRecyclerView(todos);
        configuraBotaoInsereNota();
    }

    private void altera(Nota notaRecebida, int posicaoRecebida) {
        new NotaDAO().alteraNota(posicaoRecebida, notaRecebida);
        adapter.altera(posicaoRecebida, notaRecebida);
    }

    private boolean resultadoAlteraNota(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }


    private void configuraBotaoInsereNota() {
        TextView textoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        vaiParaFormularioNotaActivityInsere(textoInsereNota);
    }

    private void vaiParaFormularioNotaActivityInsere(TextView textoInsereNota) {
        textoInsereNota.setOnClickListener(view -> {
            Intent abreFormulario = new Intent(ListaNotasAtivity.this, FormularioNotaActivity.class);
            abreActivity.launch(abreFormulario);
        });
    }

    private List<Nota> getNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    private void alteraNota(ActivityResult result, Intent data) {
        if (ehUmResultadoComNota(result, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

            if (resultadoAlteraNota(posicaoRecebida)) {
                altera(notaRecebida, posicaoRecebida);
            } else {
                errroAoAlterarNota();
            }
        }
    }

    private void errroAoAlterarNota() {
        Toast.makeText(this,
                "Ocorreu um problema na alteração da nota",
                Toast.LENGTH_SHORT).show();
    }


    private void adicionaNota(Intent data) {
        Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
        NotaDAO dao = new NotaDAO();
        dao.insere(notaRecebida);
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
        configuraItemTouchHelper(lista);
    }

    private void configuraItemTouchHelper(RecyclerView lista) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemThouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(lista);
    }

    private void configuraLayout(RecyclerView lista) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        lista.setLayoutManager(staggeredGridLayoutManager);
    }

    private void configuraAdapter(List<Nota> todos, RecyclerView lista) {
        adapter = new ListaNotasAdapter(this, todos);
        lista.setAdapter(adapter);
        adapter.setOnItemClickListener((nota, posicao) -> {
            Intent abreFormularioComNota = new Intent(ListaNotasAtivity.this,
                    FormularioNotaActivity.class);
            abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
            abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
            editaActivity.launch(abreFormularioComNota);
        });
    }


}