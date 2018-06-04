package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class AddContactActivity extends Activity {

    Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Add = (Button)findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                EditText inContact = (EditText)findViewById(R.id.inContact);
                SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "AddContacto");
                request.addProperty("phone", LoginActivity.Phone);
                request.addProperty("Password", LoginActivity.Password);
                request.addProperty("Contact_phone", inContact.getText().toString());
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try
                {
                    HttpTransportSE httpTransport = new HttpTransportSE(LoginActivity.Ip);
                    httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    httpTransport.debug = true;
                    httpTransport.call("AddContacto", envelope);
                    SoapObject result = (SoapObject)envelope.bodyIn;
                    if(result != null)
                    {
                        Intent Enter = new Intent(AddContactActivity.this, MainActivity.class);
                        startActivity(Enter);
                    }
                }
                catch(Exception e)
                {
                    inContact.setText(e.toString());
                }
            }
        });
    }
}
