package com.vivek.bcanotes.DashboardActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.vivek.bcanotes.R;
import com.vivek.bcanotes.Semester.Semester1st;
import com.vivek.bcanotes.Semester.Semester2nd;
import com.vivek.bcanotes.Semester.Semester_3rd;
import com.vivek.bcanotes.Semester.Semester_4th;
import com.vivek.bcanotes.Semester.Semester_5th;
import com.vivek.bcanotes.Semester.Semester_6th;

import java.util.List;

public class Adapter extends PagerAdapter {

    private final List<com.vivek.bcanotes.DashboardActivity.Model> models;
    private final Context context;

    public Adapter(List<com.vivek.bcanotes.DashboardActivity.Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

       ImageView imageView;
       TextView title, desc;

       imageView = view.findViewById(R.id.image);
       title = view.findViewById(R.id.title);
         desc = view.findViewById(R.id.desc);

       imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
       desc.setText(models.get(position).getDesc());

        view.setOnClickListener(v -> {
                    if (position == 0) {
                        Intent intent = new Intent(context, Semester1st.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                    } else if (position == 1) {
                        Intent intent = new Intent(context, Semester2nd.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                    } else if (position == 2) {
                        Intent intent = new Intent(context, Semester_3rd.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                    } else if (position == 3) {
                        Intent intent = new Intent(context, Semester_4th.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                    } else if (position == 4) {
                        Intent intent = new Intent(context, Semester_5th.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                        // finish();
                    } else if (position == 5) {
                        Intent intent = new Intent(context, Semester_6th.class);
                        //intent.putExtra("param", models.get(position).getTitle());
                        context.startActivity(intent);
                        // finish();

                    }
                });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
