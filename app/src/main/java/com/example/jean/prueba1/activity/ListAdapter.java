package com.example.jean.prueba1.activity;

/**
 * Created by jona on 18-02-18.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.jean.prueba1.R;

import java.util.ArrayList;

/**
 * Created by Juned on 2/1/2017.
 */

public class ListAdapter extends ArrayAdapter<Subject> {
    String estado;
    public ArrayList<Subject> MainList;

    public ArrayList<Subject> SubjectListTemp;

    public ListAdapter.SubjectDataFilter subjectDataFilter ;

    public ListAdapter(Context context, int id, ArrayList<Subject> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<Subject>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<Subject>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null){

            subjectDataFilter  = new ListAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }


    public class ViewHolder {

        TextView SubjectName;
        TextView SubjectFullForm;
        TextView SubjectPrioridad;
        TextView SubjectEstado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.custom_layout, null);

            holder = new ListAdapter.ViewHolder();

            holder.SubjectName = (TextView) convertView.findViewById(R.id.textviewName);

            holder.SubjectFullForm = (TextView) convertView.findViewById(R.id.textviewFullForm);

            holder.SubjectPrioridad = (TextView) convertView.findViewById(R.id.testViewPrioridad);

            holder.SubjectEstado = (TextView) convertView.findViewById(R.id.testViewEstado);
            convertView.setTag(holder);

        } else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }

        Subject subject = SubjectListTemp.get(position);

        holder.SubjectName.setText(subject.getSubName());

        holder.SubjectFullForm.setText(subject.getSubFullForm());

        holder.SubjectPrioridad.setText(subject.getSubFullPrioridad());

        holder.SubjectEstado.setText(subject.getSubFullEstado());


        estado=subject.getSubFullEstado();

        if (estado.equals("P")) {
            holder.SubjectEstado.setText("Pendiente");
            holder.SubjectEstado.setTextColor(Color.YELLOW);

        } else if (estado.equals("NOK")) {
            holder.SubjectEstado.setText("No Entregado");
            holder.SubjectEstado.setTextColor(Color.RED);
        } else if (estado.equals("PEND")) {
            holder.SubjectEstado.setText("En Proceso");
            holder.SubjectEstado.setTextColor(Color.GREEN);
        }

        return convertView;

    }

    private class SubjectDataFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<Subject> arrayList1 = new ArrayList<Subject>();

                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    Subject subject = MainList.get(i);

                    if(subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            }
            else
            {
                synchronized(this)
                {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<Subject>)filterResults.values;

            notifyDataSetChanged();

            clear();

            for(int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }


}