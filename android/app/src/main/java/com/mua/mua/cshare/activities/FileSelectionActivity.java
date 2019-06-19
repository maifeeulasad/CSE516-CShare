package com.mua.mua.cshare.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mua.mua.cshare.R;
import com.mua.mua.cshare.model.Util;

public class FileSelectionActivity extends AppCompatActivity {


    Button fileButton;
    TextView fileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selection);

        fileButton=findViewById(R.id.file_button);
        fileTextView=findViewById(R.id.file_location);

        fileButton.setOnClickListener(fileButtonListener);



    }


    View.OnClickListener fileButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("*/*");
            startActivityForResult(i,69);
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==69)
        {
            if(resultCode==RESULT_OK)
            {
                String dat=data.getData().getPath();
                Util.setCurrentFileLink(dat);
                fileTextView.setText(dat.substring(dat.lastIndexOf('/')));
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(FileSelectionActivity.this,"Please Select A File",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
