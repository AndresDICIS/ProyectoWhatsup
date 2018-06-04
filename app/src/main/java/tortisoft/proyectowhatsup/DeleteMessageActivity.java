package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class DeleteMessageActivity extends Activity {

    static public String deleteMessageid;
    Button Yes, No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_message);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        Yes = (Button)findViewById(R.id.Yes);
        Yes.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "DeleteMessage");
                request.addProperty("userx_id", LoginActivity.userId);
                request.addProperty("password", LoginActivity.Password);
                request.addProperty("message_id", deleteMessageid);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try
                {
                    HttpTransportSE httpTransport = new HttpTransportSE(LoginActivity.Ip);
                    httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    httpTransport.debug = true;
                    httpTransport.call("DeleteMessage", envelope);
                    SoapObject result = (SoapObject)envelope.bodyIn;
                    if(result != null)
                    {
                        Intent Enter = new Intent (DeleteMessageActivity.this, MessageActivity.class);
                        startActivity(Enter);
                    }
                }
                catch(Exception e)
                {
                    Toast advertencia = Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT );
                    advertencia.show();
                }
                Intent Enter = new Intent (DeleteMessageActivity.this, MessageActivity.class);
                startActivity(Enter);
            }
        });

        No = (Button)findViewById(R.id.No);
        No.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent Enter = new Intent (DeleteMessageActivity.this, MessageActivity.class);
                startActivity(Enter);
            }
        });
    }
}
