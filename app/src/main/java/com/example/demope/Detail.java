package com.example.demope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demope.daos.ArmorDAO;
import com.example.demope.dto.ArmorDTO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Detail extends AppCompatActivity {

    private EditText edtID, edtClassification,edtDes, edtDefense,edtTimeOfCreate;
    private  String action;
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Spinner spinnerStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.spinnerStatus = (Spinner) findViewById(R.id.spinner_status);
        String[] listStatus = {"In progress", "Finished"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerStatus.setAdapter(adapter);
        this.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerStatus.setSelection(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtID = findViewById(R.id.edtID);
        edtClassification = findViewById(R.id.edtClassification);
        edtDes = findViewById(R.id.edtDes);
        edtDefense = findViewById(R.id.edtDefense);
        edtTimeOfCreate = findViewById(R.id.edtTimeOfCreate);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if (action.equals("update")) {
            spinnerStatus.setEnabled(true);
            ArmorDTO dto = (ArmorDTO) intent.getSerializableExtra("dto");
            edtID.setText(dto.getArmorId());
            edtClassification.setText(dto.getClassification());
            edtDes.setText(dto.getDescription());
            edtDefense.setText(dto.getDefense() + "");
            edtTimeOfCreate.setText(dto.getTimeOfCreate());
            String status = dto.isStatus() == false  ? "In progress" :  dto.isStatus() == true? "Finished": "";

            for (int position = 0; position < spinnerStatus.getCount(); position++) {
                if (spinnerStatus.getItemAtPosition(position).equals(status)) {
                    spinnerStatus.setSelection(position);
                    break;
                }
            }

        } else if (action.equals("create")) {
            spinnerStatus.setEnabled(false);
            edtID.setText(intent.getStringExtra("id"));
            Date myDate = new Date();
            String timeCreate = timeStampFormat.format(myDate);
            edtTimeOfCreate.setText(timeCreate);
        }
    }

    public void clickToCancel(View view) {
        finish();
    }


    public void clickToDelete(View view) {
        try {
            String id = edtID.getText().toString();
            ArmorDAO dao= new ArmorDAO();
            FileInputStream fis= openFileInput("Armor.txt");
            List<ArmorDTO> listArmor= dao.loadFromInternal(fis);
            for(int i= 0; i<listArmor.size();i++){
                if(listArmor.get(i).getArmorId().equals(id)){
                    listArmor.remove(i);
                }
            }

            FileOutputStream fos= openFileOutput("Armor.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, listArmor);
            Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show();

            finish();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void clickToSave(View view) {
        try {
            Date myDate = new Date();
            String time = timeStampFormat.format(myDate);

            int id = Integer.parseInt(edtID.getText().toString());
            String name = edtName.getText().toString();
            String des = edtDes.getText().toString();
            int  attack = Integer.parseInt(edtAttack.getText().toString());
            String statusString = spinnerStatus.getSelectedItem().toString();
            int status=statusString.equals("In progress")?1:0;

            WeaponDAO dao= new WeaponDAO();
            FileInputStream fis= openFileInput("Weapon.txt");
            List<WeaponDTO> listWeapon= dao.loadFromInternal(fis);

            if(action.equals("create")){
                WeaponDTO dto= new WeaponDTO(id, name, des, attack,time, status);
                listWeapon.add(dto);
            }else if(action.equals("update")){
                for (WeaponDTO dto: listWeapon
                ) {
                    if (dto.getWeaponId() == id){
                        dto.setWeaponName(name);
                        dto.setAttack(attack);
                        dto.setDescription(des);
                        dto.setTimeOfCreate(time);
                        dto.setStatus(status);
                        break;
                    }

                }
            }
            FileOutputStream fos= openFileOutput("Weapon.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, listWeapon);
            Toast.makeText(this, "Save Internal Success", Toast.LENGTH_SHORT).show();
            finish();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}