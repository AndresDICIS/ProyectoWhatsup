package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class NewUserActivity extends Activity {

    Button Register;
    public String Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        Register = (Button) findViewById(R.id.btRegister);
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText inNewname = (EditText)findViewById(R.id.inNewname);
                EditText inNewphone = (EditText)findViewById(R.id.inNewphone);
                EditText InPassregis = (EditText)findViewById(R.id.InPassregis);
                SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "NewUser");
                request.addProperty("name", inNewname.getText().toString());
                request.addProperty("phone", inNewphone.getText().toString());
                request.addProperty("password", InPassregis.getText().toString());
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try
                {
                    HttpTransportSE httpTransport = new HttpTransportSE(LoginActivity.Ip);
                    httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    httpTransport.debug = true;
                    httpTransport.call("NewUser", envelope);
                    SoapObject result = (SoapObject)envelope.bodyIn;
                    if(result != null)
                    {
                        Intent Enter = new Intent(NewUserActivity.this, LoginActivity.class);
                        startActivity(Enter);
                    }
                }
                catch(Exception e)
                {
                    inNewname.setText(e.toString());
                }
            }

        });
    }
}