package anish.tutorial.toc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anish.tutorial.toc.R;
import anish.tutorial.toc.model.Productions;

public class EquationAdapter extends RecyclerView.Adapter<EquationAdapter.EquationHolder>  {
    Context context;
    List<Productions> dmList;

    public EquationAdapter(Context context, List<Productions> dmList) {
        this.context = context;
        this.dmList = dmList;
    }

    @NonNull
    @Override
    public EquationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EquationHolder(LayoutInflater.from(context).inflate(R.layout.production_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EquationHolder holder, int position) {
        holder.lhhss.setText(dmList.get(holder.getAdapterPosition()).getLhsv());
        String pro = " = " + dmList.get(holder.getAdapterPosition()).getRhsv();
        holder.rhhss.setText(pro);
    }

    @Override
    public int getItemCount() {
        return dmList.size();
    }

    public class EquationHolder extends RecyclerView.ViewHolder{
        TextView rhhss,lhhss;
        public EquationHolder(@NonNull View itemView) {
            super(itemView);
            lhhss = itemView.findViewById(R.id.viewSymbol);
           rhhss = itemView.findViewById(R.id.viewVariable);
        }
    }
}
