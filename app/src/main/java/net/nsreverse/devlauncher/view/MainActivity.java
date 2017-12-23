package net.nsreverse.devlauncher.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.nsreverse.devlauncher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.image_view_launch_app_list) ImageView launchAppListImageView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        ButterKnife.bind(this);

        launchAppListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppListActivity.class);
                startActivity(intent);
            }
        });


        int color = Color.argb(255, 255, 255, 255);
        launchAppListImageView.setColorFilter(color);
    }
}
