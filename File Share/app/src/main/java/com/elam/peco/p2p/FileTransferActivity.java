package com.elam.peco.p2p;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FileTransferActivity extends AppCompatActivity {
    public static final int PICKFILE_RESULT_CODE = 1;
    String activityName = "";
    Button chooseFile , sendFile;
    private Uri fileUri;
    private String filePath;
   static TextView output;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            activityName = extras.getString("activityName");
        }

        if(activityName.equals("SEND")){
            setContentView(R.layout.send_layout);
            chooseFile = findViewById(R.id.choosefiles);
            sendFile = findViewById(R.id.sendfile);
            output = findViewById(R.id.output);

            chooseFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("*/*");
                    chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                }
            });

            sendFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommunicationClass(FileTransferActivity.this).execute(filePath,"SEND");
                }
            });

        }
        else if(activityName.equals("RECEIVE")){
            setContentView(R.layout.receive_layout);
            output = findViewById(R.id.receOutput);
            new CommunicationClass(FileTransferActivity.this).execute(filePath,"RECEIVE");
        }



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
