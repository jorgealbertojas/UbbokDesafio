package com.example.jorge.myapplication.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.jorge.myapplication.R;
import com.example.jorge.myapplication.util.Common;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (null == savedInstanceState) {
            if (Common.isOnline(this)) {
                initFragment(MoviesFragment.newInstance(true));
                setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.movie_popular));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_option, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    /**
     * Init Fragment for cars
     * @param carFragment
     */
    private void initFragment(Fragment carFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_movies, carFragment);
        transaction.commit();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_movie_popular) {
            if (Common.isOnline(this)) {
                initFragment(MoviesFragment.newInstance(true));
                setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.movie_popular));
            }
            return true;
        }

        if (id == R.id.action_movie_top) {
            if (Common.isOnline(this)) {
                initFragment(MoviesFragment.newInstance(false));
                setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.movie_top));
            }
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
