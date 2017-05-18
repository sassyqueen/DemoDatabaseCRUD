package demodatabasecrud.android.myapplicationdev.com.demodatabasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variables with UI here
        etContent = (EditText)findViewById(R.id.et_Content);
        tvDBContent = (TextView)findViewById(R.id.tvDBcontent);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnRetrieve = (Button)findViewById(R.id.btnRetrieve);


        al = new ArrayList<String>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long row_affected = dbh.insertNote(data);
                dbh.close();

                if (row_affected != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes());
                dbh.close();

                String txt = "";
                for (int i = 0; i< al.size(); i++){
                    String tmp = al.get(i);
                    txt += tmp + "\n";
                }
                tvDBContent.setText(txt);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);

                String data = al.get(0);
                String id = data.split(",")[0].split(":")[1];
                String content = data.split(",")[1].trim();

                Note target = new Note(Integer.parseInt(id), content);
                i.putExtra("data", target);
                startActivity(i);
            }
        });

    }

}
