package ru.profitsw2000.data;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;

import ru.profitsw2000.notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {


    private CardSource cardSource   ;
    private OnItemClickListener itemClickListener   ;
    private final Fragment fragment ;
    private int menuPosition    ;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(CardSource cardSource) {
        this.cardSource = cardSource    ;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(cardSource.getMyNotes(position));
    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position) throws ParseException;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title   ;
        private AppCompatImageView image    ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText)   ;
            image = itemView.findViewById(R.id.imageView)   ;

            registerContextMenu(itemView)   ;

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        try {
                            itemClickListener.onItemClick(v, getAdapterPosition());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // Обработчик нажатий на картинке
            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();
                    menuPosition = getLayoutPosition()  ;
                    return true;
                }
            });
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(MyNotes myNotes){
            title.setText(myNotes.getTitle());
            image.setImageResource(myNotes.getPicture());
        }
    }
}