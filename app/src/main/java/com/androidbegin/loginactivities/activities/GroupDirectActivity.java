package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sara on 11/23/14.
 */
public class GroupDirectActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group_direct_activity);

        // View Admin Groups Button
        Button viewAdminGroups = (Button) findViewById(R.id.viewAdminGroups);

        // View Admin Groups Listener
        viewAdminGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDirectActivity.this,
                        AdminViewGroupActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("isAdmin", true);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        // Vew Normal Groups Button
        Button viewNormalGroups = (Button) findViewById(R.id.viewNormalGroups);

        // View Normal Groups Listener
        viewNormalGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDirectActivity.this,
                        NormalViewGroupActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("isAdmin", false);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GroupDirectActivity.this,
                Welcome.class);
        startActivity(intent);
        finish();
    }
}