package com.example.watchbeforeyougo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.watchbeforeyougo.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/*
 *Created by utkuglsvn
 */
public class FragmentStaticts extends Fragment {

    PieChart pieChart;
    private String[] markets={"Migros","Swiss Design Market","Alnatura Bio Super Markt","Migros Supermarkt","Wochenmarkt Bürkliplatz","Migros Supermarkt","bim","Rosenhof Market"};

    public static FragmentStaticts newInstance() {
        FragmentStaticts fragment = new FragmentStaticts();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staticts,container,false);
        pieChart = view.findViewById(R.id.idPieChart);
        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

               Toast.makeText(getContext(),"Person Total Number: "+h.getY()+"\n"+markets[(int) h.getX()]+"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return view;
    }
    private int[] yData = {3,5,7,9,5,10,15,9};

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i],markets[i]));
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Markets");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText("Watch Before You Go");
        pieChart.setCenterTextColor(Color.RED);
        pieChart.invalidate();
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(8);

    }
}

