package com.example.inovantsolutionstask.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inovantsolutionstask.Modal.MoreItemsModal;
import com.example.inovantsolutionstask.R;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MoreItemsAdapter extends RecyclerView.Adapter<MoreItemsAdapter.PhoneViewHold>  {

    ArrayList<MoreItemsModal> phoneLaocations;
    private boolean isWishListed = true;
    private Context context;

    public MoreItemsAdapter(Context context, ArrayList<MoreItemsModal> phoneLaocations) {
        this.context = context;
        this.phoneLaocations = phoneLaocations;
    }

    @NonNull

    @Override
    public PhoneViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_items_card, parent, false);
        return new PhoneViewHold(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHold holder, int position) {


        MoreItemsModal moreItemsModal = phoneLaocations.get(position);
        Glide.with(context).load(moreItemsModal.getItemImage()).into(holder.image);
        holder.tvBrandName.setText(moreItemsModal.getBrandName());
        holder.tvDressName.setText(moreItemsModal.getDressName());
        holder.tvCurrencyCode.setText(moreItemsModal.getCurrencyCode());
        holder.tvFinalAmt.setText(String.valueOf((Double.parseDouble(moreItemsModal.getFinalAmt()))));
        holder.tvRegularAmt.setText(String.valueOf((Double.parseDouble(moreItemsModal.getRegularAmt()))));
        holder.tvRegularAmt.setPaintFlags(holder.tvRegularAmt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return phoneLaocations.size();

    }

    public interface ListItemClickListener {
        void onMoreItemsListClick(int clickedItemIndex);
    }

    public class PhoneViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image, imgWishList;
        TextView tvBrandName, tvDressName, tvCurrencyCode, tvFinalAmt, tvRegularAmt;
        LinearLayout linearLayout;


        public PhoneViewHold(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.dress_image);
            imgWishList = itemView.findViewById(R.id.imgWishList);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvDressName = itemView.findViewById(R.id.tvDressName);
            tvCurrencyCode = itemView.findViewById(R.id.tvCurrencyCode);
            tvFinalAmt = itemView.findViewById(R.id.tvFinalAmt);
            tvRegularAmt = itemView.findViewById(R.id.tvRegularAmt);
            linearLayout = itemView.findViewById(R.id.background_color);

            imgWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(isWishListed){
                       imgWishList.setImageResource(R.drawable.ic_heartred);
                       isWishListed = false;
                   }else{
                       imgWishList.setImageResource(R.drawable.ic_heart);
                       isWishListed = true;
                   }

                }
            });
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            //mOnClickListener.onphoneListClick(clickedPosition);
        }
    }

}

