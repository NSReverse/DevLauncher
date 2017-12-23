package net.nsreverse.devlauncher.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.nsreverse.devlauncher.R;
import net.nsreverse.devlauncher.view.fragments.DeveloperApplicationsFragment;
import net.nsreverse.devlauncher.view.fragments.UserApplicationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AppListActivity -
 *
 * This is the Activity that displays a list of apps installed on the device.
 *
 * @author Robert
 * Created on 12/22/2017
 */
public class AppListActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final int KEY_FRAGMENT_USER = 1001;
    private static final int KEY_FRAGMENT_DEV = 1002;

    @BindView(R.id.image_view_user) ImageView userImageView;
    @BindView(R.id.image_view_dev) ImageView devImageView;
    @BindView(R.id.text_view_user_label) TextView userLabelTextView;
    @BindView(R.id.text_view_developer_label) TextView developerLabelTextView;
    @BindView(R.id.relative_layout_user) RelativeLayout userRelativeLayout;
    @BindView(R.id.relative_layout_developer) RelativeLayout developerRelativeLayout;

    private boolean hasFragment = false;
    private boolean firstFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        ButterKnife.bind(this);

        userRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateFragment(true, true);
            }
        });

        developerRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateFragment(false, true);
            }
        });

        if (savedInstanceState != null) {
            hasFragment = true;

            int currentFragment = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT);

            if (currentFragment == KEY_FRAGMENT_USER) {
                activateFragment(true, true);
            }
            else {
                activateFragment(false, true);
            }
        }
        else {
            if (!hasFragment) {
                activateFragment(true, false);
            }
        }
    }

    private void activateFragment(boolean userTab, boolean replace) {
        if (userTab) {
            if (replace) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, new UserApplicationsFragment())
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_frame, new UserApplicationsFragment())
                        .commit();
            }

            firstFragment = true;
        }
        else {
            if (replace) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, new DeveloperApplicationsFragment())
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_frame, new DeveloperApplicationsFragment())
                        .commit();
            }

            firstFragment = false;
        }

        int activeColor = Color.argb(255, 0, 142, 218);
        int inactiveColor = Color.argb(255, 170, 170, 170);

        userImageView.setColorFilter(userTab? activeColor : inactiveColor);
        userLabelTextView.setTextColor(userTab? activeColor : inactiveColor);
        devImageView.setColorFilter(userTab? inactiveColor : activeColor);
        developerLabelTextView.setTextColor(userTab? inactiveColor : activeColor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_CURRENT_FRAGMENT,
                (firstFragment)? KEY_FRAGMENT_USER : KEY_FRAGMENT_DEV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
