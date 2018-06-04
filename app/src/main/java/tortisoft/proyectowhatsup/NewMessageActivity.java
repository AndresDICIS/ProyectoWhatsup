package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class NewMessageActivity extends Activity {

    Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        sendMessage = (Button)findViewById(R.id.SendMess);
        sendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                EditText Mensaje = (EditText)findViewById(R.id.Mensaje);
                SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "SendMessagex");
                request.addProperty("user_id", LoginActivity.userId);
                request.addProperty("password", LoginActivity.Password);
                request.addProperty("recipient_id", MainActivity.ConId);
                request.addProperty("content", Mensaje.getText().toString());
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try
                {
                    HttpTransportSE httpTransport = new HttpTransportSE(LoginActivity.Ip);
                    httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    httpTransport.debug = true;
                    httpTransport.call("SendMessagex", envelope);
                    SoapObject result = (SoapObject)envelope.bodyIn;
                    if(result != null)
                    {
                        Intent Enter = new Intent (NewMessageActivity.this, MessageActivity.class);
                        startActivity(Enter);
                    }
                }
                catch(Exception e)
                {
                    Mensaje.setText(e.toString());
                }

            }
        });
    }
}
