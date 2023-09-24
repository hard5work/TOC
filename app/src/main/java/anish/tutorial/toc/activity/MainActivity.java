package anish.tutorial.toc.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import anish.tutorial.toc.R;

public class MainActivity extends AppCompatActivity {



    private AppBarConfiguration mAppBarConfiguration;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_hints,
                R.id.nav_wordSearch,R.id.nav_avgMath,R.id.nav_percentageCal,R.id.nav_pieChart, R.id.nav_ArdensRule)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.hint_setting) {
           /* HintFragment fragment = new HintFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,fragment);
            transaction.commit();*/
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_hint, null, false);
            final TextView textView = v.findViewById(R.id.hintView);
            String text = "\n\t\t\t\tHint for Converting to CNF \n\n" +
                    "Firstly Symbols and Variables are inserted one by one\n\n" +
                    "For eg: if S->AAC/AC/D then in application we follow like\n\n" +
                    "S in Symbol and AAC in variable and press on SET for saving production " +
                    "and again S and AC and SET and similar S and D is SET\n\n" +
                    " After enter all the variables and symbol we press on calculate\n\n" +
                    "For Null Production 'E' is used and 'E' is not used as symbol\n\n" +
                    "Here + - * / special character cannot be used and other special character also cannot be used\n\n" +
                    "In production question any letter (A-Z)/(a-z) and numbers (0-9) can be used\n\n" +
                    "RESET button empty or clears the data\n\n" +
                    "There is also suggestion in variable after word is entered and calculated\n\n";
            textView.setText(text);
            textView.setTextIsSelectable(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(v);

            AlertDialog dia = builder.create();
            dia.show();
            dia.setCancelable(true);


        }
        if (item.getItemId() == R.id.action_settings) {
            Intent set = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(set);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    boolean doubleBackToExit = false;

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // System.exit(1);
        if (doubleBackToExit) {
            super.onBackPressed();
        }
        this.doubleBackToExit = true;
        Toast.makeText(getApplication(), "Press Twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        }, 2000);

    }
}