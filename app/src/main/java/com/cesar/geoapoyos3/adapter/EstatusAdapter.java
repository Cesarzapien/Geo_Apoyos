package com.cesar.geoapoyos3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesar.geoapoyos3.R;
import com.cesar.geoapoyos3.model.Estatus;

import java.util.List;

public class EstatusAdapter extends BaseAdapter {
    private Context context;
    private List<Estatus> estatusList;

    public EstatusAdapter(Context context, List<Estatus> estatusList) {
        this.context = context;
        this.estatusList = estatusList;
    }

    @Override
    public int getCount() {
        return estatusList != null ? estatusList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_status,viewGroup,false);

        TextView txtName = rootView.findViewById(R.id.name);

        txtName.setText(estatusList.get(i).getEstatus());

        return rootView;
    }
}
