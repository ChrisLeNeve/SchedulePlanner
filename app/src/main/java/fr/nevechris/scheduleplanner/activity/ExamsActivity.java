package fr.nevechris.scheduleplanner.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.nevechris.scheduleplanner.entity.Exam;
import fr.nevechris.scheduleplanner.service.ExamService;
import fr.nevechris.scheduleplannerold.R;

public class ExamsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    private ExamService examService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        examService = new ExamService(getApplicationContext());

        insertToolbar();
        updateNavigationView();

        fetchExamsWithResults();
    }

    private void insertToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_past_exams));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.examsLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void updateNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view_exams);
        navigationView.getMenu().getItem(1).setChecked(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_exams);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fetchExamsWithResults() {
        new AsyncTask<Void, Void, List<Exam>>() {
            @Override
            protected List<Exam> doInBackground(Void... params) {
                return examService.getExamsWithResults();
            }
            @Override
            protected void onPostExecute(List<Exam> exams) {
                populateList(exams);
            }
        }.execute();
    }

    private void populateList(List<Exam> examsWithResults) {
        String[] titlesAsArray = new String[examsWithResults.size()];
        for (int i = 0; i < examsWithResults.size(); i++)
            titlesAsArray[i] = examsWithResults.get(i).getTitle();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R .layout.results_item_template, titlesAsArray);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.examsLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
