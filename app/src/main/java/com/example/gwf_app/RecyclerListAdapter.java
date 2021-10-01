package com.example.gwf_app;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> implements Filterable {
    private List<Object> recyclerItems = new ArrayList<>();
    private List<Object> backupFullItems = new ArrayList<Object>();
    private Context context;


    public RecyclerListAdapter(List<Object> recyclerItems, Context context) {
            this.recyclerItems = recyclerItems;
            this.context = context;
            backupFullItems = new ArrayList<>(recyclerItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recycler_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RecyclerListInfo recyclerListInfo = (RecyclerListInfo) recyclerItems.get(position);

        holder.meter_id.setText(recyclerListInfo.getMeter_id());
        holder.mp_name.setText(recyclerListInfo.getMp_name());

        if(recyclerListInfo.getMeter_type().equals("afm")) {
            holder.meter_type.setText(recyclerListInfo.getMeter_type());
        }
        else {
            holder.meter_type.setText(Html.fromHtml("<strong>" + recyclerListInfo.getComm_mod_type() + "</strong>" +  "<br />" +
                    "<em>" +"<small>" + "<font color='black'>" + recyclerListInfo.getComm_mod_serial() + "</font>" + "</small>" + "</em>"));
        }




        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create intent and pass meter's info to MeterInfo preview activity
                Intent intent = new Intent(context, MeterInfo.class);

                intent.putExtra("lat",  recyclerListInfo.getLat());
                intent.putExtra("lng",  recyclerListInfo.getLng());
                intent.putExtra("mp_name",  recyclerListInfo.getMp_name());
                intent.putExtra("meter_id",  recyclerListInfo.getMeter_id());
                intent.putExtra("meter_type",  recyclerListInfo.getMeter_type());
                intent.putExtra("comm_mod_type", recyclerListInfo.getComm_mod_type());
                intent.putExtra("comm_mod_serial", recyclerListInfo.getComm_mod_serial());
                intent.putExtra("last_entry", recyclerListInfo.getLast_entry());
                intent.putExtra("volume", recyclerListInfo.getVolume());
                intent.putExtra("battery_lifetime", recyclerListInfo.getBattery_lifetime());

                //pass state
                intent.putExtra("continuous_flow", recyclerListInfo.getContinuous_flow());
                intent.putExtra("broken_pipe", recyclerListInfo.getBroken_pipe());
                intent.putExtra("battery_low", recyclerListInfo.getBattery_low());
                intent.putExtra("backflow", recyclerListInfo.getBackflow());
                intent.putExtra("communication_error", recyclerListInfo.getCommunication_error());
                intent.putExtra("parsing_error", recyclerListInfo.getParsing_error());
                intent.putExtra("encoder_error", recyclerListInfo.getEncoder_error());

                intent.putExtra("us_water_level", recyclerListInfo.getUs_water_level());
                intent.putExtra("v_sensor_comm_timout", recyclerListInfo.getV_sensor_comm_timout());
                intent.putExtra("water_level_error", recyclerListInfo.getWater_level_error());
                intent.putExtra("t_air_error", recyclerListInfo.getT_air_error());
                intent.putExtra("t_water_error", recyclerListInfo.getT_water_error());
                intent.putExtra("w_air_error", recyclerListInfo.getW_air_error());
                intent.putExtra("w_water_error", recyclerListInfo.getW_water_error());
                intent.putExtra("velocity_error", recyclerListInfo.getVelocity_error());
                intent.putExtra("system_error", recyclerListInfo.getSystem_error());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView meter_id, mp_name, meter_type, comm_mod_type, comm_mod_serial;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            this.meter_id = (TextView) itemView.findViewById(R.id.meter_id);
            this.mp_name = (TextView) itemView.findViewById(R.id.mp_name);
            this.meter_type =(TextView) itemView.findViewById(R.id.meter_type);

            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Object> filterList = new ArrayList<Object>();

            if(constraint == null || constraint.length() == 0) {
                filterList.addAll(backupFullItems);
            }
            else {
                String givenFilter = constraint.toString().toLowerCase().trim();
                Log.d("charseq",givenFilter);
                //Filter will be meter_id
                int i=0;
                for(Object item : backupFullItems) {
                    RecyclerListInfo recyclerListInfo = (RecyclerListInfo) backupFullItems.get(i);

                    if(recyclerListInfo.getMeter_id().toLowerCase().contains(givenFilter)) {
                        filterList.add(item);
                    }
                    i++;
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recyclerItems.clear();
            recyclerItems.addAll((List) results.values);

            notifyDataSetChanged();
        }
    };
}

