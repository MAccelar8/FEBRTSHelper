package com.milan.brtshelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchSouceFromMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_source_from_main);
        final android.widget.SearchView searchview_sfma = (android.widget.SearchView) findViewById(R.id.searchbar_sfma_src);
        searchview_sfma.setIconifiedByDefault(true);
        searchview_sfma.setFocusable(true);
        searchview_sfma.setIconified(false);
//                searchviewmain.requestFocus();
        searchview_sfma.requestFocusFromTouch();

        final List<String> listofstation = new ArrayList<String>();
        ListView searchlistsrc = findViewById(R.id.listview_src);
        listofstation.add("Nehrunagar");
        listofstation.add("Swaminarayan Mandir");
        listofstation.add("Little Wings");

        final ArrayAdapter<String> arraysrcadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listofstation);

        searchlistsrc.setAdapter(arraysrcadapter);
        searchview_sfma.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (listofstation.contains(query)) {
                    arraysrcadapter.getFilter().filter(query);

                }else {
                    //no match found
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arraysrcadapter.getFilter().filter(newText);
                return false;
            }
        });

        searchlistsrc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                if (searchview_sfma.hasFocus()){
                    Intent resultintent = new Intent();
                    resultintent.putExtra("passsrc",str);
                    setResult(Activity.RESULT_OK,resultintent);
                    Log.i("itemcl","Click sdadasd");
                    finishAfterTransition();
                }
            }
        });

    }


}
