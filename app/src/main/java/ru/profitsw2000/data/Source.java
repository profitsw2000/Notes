package ru.profitsw2000.data;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.profitsw2000.notes.R;

public class Source implements CardSource {

    private List<MyNotes> dataSource;
    private Resources resources;

    public Source(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public Source init(){
        String[] titles = resources.getStringArray(R.array.notes_title);
        int[] pictures = getImageArray();
        String[] descriptions = resources.getStringArray(R.array.notes_description) ;
        String[] dates = resources.getStringArray(R.array.notes_date) ;
        String[] text = resources.getStringArray(R.array.notes_text)    ;

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new MyNotes(titles[i], pictures[i], descriptions[i], dates[i], text[i]));
        }
        return this;
    }

    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    @Override
    public MyNotes getMyNotes(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    public static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

        private CardSource cardSource   ;
        private OnItemClickListener itemClickListener   ;

        public NotesAdapter(CardSource cardSource) {
            this.cardSource = cardSource;
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
}
