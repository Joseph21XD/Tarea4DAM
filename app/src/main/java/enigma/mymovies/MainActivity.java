package enigma.mymovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Movie> movies= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout= (GridLayout)findViewById(R.id.gridLayout);
        for(int i=0; i<movies.size();i++){
        int j= i/3;
        View view= gridLayout.getChildAt(i+(j*3));
        ImageView imageView= (ImageView)view;
        imageView.setImageBitmap(movies.get(i).getImagen());
        }
        for(int i=0; i<movies.size();i++){
            int j= i/3;
            View view= gridLayout.getChildAt(i+(j*3)+3);
            TextView textView=(TextView) view;
            textView.setText((i+1)+") "+procesaString(movies.get(i).getNombre())+"\n   Score: "+movies.get(i).getMetascore()+"\n   Rate: "+movies.get(i).getImdbRate());
        }
    }

    public String procesaString(String s){
        String resultado="";
        while(s.length()>9){
            resultado+=s.substring(0,10);
            resultado+="-\n   ";
            s=s.substring(10);
        }
        resultado+=s;
        return resultado;
    }

}
