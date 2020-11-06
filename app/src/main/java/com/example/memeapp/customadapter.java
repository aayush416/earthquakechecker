package com.example.memeapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class customadapter extends ArrayAdapter {
    public customadapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View get=convertView;
       if(get==null)
       {
          get= LayoutInflater.from(getContext()).inflate(R.layout.custom,parent,false);
       }
      customize x= (customize) getItem(position);
        TextView y=get.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(Double.parseDouble(x.getMag()));
        y.setText(formattedMagnitude);
        TextView z=get.findViewById(R.id.location_offset);
        z.setText(x.getArea().substring(0,x.getArea().indexOf("f")+1));
        TextView z2=get.findViewById(R.id.primary_location);
        z2.setText(x.getArea().substring(x.getArea().indexOf("f")+1));
        TextView q=get.findViewById(R.id.time);
        GradientDrawable magnitudeCircle = (GradientDrawable)y.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(x.getMag()));
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        q.setText(x.formatTime(new Date(x.getTime())));
        TextView w=get.findViewById(R.id.date);
        w.setText(x.formatDate(new Date(x.getTime())));
        return get;
    }
    public int getMagnitudeColor(Double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
