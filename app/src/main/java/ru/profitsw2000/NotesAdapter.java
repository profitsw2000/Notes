package ru.profitsw2000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.profitsw2000.notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private String[] notesList   ;
    private OnItemClickListener itemClickListener   ;

    public NotesAdapter(String[] notesList) {
        this.notesList = notesList;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(notesList[position]);
    }

    @Override
    public int getItemCount() {
        return notesList.length;
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position)   ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView   ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView  ;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public TextView getTextView() {
            return textView ;
        }
    }

}
