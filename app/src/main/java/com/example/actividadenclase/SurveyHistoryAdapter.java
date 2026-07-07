package com.example.actividadenclase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SurveyHistoryAdapter extends RecyclerView.Adapter<SurveyHistoryAdapter.ViewHolder> {

    private List<SurveyResponse> surveyList;

    public static class SurveyResponse {
        String fecha;
        String resumen;

        public SurveyResponse(String fecha, String resumen) {
            this.fecha = fecha;
            this.resumen = resumen;
        }
    }

    public SurveyHistoryAdapter(List<SurveyResponse> surveyList) {
        this.surveyList = surveyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_encuesta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SurveyResponse survey = surveyList.get(position);
        holder.tvFecha.setText("Fecha: " + survey.fecha);
        holder.tvResumen.setText(survey.resumen);
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFecha;
        public TextView tvResumen;

        public ViewHolder(View view) {
            super(view);
            tvFecha = view.findViewById(R.id.tv_fecha);
            tvResumen = view.findViewById(R.id.tv_resumen);
        }
    }
}
