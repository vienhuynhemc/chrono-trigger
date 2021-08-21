package com.vientamthuong.chronotrigger.loadGame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.chronotrigger.R;

import java.util.ArrayList;

public class LoadGameAdapter extends RecyclerView.Adapter<LoadGameAdapter.ViewHolder> {
    private ArrayList<LoadGameInfo> listLoadGameInfo;
    private Context context;
    private OnItemClickListener mListener;

    public LoadGameAdapter(ArrayList<LoadGameInfo> listLoadGameInfo, Context context) {
        this.listLoadGameInfo = listLoadGameInfo;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_load_game, parent, false);
        return new ViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadGameAdapter.ViewHolder holder, int position) {
        LoadGameInfo loadGameInfo = listLoadGameInfo.get(position);
        holder.tvLocation.setText(loadGameInfo.getLocation());
        holder.tvTimeSave.setText(loadGameInfo.getTimeSave());
        holder.tvGold.setText("" + loadGameInfo.getGold() + "G");
        if (!loadGameInfo.isAutoSave()) holder.tvAutoSave.setVisibility(View.INVISIBLE);
        holder.tvLevel.setText(loadGameInfo.getLevel());
        holder.tvTimePlay.setText(loadGameInfo.getTimePlay());
    }

    @Override
    public int getItemCount() {
        return listLoadGameInfo.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        TextView tvTimeSave;
        TextView tvGold;
        TextView tvAutoSave;
        TextView tvLevel;
        TextView tvTimePlay;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.textViewLocationLayoutLoadGame);
            tvTimeSave = itemView.findViewById(R.id.textViewTimeSaveLayoutLoadGame);
            tvGold = itemView.findViewById(R.id.textViewGoldLayoutLoadGame);
            tvAutoSave = itemView.findViewById(R.id.textViewAutoSaveLayoutLoadGame);
            tvLevel = itemView.findViewById(R.id.textViewLevelLayoutLoadGame);
            tvTimePlay = itemView.findViewById(R.id.textViewTimeToPlayLayoutLoadGame);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }
    }
}
