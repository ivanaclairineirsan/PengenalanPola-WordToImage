package com.example.ivanaclairine.wordtoimage;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ImageActivity extends ActionBarActivity {
    TextView textView;
    ImageView imageView;
    TextView letterView;
    String path = "/storage/emulated/0/Pictures/A-Z/";

    private static String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final String newString;
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.inputText);
        letterView = (TextView) findViewById(R.id.letterView);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("textInput");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("textInput");
        }

        textView.setText("Input: " + newString);
        final List<String> imagePaths = getImagePaths(newString);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                Log.d("path", imagePaths.get(i));
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePaths.get(i)));
                letterView.setText(String.valueOf(newString.charAt(i)));
                i++;
                if (i > imagePaths.size()- 1) {
                    i = 0;
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    public List<String> getImagePaths(String text){
        List<String> retList = new ArrayList<>();
        for(int i=0; i<text.length(); i++){
            retList.add(i, path + text.charAt(i) + ".jpg");
        }
        return retList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
