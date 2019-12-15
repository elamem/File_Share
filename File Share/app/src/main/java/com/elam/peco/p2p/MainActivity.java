package com.elam.peco.p2p;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    static Thread sent;
    static Thread receive;
    static Socket socket;
    private static final int SERVERPORT = 12346;
    private static final String SERVER_IP = "127.0.0.1";
    public static final int PICKFILE_RESULT_CODE = 1;
    private Uri fileUri;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        Button button =  findViewById(R.id.sendj);
        Button chooseFile = findViewById(R.id.chooseFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommunicationClass(MainActivity.this).execute(filePath);
                editText.getText().clear();
            }
        });

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = FileUtil.getPath(getApplicationContext(),fileUri);
                    Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_LONG).show();
                     }

                break;
        }
    }
}