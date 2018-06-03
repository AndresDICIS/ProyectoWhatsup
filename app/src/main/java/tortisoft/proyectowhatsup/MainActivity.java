package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends Activity {

    Button Sesion, Enviar, addContact;
    int nContacts;
    ListView lvContacts;
    public static String Prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lvContacts = (ListView) findViewById(R.id.lvContacts);
        //-------------------------------------------------------------------Mostrar lista de contactos
        SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "ContactList");
        request.addProperty("phone", LoginActivity.Phone);
        request.addProperty("password", LoginActivity.Password);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        try
        {
            HttpTransportSE httpTransport = new HttpTransportSE("http://192.168.0.4/WebService/WebService.dll");
            httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransport.debug = true;
            httpTransport.call("ContactList", envelope);
            SoapObject result = (SoapObject)envelope.getResponse();
            if(result != null)
            {
                Prueba = result.getProperty(0).toString();
                Toast advertencia = Toast.makeText(getApplicationContext(),Prueba, Toast.LENGTH_SHORT );
                advertencia.show();
            }
        }
        catch(Exception e)
        {
            Toast advertencia = Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT );
            advertencia.show();

        }

        Sesion = (Button)findViewById(R.id.Sesion);
        Sesion.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent Enter = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(Enter);
            }
        });

        Enviar = (Button)findViewById(R.id.sendMessage);
        Enviar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent Enter = new Intent (MainActivity.this, NewMessageActivity.class);
                startActivity(Enter);
            }
        });

        addContact = (Button)findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent Enter = new Intent (MainActivity.this, AddContactActivity.class);
                startActivity(Enter);

            }
        });
    }
}
