package enigma.mymovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {
    Spinner spinner;
    String categoria="list_order";
    String orden= "asc";

    public void pushRadioButton(View view){
        RadioButton radioButton= findViewById(R.id.radioButton);
        RadioButton radioButton2= findViewById(R.id.radioButton2);
        RadioButton radioButton3= (RadioButton)view;
        String s= radioButton3.getText().toString();
        if(s.equals("Ascendant")){
            Log.d("ORDEN","ASCENDANT");
            radioButton.setChecked(true);
            radioButton2.setChecked(false);
            orden="asc";
        }
        else{
            Log.d("ORDEN","DISCENDANT");
            radioButton.setChecked(false);
            radioButton2.setChecked(true);
            orden="desc";
        }

    }

    public void pushButton(View view){
        GetInfo getInfo= new GetInfo();
        try {
            String s="http://www.imdb.com/list/ls068796278/?sort=";
            s+=categoria.replaceAll(" ", "_");
            MainActivity.movies= getInfo.execute(s+","+orden+"&st_dt=&mode=detail&page=1").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"list_order","alpha","moviemeter","user rating","num votes","release date","runtime","date added"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_layout, letra));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                categoria= adapterView.getItemAtPosition(pos)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
    }

    private class GetInfo extends AsyncTask<String,Void,ArrayList<Movie>> {


        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            ArrayList<Movie> movies= new ArrayList<>();
            URL pagina = null;
            try {
                boolean isFirst= true;
                String img="loadlate=";
                String nam="> <img alt=\"";
                String met="metascore  favorable";
                String rat="rating-rating \"><span class=\"value\">";
                pagina = new URL(strings[0]);
                URLConnection uc = pagina.openConnection();
                uc.connect();
                BufferedReader lector = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
                String linea = "";
                String nombre= "";
                String imagen= "";
                String rate="";
                String score="";
                int cant= 21;
                int cont=4;
                while ((linea = lector.readLine()) != null && cant!=0) {
                    if (linea.contains(img)) {
                        String s=linea.substring(10);
                        imagen= s.substring(0,s.length()-1);
                        cont--;}
                    else if(linea.contains(nam)){
                        if(!isFirst){
                            String s=linea.substring(12);
                            nombre= s.substring(0,s.length()-1);
                            cont--;}
                        else
                            isFirst=false;
                    }
                    else if(linea.contains(met)){
                        String s=linea.substring(35);
                        score= s.substring(0,s.length()-15);
                        cont--;
                    }
                    else if(linea.contains(rat)){
                        String s=linea.substring(49);
                        rate= s.substring(0,s.length()-69);
                        Log.d("ESTE",rate);
                        cont--;
                    }
                    if(cont==0){
                        cant--;
                        cont=4;
                        URL url= new URL(imagen);
                        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                        httpURLConnection.connect();
                        InputStream inputStream= httpURLConnection.getInputStream();
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        movies.add(new Movie(nombre,bitmap,Double.parseDouble(rate),Integer.parseInt(score)));
                    }

                }


            } catch (MalformedURLException e) {
                Log.d("ERROR", "MalformedURLException");
            } catch (IOException e) {
                Log.d("ERROR", "IOException");
            }

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> s) {
            MainActivity.movies= s;
            Intent intent= new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
