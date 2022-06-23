package com.div.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    /**
     Gradle changes:

     implementation("com.squareup.okhttp3:okhttp:4.9.0")
     implementation 'commons-io:commons-io:2.6'


     Manifest changes:

     <uses-permission android:name="android.permission.INTERNET"/>
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>


     In application tag

     android:requestLegacyExternalStorage="true"
     android:usesCleartextTraffic="true"

     * */


    public void CLickMe(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    File mFile=new File( Environment.getExternalStorageDirectory() + "/" +"Download/test tune.mp3");
                    @SuppressLint({"NewApi", "LocalSuppress"}) byte[] bytes = FileUtils.readFileToByteArray(mFile);
                    RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);

                    Request request = new Request.Builder()
                            .url("https://chat.ibollysongs.com/MyMemos/test_tune.mp3")
                            .put(body)
                            .build();

                    OkHttpClient client = new OkHttpClient().newBuilder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            String credential = Credentials.basic("macymind", "macymind123");
                            return response.request().newBuilder().header("Authorization", credential).build();
                        }
                    }).build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response.body().string());
                }catch (Exception e){
                    Log.e("WWW", "onCreate: "+e.toString() );
                }

            }
        }).start();

    }

    public void StartMe(View view) {
        CLickMe();
    }
}