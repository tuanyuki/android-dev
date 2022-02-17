package com.nta.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvList;
    ArrayList<congViec> arrayList;
    Dialog dialog;
    adapterCV adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvList = (ListView) findViewById(R.id.listView);

        database = new Database(MainActivity.this, "ghichu.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS congViec(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenCV VARCHAR(500))");

        Cursor dataCV = database.GetData("SELECT * FROM congViec");
        arrayList = new ArrayList<>();
        while (dataCV.moveToNext()) {
            arrayList.add(new congViec(dataCV.getString(1), dataCV.getInt(0)));
        }
        adapter = new adapterCV(MainActivity.this,R.layout.layout_cong_viec,arrayList);
        lvList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.idMenuAdd){
            setDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    void setDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_write_work);
        EditText edtContent = (EditText) dialog.findViewById(R.id.idEDTWordToDo);
        Button btnConfirm = (Button) dialog.findViewById(R.id.idBTNConfirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.idBTNCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.QueryData("INSERT INTO congViec VALUES(null,'"+edtContent.getText().toString().trim()+"')");
                updateContent();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    void updateContent(){
        Cursor dataCV = database.GetData("SELECT * FROM congViec");
        arrayList.clear();
        while(dataCV.moveToNext()){
            arrayList.add(new congViec(dataCV.getString(1),dataCV.getInt(0)));
        }
        adapter.notifyDataSetChanged();
    }

    public void setDialogEdit(String s,Integer id){
        Dialog dialogEdit = new Dialog(this);
        dialogEdit.setContentView(R.layout.edit_work);
        Button btnCancel = (Button) dialogEdit.findViewById(R.id.idBTNCancel2);
        Button btnConfirm = (Button) dialogEdit.findViewById(R.id.idBTNConfirm2);
        EditText edtContent = (EditText) dialogEdit.findViewById(R.id.idEDTContentWork);

        edtContent.setText(s);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = edtContent.getText().toString().trim();
                database.QueryData("UPDATE congViec SET TenCV = '"+value+"' WHERE Id = '"+id+"'");
                updateContent();
                dialogEdit.dismiss();
            }
        });
        dialogEdit.show();
    }

    public void setDialogDelete(Integer id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ban co chac muon xoa khong?");
        builder.setNegativeButton("khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM congViec WHERE Id = '"+id+"'");
                updateContent();
            }
        });
        builder.show();
    }
}