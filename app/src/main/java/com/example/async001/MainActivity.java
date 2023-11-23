package com.example.async001;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView tvBook;
    Button btnGo, btnHadgama;
    String all = "";
    myAsincClass mac;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBook = (TextView) findViewById(R.id.tvBook);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnHadgama = (Button) findViewById(R.id.btnHadgama);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 goRead(btnGo);

                 */

                mac = new myAsincClass();
              //לקבל פרמטרים אפשר!!! כי בפעולה של הפעולה ברקע יש 3 נקודות!! לכן היא מכניסה למערך כמה פרמטרים שאנחנו רוצים ומאתחלת אותם
                mac.execute();
            }
        });

        btnHadgama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHadgama.setText("Work");
            }
        });
    }

    public void goRead(View view) {
        try {
            InputStream is = getResources().openRawResource(R.raw.mybook);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String temp = br.readLine();

            while (temp != null) {
                all += temp + "\n";
                temp = br.readLine();
            }
            tvBook.setText(all);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            //   Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
        }

    }


    //יוצר קלאס פנימי
    //גנרי - ניתן לשנות סוגי פרמטרים  הראשון והאחרון זה מה שהפעולה מקבלת ומה שהיא מחזירה
    private class myAsincClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InputStream is = MainActivity.this.getResources().openRawResource(R.raw.mybook);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String temp = br.readLine();

                while (temp != null) {
                    all += temp + "\n";
                    temp = br.readLine();
                }
             // אי אפשר להשתמש בפעולה ברקע בשום רכיב שהוא מממשק המשתמש!!
                // לא ניתן להשתמש ברכיבי ה-ui מהפעולה הזאת של הרקע!!
                // tvBook.setText(all);
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        
        protected void onPreExecute(Void unused) {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            btnGo.setText("Asinc Task finished");
            tvBook.setText(all);

        }
    }
}