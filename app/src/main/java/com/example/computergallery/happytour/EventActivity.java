package com.example.computergallery.happytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {
    TextView title, WhatWeWillProvide,WhatWeWillDo,note,whereWeWillBe,contact,aboutHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        title = (TextView) findViewById(R.id.event_name);
        WhatWeWillProvide = (TextView) findViewById(R.id.event_whatwewillprovide);
        WhatWeWillDo = (TextView) findViewById(R.id.event_whatwewilldo);
        note = (TextView) findViewById(R.id.note);
        whereWeWillBe = (TextView) findViewById(R.id.contact);
        contact = (TextView) findViewById(R.id.contact);
        aboutHost = (TextView) findViewById(R.id.event_aboutyourhost);

        String name = getIntent().getStringExtra("name");
        String provide = getIntent().getStringExtra("whatprovide");
        String WillDo = getIntent().getStringExtra("wewilldo");
        String noted = getIntent().getStringExtra("note");
        String WillBe = getIntent().getStringExtra("wherebe");
        String contact = getIntent().getStringExtra("contact");
        String aboutHost = getIntent().getStringExtra("about");
        String image1 = getIntent().getStringExtra("image1");
        String image2 = getIntent().getStringExtra("image2");
        String image3 = getIntent().getStringExtra("image3");

    }
}
