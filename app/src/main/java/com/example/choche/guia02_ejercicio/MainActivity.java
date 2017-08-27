package com.example.choche.guia02_ejercicio;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static boolean cambiar_nombre;
    public static String nuevo_nombre;
    private TextView lblEstado;
    private EditText url,nombre;
    private Button btnDescargar;
    private RadioButton rbnCambiar,rbnNoCambiar;
    private ProgressBar prgBarra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblEstado = (TextView) findViewById(R.id.lblEstado);
        url = (EditText) findViewById(R.id.txtURL);
        nombre=(EditText) findViewById(R.id.txtNombre);
        btnDescargar = (Button) findViewById(R.id.btnDescargar);
        rbnCambiar=(RadioButton) findViewById(R.id.rbnCambiarnombre);
        rbnNoCambiar=(RadioButton) findViewById(R.id.rbnNoCambiarnombre);
        prgBarra=(ProgressBar) findViewById(R.id.prgBarra);

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo_nombre=nombre.getText().toString();
                new Descargar(
                        MainActivity.this,
                        lblEstado,
                        btnDescargar,prgBarra
                ).execute(url.getText().toString());
            }
        });

        rbnCambiar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    nombre.setEnabled(true);
                    cambiar_nombre=true;
                }else{
                    nombre.setEnabled(false);
                    cambiar_nombre=false;
                }
            }
        });
        verifyStoragePermissions(this);
    }
    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
