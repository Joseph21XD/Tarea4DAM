package enigma.mymovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        int s= Integer.parseInt(intent.getStringExtra("VALOR"));
        TextView v1= findViewById(R.id.textView23);
        TextView v2= findViewById(R.id.textView25);
        TextView v3= findViewById(R.id.textView26);
        TextView v4= findViewById(R.id.textView27);
        ImageView imageView= findViewById(R.id.imageView23);
        v1.setText(MainActivity.movies.get(s).getNombre());
        v2.setText(MainActivity.movies.get(s).getDescripcion());
        v3.setText("Metascore: "+ MainActivity.movies.get(s).getMetascore()+"");
        v4.setText("Rating: "+ MainActivity.movies.get(s).getImdbRate()+"");
        imageView.setImageBitmap(MainActivity.movies.get(s).getImagen());
    }
}
