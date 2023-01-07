package com.zee.club.home.ui.activities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.AppInfoResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PublishContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AppInfoResp> dataList;
    public final HashMap<String, AppInfoResp> selectedAppInfoMap = new HashMap<>();

    public PublishContentAdapter(List<AppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_publish_content, parent, false);
            return new ActivitiesViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_publish_content_selected, parent, false);
            return new ActivitiesSelectedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ActivitiesViewHolder){
            ((ActivitiesViewHolder)holder).bind(dataList.get(position));
        }else{
            ((ActivitiesSelectedViewHolder)holder).bind(dataList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        AppInfoResp appInfoResp = dataList.get(position);
        if(selectedAppInfoMap.containsKey(appInfoResp.getSkuId())){
            return 1;
        }
        return 0;
    }

    public void setDataList(List<AppInfoResp> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<AppInfoResp> getSelectedAppInfoList(){
        Iterator<AppInfoResp> iterator = selectedAppInfoMap.values().iterator();
        List<AppInfoResp> selectedAppInfoList = new ArrayList<>();
        while (iterator.hasNext()) {
            selectedAppInfoList.add(iterator.next());
        }
        return selectedAppInfoList;
    }

    class ActivitiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvItemName;

        public ActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_activities_publish_name);
            itemView.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            tvItemName.setText(data.getProductTitle());
            itemView.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) v.getTag();
                selectedAppInfoMap.put(appInfoResp.getSkuId(), appInfoResp);
                notifyDataSetChanged();
            }
        }
    }

    class ActivitiesSelectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvItemName;

        public ActivitiesSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_activities_publish_name);
            itemView.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            tvItemName.setText(data.getProductTitle());
            itemView.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) v.getTag();
                selectedAppInfoMap.remove(appInfoResp.getSkuId());
                notifyDataSetChanged();
            }
        }
    }
}
