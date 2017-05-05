package android.alex.edu.menuapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                    implements BackgroundFragment.onDialogFragmentChoocedColorListener
{

    private static final int REQUEST_CODE_CALL = 1;

    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       layout = (ConstraintLayout) findViewById(R.id.layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {
            case R.id.action_dial:
                dial();
                return true;
            case R.id.action_call:
                call();
                return true;
            case R.id.action_chande_background:
                createDialogFragmentToSetMainActivityBackground(layout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == REQUEST_CODE_CALL)
        {
            //responce for Call Now permissions.

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            }
            else
            {
                Toast.makeText(this, "No Permission for Call", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            throw new RuntimeException("At Callback permission method Unexpected requestCode   = " +  requestCode);
        }
    }

    private void dial()
    {
        Uri webpage = Uri.parse("https://www.google.co.il");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if(canOpen(webIntent))
        {
            startActivity(webIntent);
        }
    }

    private void call()
    {
        Uri phoneNumber = Uri.parse("tel:1111111");
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumber);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
            return;
        }

        startActivity(callIntent);
    }

    private boolean canOpen(Intent intent)
    {
        return intent.resolveActivity(getPackageManager()) != null;
    }

    private void createDialogFragmentToSetMainActivityBackground(ConstraintLayout main_activity_layout)
    {
        BackgroundFragment f = new BackgroundFragment();
        f.show(getSupportFragmentManager(), "BackgroundDialog");
    }


    @Override
    public void onDialogFragmentChoocedColor(int color)
    {
        layout.setBackgroundColor(color);
    }
}
