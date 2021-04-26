package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Mountain> mountainArrayList=new ArrayList(); //Skapar en ny lista med namnet mountainArrayList. Värden av typen mountain som är skapad.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<Mountain> adapter=new ArrayAdapter<Mountain>(this, R.layout.list_item_textview,R.id.text_T,mountainArrayList); //Adapter pekar på layouten och sedan på id:et och tar in värdet av mountainArrayList. Adapter kopplar samman alla.
        ListView my_listview=(ListView) findViewById(R.id.list_V);


        mountainArrayList.add(new Mountain("Matterhorn","Alps",4478));
        mountainArrayList.add(new Mountain("Mont Blanc","Alps",4808));
        mountainArrayList.add(new Mountain("Denali","Alaska",6190));

    }
}
