package br.com.alura.ceep.ui.adapter.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHoder> {

    private List<Nota> notas;
    private Context context;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public NotaViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHoder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHoder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);


    }

    @Override
    public int getItemCount() {
        return notas.size();
    }
    
    class NotaViewHoder extends RecyclerView.ViewHolder{

        private final TextView titulo;
        private final TextView descricao;

        public NotaViewHoder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
        }

        private void vincula(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }
}
