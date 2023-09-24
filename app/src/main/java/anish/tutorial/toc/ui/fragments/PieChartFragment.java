package anish.tutorial.toc.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import anish.tutorial.toc.R;

public class PieChartFragment extends Fragment {
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vs = inflater.inflate(R.layout.fragment_piechart,container,false);
        pieChart = vs.findViewById(R.id.pieChart1);
        getEntries();
        pieDataSet = new PieDataSet(pieEntries,"");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
      //  pieDataSet.setSliceSpace(0f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(15f);
      //  pieDataSet.setSliceSpace(1f);
        Description description = new Description();
        description.setText("Checking Desc");
        pieChart.setDescription(description);
        pieChart.setHoleRadius(0f);




        return vs;
    }
    private void getEntries() {
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(7.1f, "D"));
        pieEntries.add(new PieEntry(14.3f, "A"));
        pieEntries.add(new PieEntry(35.7f, "C"));
        pieEntries.add(new PieEntry(42.9f, "B"));
     //   pieEntries.add(new PieEntry(17f, 0));
     //   pieEntries.add(new PieEntry(31f, 0));
    }
}
