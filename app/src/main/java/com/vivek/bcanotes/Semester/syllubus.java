package com.vivek.bcanotes.Semester;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.vivek.bcanotes.R;

public class syllubus extends AppCompatActivity {

    PDFView syll;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_syllubus);


        syll = findViewById(R.id.syllubs);

        syll.fromAsset("syllabus.pdf")
                .enableSwipe(true)


                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
}