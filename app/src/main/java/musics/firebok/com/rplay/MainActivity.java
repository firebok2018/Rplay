package musics.firebok.com.rplay;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST=1;
    MediaPlayer mp;
    ArrayList<String> arraylist = new ArrayList<String>();
    ListView listview;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        }else{
            doStuff();

        }
    }

    private void doStuff() {

        ListView listView= (ListView) findViewById(R.id.listView);

        getMusic();
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Seleccionado"+id +" " +position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),PlaySong.class).putExtra("pos",position).putExtra("listSong",arrayList));//llama al layout del reporoductor y laa cllas reproductor

                Toast.makeText(MainActivity.this, "sixe"+ arraylist.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "position"+ arraylist.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Empty "+arraylist.isEmpty(), Toast.LENGTH_SHORT).show();


//                switch (position){
//                    default:
//                        if (!mp.isPlaying()) { //si no suena al play
//                            mp.start();
//                        } else if (mp.isPlaying()) { //si suena la paro
//                            mp.pause();
//                        }
//                        break;
//                }






            }

        });
    }


    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle= songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtista= songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songDuration=songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do{
                String currentTitle=songCursor.getString(songTitle);
                String currentArtista=songCursor.getString(songArtista);
                int currentDuration=songDuration;



                arraylist.add(currentTitle+ "\n"+ currentArtista +"\n"+currentDuration);
            }while (songCursor.moveToNext());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle= songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtista= songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do{
                String currentTitle=songCursor.getString(songTitle);
                String currentArtista=songCursor.getString(songArtista);



                arraylist.add(currentTitle+ "\n"+ currentArtista +"\t");
            }while (songCursor.moveToNext());
        }
    }
}
