package com.abc.crudsqliteii;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    DBHelper myDb;

    EditText et_id, et_name, et_surname, et_year;
    Button btn_add, btn_get, btn_udp, btn_del_one, btn_del_all;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHelper(this);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_surname);
        et_year = findViewById(R.id.et_year);
        btn_add = findViewById(R.id.btn_add);
        btn_get = findViewById(R.id.btn_get);
        btn_udp = findViewById(R.id.btn_udp);
        btn_del_one = findViewById(R.id.btn_del_one);
        btn_del_all = findViewById(R.id.btn_del_all);
        tv = findViewById(R.id.tv);

        addData();
        outData();
        updateData();
        deleteOne();
        delAll();
    }

    public void addData() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String surname = et_surname.getText().toString();
                int year = Integer.parseInt(et_year.getText().toString());

                Data data = new Data(name, surname, year);
                boolean isInserted = myDb.addOne(data);
                if (isInserted = true)
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void outData() {
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "";
                LinkedList<Data> list = myDb.getAll();
                for (Data d: list) {
                    text += d.name + " " + d.surname + " " + d.year + "\n";
                }
                tv.setText(text);
            }
        });
    }

    public void updateData() {
        btn_udp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = myDb.updData(
                        et_id.getText().toString(),
                        et_name.getText().toString(),
                        et_surname.getText().toString(),
                        et_year.getText().toString()
                );
                if (isUpdated == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteOne() {
        btn_del_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.delOne(et_id.getText().toString());
            }
        });
    }

    public void delAll() {
        btn_del_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteAll();
            }
        });
    }
}