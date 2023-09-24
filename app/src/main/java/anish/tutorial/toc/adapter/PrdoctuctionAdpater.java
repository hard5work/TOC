package anish.tutorial.toc.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anish.tutorial.toc.R;
import anish.tutorial.toc.model.Productions;

public class PrdoctuctionAdpater extends  RecyclerView.Adapter<PrdoctuctionAdpater.ProductionViewer>{
    Context c;
    List<Productions> plist;

    public PrdoctuctionAdpater(Context c, List<Productions> plist) {
        this.c = c;
        this.plist = plist;
    }

    @NonNull
    @Override
    public ProductionViewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductionViewer(LayoutInflater.from(c).inflate(R.layout.production_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductionViewer holder, int position) {
        holder.sym.setText(plist.get(holder.getAdapterPosition()).getSymbol());
        String pro = " -> " + plist.get(holder.getAdapterPosition()).getVariable();
        holder.var.setText(pro);

    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    public class ProductionViewer extends RecyclerView.ViewHolder{
        TextView sym,var;
        public ProductionViewer(@NonNull View itemView) {
            super(itemView);

            sym = itemView.findViewById(R.id.viewSymbol);
            var = itemView.findViewById(R.id.viewVariable);
        }
    }
}
