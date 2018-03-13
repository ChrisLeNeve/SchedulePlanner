package fr.nevechris.scheduleplanner.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.nevechris.scheduleplanner.service.ExamService;
import fr.nevechris.scheduleplannerold.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CaldroidFragment caldroidFragment;
    private NavigationView navigationView;

    private ExamService examService;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu_home);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.context = this.getApplicationContext();

        examService = new ExamService(getApplicationContext());

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        caldroidFragment = new CaldroidFragment();
        fetchDatesAndUpdateCalendar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_exam),
                deleteExamsFab = (FloatingActionButton) findViewById(R.id.fab_delete_all_exams);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newExamActivity = new Intent(getApplicationContext(), CreateExamActivity.class);
                startActivity(newExamActivity);
            }
        });

        deleteExamsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllExams();
            }
        });

    }

    private void deleteAllExams()  {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return examService.deleteAllExams();
            }
            @Override
            protected void onPostExecute(Boolean successfullyDeleted) {
                if (successfullyDeleted)
                    insertCalendarAndColourTheseDates(new ArrayList<Date>());
                else
                    Toast.makeText(context, "Delete operation failed lad!", Toast.LENGTH_LONG);
            }
        }.execute();
    }

    private void fetchDatesAndUpdateCalendar() {
        new AsyncTask<Void, Void, List<Date>>() {
            @Override
            protected List<Date> doInBackground(Void... params) {
                return examService.getAllExamDates();
            }
            @Override
            protected void onPostExecute(List<Date> dates) {
                insertCalendarAndColourTheseDates(dates);
            }
        }.execute();
    }

    private void insertCalendarAndColourTheseDates(List<Date> allExamDates) {
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        ColorDrawable lightBlueColor = new ColorDrawable(Color.rgb(151, 251, 253));
        for (int i = 0; i < allExamDates.size(); i++) {
            caldroidFragment.setBackgroundDrawableForDate(lightBlueColor, allExamDates.get(i));
        }

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarWrapper, caldroidFragment);
        t.commit();

        caldroidFragment.refreshView();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                if (date == null)
                    return;
                long dateInMs = date.getTime();
                String sDateInMs = String.valueOf(dateInMs);

                Intent dateExam = new Intent(getApplicationContext(), DateExams.class);
                dateExam.putExtra("date", sDateInMs);
                startActivity(dateExam);
            }

//            @Override
//            public void onChangeMonth(int month, int year) {
//                String text = "month: " + month + " year: " + year;
//                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//            }
        };

        caldroidFragment.setCaldroidListener(listener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.activity_main_drawer, menu);
         return true;
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_layout);
        drawer.closeDrawers();

//        View oudated
//        if (id == R.id.nav_subjects) {
//            Intent subjectsActivity = new Intent(getApplicationContext(), SubjectsActivityold.class);
//            startActivity(subjectsActivity);
//        } else
        if (id == R.id.nav_exams) {
            Intent examsActivity = new Intent(getApplicationContext(), ExamsActivity.class);
            startActivity(examsActivity);
        }

        return true;
    }

}
