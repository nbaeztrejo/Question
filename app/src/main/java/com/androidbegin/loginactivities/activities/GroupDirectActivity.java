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

        Button viewAdminGroups = (Button) findViewById(R.id.viewAdminGroups);

        viewAdminGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDirectActivity.this,
                        AdminViewGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button viewNormalGroups = (Button) findViewById(R.id.viewNormalGroups);

        viewNormalGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDirectActivity.this,
                        NormalViewGroupActivity.class);
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