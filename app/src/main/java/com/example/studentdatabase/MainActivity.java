package com.example.studentdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
com.example.studentdatabase.DatabaseHelper myDb;
   EditText editName,editSurname,editMarks,editId;
   Button btnAdd,btnView,btnDelete,btnUpdate;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        editName=(EditText) findViewById(R.id.editText_name);
        editSurname=(EditText) findViewById(R.id.editText_surname);
        editMarks=(EditText) findViewById(R.id.editText_Marks);
        btnAdd=(Button) findViewById(R.id.add);
       btnView=(Button) findViewById(R.id.view);
       btnDelete=(Button) findViewById(R.id.delete);
       btnUpdate=(Button) findViewById(R.id.update);
       editId=(EditText) findViewById(R.id.editText_id);
       AddData();
       DeleteData();
       UpdateData();
       viewAll();
   }
   public void AddData(){
       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean isInserted=myDb.insertData(editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
               if(isInserted==true)
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
           }
       });
   }
   public void  DeleteData(){
       btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Integer deletedRows= myDb.deleteData(editId.getText().toString());
               if(deletedRows>0)
                   Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
           }
       });
   }
   public void UpdateData(){
       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean isUpdate= myDb.updateData(editId.getText().toString(),editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
               if(isUpdate==true)
                   Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
           }
       });
   }
   public void showMessage(String title,String Message){
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setCancelable(true);
       builder.setTitle(title);
       builder.setMessage(Message);
       builder.show();
   }
   public void viewAll(){
       btnView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Cursor res=myDb.getAllData();
               if(res.getCount()==0){
                   showMessage("Error","No Data Found");
                   return;
               }
               StringBuffer stringBuffer=new StringBuffer();
               while(res.moveToNext())
               {
                   stringBuffer.append("ID"+res.getString(0)+"\n");
                   stringBuffer.append("NAME"+res.getString(1)+"\n");
                   stringBuffer.append("SURNAME"+res.getString(2)+"\n");
                   stringBuffer.append("MARKS"+res.getString(3)+"\n");
               }
               showMessage("Data",stringBuffer.toString());

           }
       });
   }
}