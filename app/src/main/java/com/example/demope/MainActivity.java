package com.example.demope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demope.daos.ArmorAdapter;
import com.example.demope.daos.ArmorDAO;
import com.example.demope.dto.ArmorDTO;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtTitle;
    private ListView listViewWeapon;
    private ArmorAdapter adapter;
    private ArmorAdapter adapter2;

    private TextView  txtTitleSearchResult;
    private EditText valueTo, valueFrom;
    private ListView listViewArmor, listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            ArmorDAO dao= new ArmorDAO();
            FileInputStream fis = openFileInput("Armor.txt");
            List<ArmorDTO> result= dao.loadFromInternal(fis);
            adapter.setListWeapon(result);
            listViewArmor.setAdapter(adapter);
            listSearch.setAdapter(adapter2);

        } catch (Exception  throwables) {
            throwables.printStackTrace();
        }

    }

    public void clickToLoad(View view) {
        listViewArmor= findViewById(R.id.listViewWeapon);

        adapter= new ArmorAdapter();
        try {
            ArmorDAO dao= new ArmorDAO();
            FileInputStream fis = openFileInput("Armor.txt");
            List<ArmorDTO> result= dao.loadFromInternal(fis);
            adapter.setListWeapon(result);
            listViewArmor.setAdapter(adapter);

            //    txtTitle.setText("List Weapon From Internal");
            listViewArmor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ArmorDTO dto=(ArmorDTO) listViewArmor.getItemAtPosition(i);
                    Intent intent = new Intent(MainActivity.this, Detail.class);
                    intent.putExtra("dto",dto);
                    intent.putExtra("action","update");
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToSearch(View view) {
        txtTitleSearchResult= findViewById(R.id.txtTitleSearchResult);

        valueTo = findViewById(R.id.valueTo);
        valueFrom = findViewById(R.id.valueFrom);


        listViewArmor= findViewById(R.id.listViewWeapon);
        listSearch= findViewById(R.id.listSearch);
        adapter= new ArmorAdapter();
        try {
            ArmorDAO dao= new ArmorDAO();
            FileInputStream fis = openFileInput("Armor.txt");
            List<ArmorDTO> result= dao.loadFromInternal(fis);
            List<ArmorDTO> seachValue= new ArrayList<>();
            for (int i = 0 ;i< result.size();i++){
                if(result.get(i).getDefense() >= Integer.parseInt(valueFrom.getText().toString())
                        && result.get(i).getDefense() <= Integer.parseInt(valueTo.getText().toString())
                        &&result.get(i).isStatus() == true
                ){
                    seachValue.add(result.get(i));
                }
            }
            if(seachValue.size() > 0){
                adapter.setListWeapon(seachValue);
                listSearch.setAdapter(adapter);
                listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ArmorDTO dto=(ArmorDTO) listSearch.getItemAtPosition(i);
                        Intent intent = new Intent(MainActivity.this, Detail.class);
                        intent.putExtra("dto",dto);
                        intent.putExtra("action","update");
                        startActivity(intent);
                    }
                });

                txtTitleSearchResult.setText("");
            }else{
                txtTitleSearchResult.setText("dont exits");
                listSearch.setAdapter(adapter);

                //txtTitleSearchResult.setText("dont exits");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToCreate(View view) {
        Intent intent= new Intent(MainActivity.this,Detail.class );
        intent.putExtra("action","create");
        if (listViewWeapon==null){
            intent.putExtra("id","1");
        }else {
            intent.putExtra("id",(listViewWeapon.getCount()+1)+"");
        }
        startActivity(intent);
    }
}