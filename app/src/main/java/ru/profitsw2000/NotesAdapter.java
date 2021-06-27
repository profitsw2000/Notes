package ru.profitsw2000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import ru.profitsw2000.notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private CardSource cardSource   ;
    private OnItemClickListener itemClickListener   ;

    public NotesAdapter(CardSource cardSource) {
        this.cardSource = cardSource;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.setData(cardSource.getMyNotes(position));
    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position)   ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title   ;
        private AppCompatImageView image    ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText)   ;
            image = itemView.findViewById(R.id.imageView)   ;

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(MyNotes myNotes){
            title.setText(myNotes.getTitle());
            image.setImageResource(myNotes.getPicture());
        }
    }

}
