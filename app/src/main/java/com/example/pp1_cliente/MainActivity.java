package com.example.pp1_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.system.Os;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity<Sting> extends AppCompatActivity {
    private EditText EditPname, EditNum, coordX,coordY;
    private Button btnR, btnV, btnA, btnCrear, btnConfir;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditPname = findViewById(R.id.EditPname);
        EditNum = findViewById(R.id.EditNum);
        coordX = findViewById(R.id.CoordX);
        coordY = findViewById(R.id.CoordY);

        btnR = findViewById(R.id.btnR);
        btnV = findViewById(R.id.btnV);
        btnA = findViewById(R.id.btnA);
        btnCrear = findViewById(R.id.btnCrear);
        btnConfir = findViewById(R.id.btnConfir);

        //--------------------
        btnR.setOnClickListener(
            (v)->{
           String x = coordX.getText().toString();
           String y = coordY.getText().toString();
           EnviarMensaje(x+":"+y);

           initCliente();

    }
    );
    }
    public void initCliente() {
        new Thread(
                ()->{
                    try{
                        //para solicitar la conexión
                        Socket socket = new Socket("192.168.20.1", 5000);

                        //cliente y server conectados
                        System.out.println("Conexión exitosa");

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        reader = new BufferedReader(isr);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        writer = new BufferedWriter(osw);

                        while(true) {
                            System.out.println("Esperando...");
                            String line = reader.readLine();
                            System.out.println("Recibido");
                            System.out.println("Recibido: " + line);

                           /* String[] coord = ((String) line).split (":");
                            int x = Integer.parseInt(coord[0]);
                            int Y = Integer.parseInt(coord[1]);
                            PVector vector = new PVector(x,y);
                            posiciones.add(vector);*/

                        }

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    public void EnviarMensaje(String msj){
        new Thread(
                ()->{

                    try{
                       /* OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter writer = new BufferedWriter(osw);*/

                        writer.write(msj + "\n");
                        writer.flush();

                } catch (IOException e) {
                        e.printStackTrace();
                    }
        }
        ).start();

   }

}

