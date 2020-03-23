package com.example.emurgency13.Home;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emurgency13.R;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {

    private List<HomeListModel> HomeListModels;
    private OnHomeListItemClicked onHomeListItemClicked;

    public HomeListAdapter(OnHomeListItemClicked onHomeListItemClicked) {
        this.onHomeListItemClicked = onHomeListItemClicked;
    }


    public void setHomeListModels(List<HomeListModel> HomeListModels) {
        this.HomeListModels = HomeListModels;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.listTitle.setText(HomeListModels.get(position).getName());

        String imageUrl = HomeListModels.get(position).getImage();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.listImage);

        holder.listDesc.setText(HomeListModels.get(position).getDesc());
        holder.listTag.setText(HomeListModels.get(position).getTag());

    }

    @Override
    public int getItemCount() {
        if(HomeListModels == null){
            return 0;
        } else {
            return HomeListModels.size();
        }
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView listImage;
        private TextView listTitle;
        private TextView listDesc;
        private TextView listTag;
        private Button listBtn;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            listImage = itemView.findViewById(R.id.list_image);
            listTitle = itemView.findViewById(R.id.list_title);
            listDesc = itemView.findViewById(R.id.list_desc);
            listTag = itemView.findViewById(R.id.list_tag);
            listBtn = itemView.findViewById(R.id.list_btn);

            listDesc = (TextView) itemView.findViewById(R.id.list_desc);

            listDesc.setMovementMethod(new ScrollingMovementMethod());

            listBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHomeListItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnHomeListItemClicked {
        void onItemClicked(int position);
    }
}
