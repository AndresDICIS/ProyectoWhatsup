package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends Activity {

    Button Sesion, Enviar, addContact;
    int nContacts;
    ListView lvContacts;
    public static String Prueba;
    public static String ConId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lvContacts = (ListView) findViewById(R.id.lvContacts);
        //-------------------------------------------------------------------Mostrar lista de contactos
        SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "ContactList");
        request.addProperty("phone", LoginActivity.Phone);
        request.addProperty("Password", LoginActivity.Password);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        try
        {
            HttpTransportSE httpTransport = new HttpTransportSE(LoginActivity.Ip);
            httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            httpTransport.debug = true;
            httpTransport.call("ContactList", envelope);
            SoapObject result = (SoapObject)envelope.bodyIn;
            if(result != null)
            {
                final int A = result.getPropertyCount();
                String[] Contacts = new String[A];
                final String[] Ids = new String[A];
                String[] phoneNumbers = new String[A];

                int i;
                for(i = 0; i < result.getPropertyCount(); i++)
                {
                    Prueba = result.getProperty(i).toString();
                    Prueba = Prueba.substring(17, 18);
                    Ids[i] = Prueba;
                }
                for(i = 0; i < result.getPropertyCount(); i++)
                {
                    Prueba = result.getProperty(i).toString();
                    Prueba = Prueba.substring(Prueba.indexOf("namex=") + 6 , Prueba.indexOf(";", Prueba.indexOf("namex=")));
                    Contacts[i] = Prueba;
                }
                for(i = 0; i < result.getPropertyCount(); i++)
                {
                    Prueba = result.getProperty(i).toString();
                    Prueba = Prueba.substring(Prueba.indexOf("phone=") + 6 , Prueba.indexOf(";", Prueba.indexOf("phone=")));
                    phoneNumbers[i] = Prueba;
                }

                ArrayAdapter<String> ContactsList = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, Contacts);
                lvContacts.setAdapter(ContactsList);

                lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ConId = Ids[i];
                        Intent Enter = new Intent (MainActivity.this, MessageActivity.class);
                        startActivity(Enter);
                    }
                });
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
