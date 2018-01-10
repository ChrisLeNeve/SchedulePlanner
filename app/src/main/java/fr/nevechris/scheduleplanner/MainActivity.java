package fr.nevechris.scheduleplanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.GregorianCalendar;
import java.util.List;
import fr.nevechris.scheduleplanner.connection.DatabaseContract;
import fr.nevechris.scheduleplanner.connection.DatabaseManager;
import fr.nevechris.scheduleplanner.connection.DbHelper;

import static android.R.attr.id;
import static fr.nevechris.scheduleplanner.R.string.menu_home;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CaldroidFragment caldroidFragment;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Context test = this.getApplicationContext();
//        test.deleteDatabase("planner2.db");

        DatabaseManager.createDbHelper(getApplication());

        caldroidFragment = new CaldroidFragment();
        insertSampleData();
        insertCalendarAndColourTheseDates(fetchTheSampleExamDates());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_exam);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newExamActivity = new Intent(getApplicationContext(), CreateExamActivity.class);
                startActivity(newExamActivity);
//                Intent examsActivity = new Intent(getApplicationContext(), ExamsActivity.class);
//                startActivity(examsActivity);
            }
        });
    }

    private void insertSampleData() {
        SQLiteDatabase writeDb = DatabaseManager.getConnection().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FeedEntry.EXAMS_EXAMID, 1);
        values.put(DatabaseContract.FeedEntry.EXAMS_DATE, getSampleStartDate());
        values.put(DatabaseContract.FeedEntry.EXAMS_TITLE, "Chemistry");
        values.put(DatabaseContract.FeedEntry.EXAMS_DAYSNEEDEDTOPREPARE, 15);
        values.put(DatabaseContract.FeedEntry.EXAMS_DESCRIPTION, "Chemistry test on précipités bleus avec de l'amidon basique and here's some more text for you to make it nice and long");
        values.put(DatabaseContract.FeedEntry.EXAMS_DIFFICULTY, 4);
        values.put(DatabaseContract.FeedEntry.EXAMS_PLACE, "Amphitheater of Rome");
        values.put(DatabaseContract.FeedEntry.EXAMS_TEACHER_NAME, "M. BOUJUUUUUUU!!!");
        long newRowId = writeDb.insert(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, null, values);
    }

    private static String getSampleStartDate() {
        // November 1st. To date. Then to long (unix timestamp). Then to string.
        Date d = new GregorianCalendar(2017, 10, 10).getTime();
        long dateMilliseconds = d.getTime();
        return String.valueOf(dateMilliseconds);
    }

    private List<Date> fetchTheSampleExamDates() {
        SQLiteDatabase readDb = DatabaseManager.getConnection().getReadableDatabase();
        String[] projection = {
                DatabaseContract.FeedEntry.EXAMS_EXAMID,
                DatabaseContract.FeedEntry.EXAMS_DATE
        };

        Cursor cursor = readDb.query(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, projection, null, null, null, null, null);
        List<Date> allExamDates = new ArrayList<Date>();
        Date currentDate = null;
        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_EXAMID));
            long lStartDate = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_DATE)
            );

            currentDate = new Date(lStartDate);
            allExamDates.add(currentDate);
        }

        cursor.close();

        return allExamDates;
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


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        if (item.getItemId() == Integer.valueOf(R.id.nav_home)) {
////                goHome();
//            return true;
//        } else if (item.getItemId() == Integer.valueOf(R.id.nav_subjects)) {
////                showHelp();
//            startActivity(new Intent(this, DateExams.class));
//            return true;
//        } else if (item.getItemId() == Integer.valueOf(R.id.nav_exams)) {
//            return super.onOptionsItemSelected(item);
//        } else {
//            int i = 1;
//        }
//        return false;
//    }


    @Override
    protected void onDestroy() {
//        connection.close();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_layout);
        drawer.closeDrawers();

//        View oudated
//        if (id == R.id.nav_subjects) {
//            Intent subjectsActivity = new Intent(getApplicationContext(), SubjectsActivity.class);
//            startActivity(subjectsActivity);
//        } else
        if (id == R.id.nav_exams) {
            Intent examsActivity = new Intent(getApplicationContext(), ExamsActivity.class);
            startActivity(examsActivity);
        }

        return true;
    }

}
