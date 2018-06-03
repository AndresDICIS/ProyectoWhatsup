package tortisoft.proyectowhatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.StrictMode;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class LoginActivity extends Activity {

    Button Enter, newUser;
    public static String userId;
    public static String Password;
    public static String Name;
    public static String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());


        Enter = (Button) findViewById(R.id.btOk);
        Enter.setOnClickListener(new View.OnClickListener() {

            @Override
        public void onClick(View view) {
            EditText inUser = (EditText)findViewById(R.id.inUser);
            EditText inPassword = (EditText)findViewById(R.id.InPassword);
            SoapObject request = new SoapObject("http://www.ugto.com/Whatsup", "Login");
            request.addProperty("phone", inUser.getText().toString());
            request.addProperty("password", inPassword.getText().toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;
            try
            {
                HttpTransportSE httpTransport = new HttpTransportSE("http://192.168.0.4/WebService/WebService.dll");
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                httpTransport.debug = true;
                httpTransport.call("Login", envelope);
                SoapObject result = (SoapObject)envelope.getResponse();
                if(result != null)
                {
                    userId = result.getProperty(0).toString();
                    Password = inPassword.getText().toString();
                    Name = result.getProperty(1).toString();
                    Phone = result.getProperty(2).toString();
                    Intent Enter = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(Enter);
                }
            }
            catch(Exception e)
            {
                inUser.setText(e.toString());
            }

        }
    });

        newUser = (Button) findViewById(R.id.btinNew);
        newUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent Enter = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(Enter);
            }
        });

    }

}
