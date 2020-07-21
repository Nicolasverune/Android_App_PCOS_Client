package com.coste.pcos_summer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    int ViewID;

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeriesD;
    PointsGraphSeries<DataPoint> xySeriesA;

    PointsGraphSeries<DataPoint> onClickSeries;

    //create graphview object
    GraphView mScatterPlot;

    //make xyValueArray global
    ArrayList<XYValue> xyValueArrayD;
    ArrayList<XYValue> xyValueArrayA;

    TextView textCheckedID, textCheckedIndex;

    private int iDidSeeEverything = 0;
    private int quiz1 = 1;
    private int quiz2 = 1;
    private int quiz3 = 1;
    private int depression;
    private int anxiety;
    private int sendShower = 1;

    private int dq1_0,aq1_0,dq1_1,aq1_1,dq1_2,aq1_2,dq2_0,aq2_0,dq2_1,aq2_1,dq3_0,aq3_0,dq3_1,aq3_1;


    private String squiz = null;
    private String ID = null;

    private Button mLogin = null;
    private Button mRegister = null;
    private Button mConfirm = null;
    private Button mfinal = null;
    private Button mseedata = null;
    private Button mquestseedata = null;

    private Button mquest = null;

    private Button mquest1 = null;
    private Button mquest2 = null;
    private Button mquest3 = null;

    private EditText mq1 = null;
    private EditText mq2 = null;

    private boolean isInLogin = true;

    private String message;
    public String messager;

    private Socket client;


    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    private View.OnClickListener clickListenerBoutons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //LOGIN PAGE
            setContentView(R.layout.activity2);

            ViewID = 1;

            mRegister = (Button) findViewById(R.id.register);
            mRegister.setOnClickListener(clickListenerBoutons2);
            isInLogin = true;
            mConfirm = (Button) findViewById(R.id.confirm);
            mConfirm.setOnClickListener(clickListenerBoutons3);
            // This callback will only be called when MyFragment is at least Started.

        }
    };

    @Override
    public void onBackPressed() {
        switch (ViewID){
            case(1) :
                setContentView(R.layout.activity_main);
                mLogin = (Button) findViewById(R.id.login);
                mRegister = (Button) findViewById(R.id.register);
                mLogin.setOnClickListener(clickListenerBoutons);
                mRegister.setOnClickListener(clickListenerBoutons2);
                break;
            case(2) :
                questionnaireMENU();
                break;
            }
        return;
    }


    private View.OnClickListener clickListenerBoutons2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Register PAGE
            setContentView(R.layout.activity3);

            ViewID = 1;

            mLogin = (Button) findViewById(R.id.login);
            mLogin.setOnClickListener(clickListenerBoutons);
            isInLogin = false;
            mConfirm = (Button) findViewById(R.id.confirm);
            mConfirm.setOnClickListener(clickListenerBoutons3);


            //TO force some min max values in some fields (ex : birth date not before 1900)
            //edit : min values doesn't seem to work
            EditText et = (EditText) findViewById(R.id.ETR3);
            et.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2020")});

            EditText et4_1 = (EditText) findViewById(R.id.ETR4_1);
            et4_1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "31")});
            EditText et4_2 = (EditText) findViewById(R.id.ETR4_2);
            et4_2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
            EditText et4_3 = (EditText) findViewById(R.id.ETR4_3);
            et4_3.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2020")});

            EditText et5_1 = (EditText) findViewById(R.id.ETR5_1);
            et5_1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "31")});
            EditText et5_2 = (EditText) findViewById(R.id.ETR5_2);
            et5_2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
            EditText et5_3 = (EditText) findViewById(R.id.ETR5_3);
            et5_3.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2020")});

            EditText et6_1 = (EditText) findViewById(R.id.ETR6_1);
            et6_1.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "31")});
            EditText et6_2 = (EditText) findViewById(R.id.ETR6_2);
            et6_2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
            EditText et6_3 = (EditText) findViewById(R.id.ETR6_3);
            et6_3.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2020")});

        }
    };


    private View.OnFocusChangeListener editTextListner = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            //Works for all string so we need the name to get a personalized name
            String fullName = getResources().getResourceName(view.getId());
            String name = fullName.substring(fullName.lastIndexOf("/") + 1);

            //Toast is going to disapear
            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

            //We load our shared preferences
            SharedPreferences preferences = getSharedPreferences("prefID", Context.MODE_PRIVATE) ;
            SharedPreferences.Editor editor = preferences.edit();

            //we take the good view
            EditText text = (EditText) findViewById(view.getId());
            //get the good share sets the name of the save to #Answerxxx with xxx the number of the question
            editor.putString(getTheGoodShared(name,"Answer",0), text.getText().toString());
            editor.apply();
        }
    };

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }



    //Login Register Function
    private View.OnClickListener clickListenerBoutons3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            byte[] data  = Base64.getDecoder().decode("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAktfiT+cnZPj0mm0sxJ13ENlFg7+qQpPSp5mCgFSJpV9Onvs3FLVFlPR5v9iZYOdPq0IgPk8OKeuIsKuLE6KwvRfup3vhtkTI+rrUL6dvTalFldtN2bC4UEiiFifVzaVYhzYK39h8fwD/5JKrr7FsI7izkVELFX7wcCELHcSbx6lNYD102JSgdbY7lqn+PuFi7AUxalr8YcjONWOqztRoZg5BcrxY9OULLHpxxxxUZotIIthXYuIEmvmt37bPUor4m/iAOLiv1o2BVWUw+kNmEoC6L1tPOWfjkFsNHpsSZb2NtvVr9q48XqvcxumyB8tIQOEvMyUYGWl4BeUlyH/ChwIDAQAB".getBytes());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
            KeyFactory fact = null;
            try {
                fact = KeyFactory.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            PublicKey public_key = null;
            try {
                public_key = fact.generatePublic(spec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }


            if (isInLogin)
            {
                //LOGIN CHECK -- Added to database
                EditText text1 = (EditText)findViewById(R.id.ETL1);
                ID = text1.getText().toString();
                EditText text2 = (EditText)findViewById(R.id.ETL2);
                String Password = text2.getText().toString();
                try {
                    Password = encrypt(Password,public_key);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "snif" , Toast.LENGTH_SHORT).show();
                }
                if(!ID.isEmpty() && !Password.isEmpty() )
                {

                    //We build the string for the server
                    // 2 stands for LOGIN
                    message = "2^"+ID+"%"+Password;

                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();

                    try {
                        //We wait for the server to respond the "received" message that confirms everything is ok
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Response from the server
                    if (messager != null) {
                        //We can't send data again

                        String logtest = messager.split("\\^")[0];
                        Toast.makeText(getApplicationContext(), messager.split("\\^")[1] , Toast.LENGTH_SHORT).show();
                        if (logtest.equals("1")) {
                            //Loging in or Registered
                            questionnaireMENU();
                        }
                    }
                    else {
                        //Problem sending the message
                        Toast.makeText(getApplicationContext(), "Try again please", Toast.LENGTH_SHORT).show();
                    }
                }
                else{Toast.makeText(getApplicationContext(), "Please fill ", Toast.LENGTH_SHORT).show();}


            }
            else{
                //REGISTER CHECK -- Everything filled -- Not in database
                // WE first get all our strings back
                EditText text1 = (EditText)findViewById(R.id.ETR1);
                ID = text1.getText().toString();
                EditText text2 = (EditText)findViewById(R.id.ETR2);
                String Password = text2.getText().toString();
                try {
                    Password = encrypt(Password,public_key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EditText text3 = (EditText)findViewById(R.id.ETR3);
                String YearBirth = text3.getText().toString();
                EditText text4_1 = (EditText)findViewById(R.id.ETR4_1);
                EditText text4_2 = (EditText)findViewById(R.id.ETR4_2);
                EditText text4_3 = (EditText)findViewById(R.id.ETR4_3);
                String tempo1 = text4_1.getText().toString();
                String tempo2 = text4_2.getText().toString();
                String tempo3 = text4_3.getText().toString();

                EditText text5_1 = (EditText)findViewById(R.id.ETR5_1);
                EditText text5_2 = (EditText)findViewById(R.id.ETR5_2);
                EditText text5_3 = (EditText)findViewById(R.id.ETR5_3);
                String tempo4 = text5_1.getText().toString();
                String tempo5 = text5_2.getText().toString();
                String tempo6 = text5_3.getText().toString();

                EditText text6_1 = (EditText)findViewById(R.id.ETR6_1);
                EditText text6_2 = (EditText)findViewById(R.id.ETR6_2);
                EditText text6_3 = (EditText)findViewById(R.id.ETR6_3);
                String tempo7 = text6_1.getText().toString();
                String tempo8 = text6_2.getText().toString();
                String tempo9 = text6_3.getText().toString();

                RadioGroup rg = (RadioGroup) findViewById(R.id.RGR);
                int selectedRadioButtonID = rg.getCheckedRadioButtonId();
                String textradio = "";
                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    textradio = selectedRadioButton.getText().toString();
                }


                //We check needed fields aren't empty
                if(!ID.isEmpty() && !Password.isEmpty() && !YearBirth.isEmpty() && !tempo1.isEmpty() && !tempo2.isEmpty() && !tempo3.isEmpty()
                && !textradio.isEmpty())
                {
                    String FirstVisit = tempo1+"/"+tempo2+"/"+tempo3;
                    String Date1 = tempo4+"/"+tempo5+"/"+tempo6;
                    String Date2 = tempo7+"/"+tempo8+"/"+tempo9;
                    //We build the string for the server
                    //1 STANDS FOR REGISTER
                    message = "1^"+ID+"%"+Password+"%"+YearBirth+"%"+FirstVisit+"%"+Date1+"%"+Date2+"%"+textradio;
                    //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();

                    try {
                        //We wait for the server to respond the "received" message that confirms everything is ok
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Response from the server
                    if (messager != null) {
                        //We can't send data again

                        String logtest = messager.split("\\^")[0];
                        Toast.makeText(getApplicationContext(), messager.split("\\^")[1] , Toast.LENGTH_SHORT).show();
                        if (logtest.equals("1")) {
                            //Loging in or Registered
                            questionnaireMENU();
                        }
                    }
                    else {
                        //Problem sending the message
                        Toast.makeText(getApplicationContext(), "Try again please", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                    Toast.makeText(getApplicationContext(), "Please fill ", Toast.LENGTH_SHORT).show();

                }
            }
        }
    };


    private View.OnClickListener clickListenerBoutonsquest1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.questionnaire1);

            ViewID = 2;

            if (quiz1==1)
                iDidSeeEverything += 1;

            quiz1 = 0;

            mquest1 = (Button) findViewById(R.id.quest);
            mquest1.setOnClickListener(clickListenerBoutonsMENU);

            RadioGroup radioGroupq1_0;
            radioGroupq1_0 = (RadioGroup)findViewById(R.id.q1_0);
            radioGroupq1_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq1_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq1_0.getId()),radioGroupq1_0);
            RadioGroup radioGroupq1_1;
            radioGroupq1_1 = (RadioGroup)findViewById(R.id.q1_1);
            radioGroupq1_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq1_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq1_1.getId()),radioGroupq1_1);
            RadioGroup radioGroupq1_2;
            radioGroupq1_2 = (RadioGroup)findViewById(R.id.q1_2);
            radioGroupq1_2.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq1_2.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq1_2.getId()),radioGroupq1_2);


        }
    };


    private View.OnClickListener clickListenerBoutonsquest2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.questionnaire2);

            ViewID = 2;

            if (quiz2==1)
                iDidSeeEverything += 1;

            quiz2 = 0;

            mquest1 = (Button) findViewById(R.id.quest);
            mquest1.setOnClickListener(clickListenerBoutonsMENU);

            RadioGroup radioGroupq2_0;
            radioGroupq2_0 = (RadioGroup)findViewById(R.id.q2_0);
            radioGroupq2_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq2_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq2_0.getId()),radioGroupq2_0);
            RadioGroup radioGroupq2_1;
            radioGroupq2_1 = (RadioGroup)findViewById(R.id.q2_1);
            radioGroupq2_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq2_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq2_1.getId()),radioGroupq2_1);


        }
    };

    private View.OnClickListener clickListenerBoutonsquest3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.questionnaire3);

            ViewID = 2;

            if (quiz3==1)
            iDidSeeEverything += 1;

            quiz3 = 0;

            mquest1 = (Button) findViewById(R.id.quest);
            mquest1.setOnClickListener(clickListenerBoutonsMENU);

            //the changelistenerDA is muted cause only one change listener seems to work

            RadioGroup radioGroupq3_0;
            radioGroupq3_0 = (RadioGroup)findViewById(R.id.q3_0);
            radioGroupq3_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq3_0.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq3_0.getId()),radioGroupq3_0);
            RadioGroup radioGroupq3_1;
            radioGroupq3_1 = (RadioGroup)findViewById(R.id.q3_1);
            radioGroupq3_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
//radioGroupq3_1.setOnCheckedChangeListener(radioGroupOnCheckedChangeListenerDA);
            LoadPreferences(Integer.toString(radioGroupq3_1.getId()),radioGroupq3_1);
        }
    };


    private View.OnClickListener clickListenerBoutonsMENU  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            questionnaireMENU();
        }
    };

    private void questionnaireMENU(){
        setContentView(R.layout.questionnaire_menu);

        ViewID = 2;

        mfinal = (Button) findViewById(R.id.finalSend);
        mfinal.setOnClickListener(clickListenerBoutonsSend);
        if (sendShower == 1) {
            if (iDidSeeEverything == 3 )
                mfinal.setEnabled(true);
            else
                mfinal.setEnabled(false);}
        else {
            mfinal.setEnabled(false);
        }

        mquest1 = (Button) findViewById(R.id.quest1);
        mquest1.setOnClickListener(clickListenerBoutonsquest1);

        mquest2 = (Button) findViewById(R.id.quest2);
        mquest2.setOnClickListener(clickListenerBoutonsquest2);

        mquest3 = (Button) findViewById(R.id.quest3);
        mquest3.setOnClickListener(clickListenerBoutonsquest3);

        mseedata = (Button) findViewById(R.id.seeData);
        mseedata.setOnClickListener(clickListenerBoutonsSeeData);

        //Do not remove, sending button !


    }

    /*
    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListenerDA =
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId) {
                        case R.id.q1_0_1:
                            dq1_0 = 1;
                            aq1_0 = 2 ;
                            break;
                        case R.id.q1_0_2:
                            dq1_0 = 1;
                            aq1_0 = 2 ;
                            break;
                        case R.id.q1_0_3:
                            dq1_0 = 1;
                            aq1_0 = 2 ;
                            break;
                        case R.id.q1_1_1:
                            dq1_1 = 1;
                            aq1_1 = 2 ;
                            break;
                        case R.id.q1_1_2:
                            dq1_1 = 1;
                            aq1_1 = 2 ;
                            break;
                        case R.id.q1_2_1:
                            dq1_2 = 1;
                            aq1_2 = 2  ;
                            break;
                        case R.id.q1_2_2:
                            dq1_2 = 1;
                            aq1_2 = 2 ;
                            break;
                        case R.id.q2_0_1:
                            dq2_0 = 1;
                            aq2_0 = 2  ;
                            break;
                        case R.id.q2_0_2:
                            dq2_0 = 1;
                            aq2_0 = 2 ;
                            break;
                        case R.id.q2_0_3:
                            dq2_0 = 1;
                            aq2_0 = 2 ;
                            break;
                        case R.id.q2_1_1:
                            dq2_1 = 1;
                            aq2_1 = 2 ;
                            break;
                        case R.id.q2_1_2:
                            dq2_1 = 1;
                            aq2_1 = 2 ;
                            break;
                        case R.id.q2_1_3:
                            dq2_1 = 1;
                            aq2_1 = 2 ;
                            break;
                        case R.id.q3_0_1:
                            dq3_0 = 1;
                            aq3_0 = 2 ;
                            break;
                        case R.id.q3_0_2:
                            dq3_0 = 1;
                            aq3_0 = 2 ;
                            break;
                        case R.id.q3_1_1:
                            dq3_1 = 1;
                            aq3_1 = 2  ;
                            break;
                        case R.id.q3_1_2:
                            dq3_1 = 1;
                            aq3_1 = 2 ;
                            break;
                    }}};
    */

    private View.OnClickListener clickListenerBoutonsSend  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //We send the data in a local json file and to the server
            message = squiz; //Function needed to set the right message on, message in function of answers
            int depressionScore =dq1_0+dq1_1+dq1_2+dq2_0+dq2_1+dq3_0+dq3_1;
            int anxietyScore =aq1_0+aq1_1+aq1_2+aq2_0+aq2_1+aq3_0+aq3_1;
            //To the json file
            try {
                //In facts it's not a json file .. but doesn't change anything
                //v stands for view
                //depression score should have a function, calculating its value

                stringToJson(message,depressionScore,anxietyScore,v);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(), "local saved done 1/2", Toast.LENGTH_LONG).show();

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


            message = "3^"+ID+"%"+message;
            //To the server
            SendMessage sendMessageTask = new SendMessage();
            sendMessageTask.execute();

            try {
                //We wait for the server to respond the "received" message that confirms everything is ok
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Response from the server
            if (messager != null) {
                //We can't send data again
                if (messager == "error"){
                    Toast.makeText(getApplicationContext(), messager , Toast.LENGTH_LONG).show();
                    clearpref();
                    //Back to Start
                    setContentView(R.layout.activity_main);
                    mLogin = (Button) findViewById(R.id.login);
                    mRegister = (Button) findViewById(R.id.register);
                    mLogin.setOnClickListener(clickListenerBoutons);
                    mRegister.setOnClickListener(clickListenerBoutons2);
                }
                else {
                    Toast.makeText(getApplicationContext(), messager,  Toast.LENGTH_SHORT).show();
                    //We also clear all the preferences for the next time they answer.
                    sendShower = 0;
                    mfinal.setEnabled(false);
                    clearpref();
                    ;
                }
            }
            else {
                //Problem sending the message
                Toast toast4 = Toast.makeText(getApplicationContext(), "Try again please", Toast.LENGTH_SHORT);
                toast4.show();
            }





        }
    };

    public void getDataFromTxt(){

        ViewID = 2;

        String simpleFileName ="note.txt";
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xySeriesD = new PointsGraphSeries<>();
        xySeriesA = new PointsGraphSeries<>();
        xyValueArrayD = new ArrayList<>();
        xyValueArrayA = new ArrayList<>();
        int count = 1;
        int maxYD=0;
        int maxYA=0;
        int minYD=0;
        int minYA=0;
        int maxY=0;
        int minY=0;
        try {
            FileInputStream in = this.openFileInput(simpleFileName);
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s = "";
            while((s= br.readLine())!= null)  {
                Log.d(s,"nice");
                try{String before_last = s.split("\\$")[s.split("\\$").length -2];
                    String last = s.substring(s.lastIndexOf('$') + 1);
                depression = Integer.parseInt(before_last);
                anxiety = Integer.parseInt(last);
                Log.d(Integer.toString(depression),Integer.toString(count));
                xyValueArrayD.add(new XYValue(count,depression));
                xyValueArrayA.add(new XYValue(count,anxiety));
                if (count==1){
                    maxYD = depression;
                    maxYA = anxiety;
                    minYD = depression;
                    minYA = anxiety;
                }
                else {
                    if (depression<minYD){
                        minYD = depression;
                    }
                    if (depression>maxYD){
                        maxYD = depression;
                    }
                    if (anxiety<minYA){
                        minYA = anxiety;
                    }
                    if (anxiety>maxYA){
                        maxYA = anxiety;
                    }
                }
                }
                catch (Exception e){}
                count ++;
            }
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    //sort it in ASC order
    xyValueArrayD = sortArray(xyValueArrayD);
    xyValueArrayA = sortArray(xyValueArrayA);
    //add the data to the series
            for(int i = 0;i <xyValueArrayD.size(); i++){
        double xD = xyValueArrayD.get(i).getX();
        double yD = xyValueArrayD.get(i).getY();
        xySeriesD.appendData(new DataPoint(xD,yD),true, 1000);
        double xA = xyValueArrayA.get(i).getX();
        double yA = xyValueArrayA.get(i).getY();
        xySeriesA.appendData(new DataPoint(xA,yA),true, 1000);
    }
        minY = Math.min(minYA,minYD);
        maxY = Math.max(maxYA,maxYD);
        createScatterPlot(count,1,maxY,minY);

    };

    private View.OnClickListener clickListenerBoutonsSeeData  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setContentView(R.layout.see_data);
            getDataFromTxt();

            mquestseedata = (Button) findViewById(R.id.seeDataToMenu);
            mquestseedata.setOnClickListener(clickListenerBoutonsMENU);


        }

        ;
    };


    //Method to clear the data saved in sharedpreferences
    public void clearpref()
    {
        SharedPreferences preferences = getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // When we click on a button, we call this function
                    // squizzmodifier to modify the final string that will be send
                    // savepref to save till the user sends, if he wants to change
                    RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                    int checkedIndex = group.indexOfChild(checkedRadioButton);
                    SQuizzModifier(getResources().getResourceEntryName(checkedId));
                    SavePreferences(Integer.toString(group.getId()), checkedIndex);
                    //
                    switch(checkedId) {
                            case R.id.q1_0_1:
                                dq1_0 = 1;
                                aq1_0 = 2 ;
                                break;
                            case R.id.q1_0_2:
                                dq1_0 = 1;
                                aq1_0 = 2  ;
                                break;
                            case R.id.q1_1_1:
                                dq1_1 = 1;
                                aq1_1 = 2 ;
                                break;
                            case R.id.q1_1_2:
                                dq1_1 = 1;
                                aq1_1 = 2 ;
                                break;
                            case R.id.q1_2_1:
                                dq1_2 = 1;
                                aq1_2 = 2  ;
                                break;
                            case R.id.q1_2_2:
                                dq1_2 = 1;
                                aq1_2 = 2 ;
                                break;
                            case R.id.q2_0_1:
                                dq2_0 = 1;
                                aq2_0 = 2  ;
                                break;
                            case R.id.q2_0_2:
                                dq2_0 = 1;
                                aq2_0 = 2 ;
                                break;
                            case R.id.q2_0_3:
                                dq2_0 = 1;
                                aq2_0 = 2 ;
                                break;
                            case R.id.q2_1_1:
                                dq2_1 = 1;
                                aq2_1 = 2 ;
                                break;
                            case R.id.q2_1_2:
                                dq2_1 = 1;
                                aq2_1 = 2 ;
                                break;
                            case R.id.q2_1_3:
                                dq2_1 = 1;
                                aq2_1 = 2 ;
                                break;
                            case R.id.q3_0_1:
                                dq3_0 = 1;
                                aq3_0 = 2 ;
                                break;
                            case R.id.q3_0_2:
                                dq3_0 = 1;
                                aq3_0 = 2 ;
                                break;
                            case R.id.q3_1_1:
                                dq3_1 = 1;
                                aq3_1 = 2  ;
                                break;
                            case R.id.q3_1_2:
                                dq3_1 = 1;
                                aq3_1 = 2 ;
                                break;
                    }
                }};

    private void SQuizzModifier(String name){
        String ar[] = name.split("_");
        String ar0[] = null;
        String ar1[] = null;
        int npage = Integer.parseInt(Character.toString(ar[0].charAt(1)));
        int nnumero = Integer.parseInt(ar[1]);
        int xpage0 = 0;
        int xnumero0=0;
        int xpage1 = 0;
        int xnumero1=0;
        if (squiz == null || squiz.isEmpty() ) {
            squiz = name;
            return;
        }
        if (!squiz.contains("\\$")) {
            ar0 = squiz.split("_");
            xpage0 = Integer.parseInt(Character.toString(ar0[0].charAt(1)));
            xnumero0 = Integer.parseInt(ar0[1]);
            if (xpage0>npage || (xpage0==npage && xnumero0>npage)){
                squiz = name+"$"+squiz;
                return;
            }
            if (xpage0<npage || (xpage0==npage && xnumero0<npage)){
                squiz = squiz+"$"+name;
                return;
            }
            squiz = name ;
            return;
        }
        String[] arr = squiz.split("\\$");
        int a = 0;
        for (int i = 0; i < arr.length-1; i++) {
            // accessing each element of array
            ar0 = arr[i].split("_");
            ar1 = arr[i+1].split("_");
            xpage0 = Integer.parseInt(Character.toString(ar0[0].charAt(1)));
            xnumero0 = Integer.parseInt(ar0[1]);
            xpage1 = Integer.parseInt(Character.toString(ar1[0].charAt(1)));
            xnumero1 = Integer.parseInt(ar1[1]);
            if (npage==xpage1 && nnumero==xnumero1) {
                arr[i + 1] = name;
                return;
            }
            if (npage<xpage0 || (npage==xpage0 && nnumero<xnumero0)) {
                squiz = name + "$" + squiz;
                return;
            }
            if (npage>xpage1 || (npage==xpage1 && nnumero>xnumero1)) {
                squiz = squiz + "$" + name;
                return;
            }
            if (npage == xpage0 && nnumero > xnumero0 || npage > xnumero0 && (npage < xpage1 || npage == xpage1 && nnumero < xnumero1)) {
                a = i;
                break;
            }
        }
        String[] arr1 =null;
        String[] arr2 =null;
        String[] arr3 =new String[arr.length+1];
        arr1 = Arrays.copyOfRange(arr, 0, a + 1);
        arr2 = Arrays.copyOfRange(arr, a + 1, arr.length);

        int pos = 0;
        for (String element : arr1) {
            arr3[pos] = element;
            pos++;
        }
        arr3[pos] = name;
        pos++;
        for (String element : arr2) {
            arr3[pos] = element;
            pos++;
        }

        squiz = String.join("$",arr3);
        return;

    }

    private void SavePreferences(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void LoadPreferences(String key,RadioGroup group){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(key, 0);
        RadioButton savedCheckedRadioButton = (RadioButton)group.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);
    }




    //We identify questions with a number at the end of the variable
    //Returns in fact the "returnstring" + the number we did have at the entrance
    //the int is used to upgrade the number at the end
    public static String getTheGoodShared(String enter,String returnstring,int plus) {
        int a = 0;
        String end = "";
        if (enter.equals(""))
            return returnstring + "1" ;
        for (int i = enter.length()-1;i >= 0;i--) {
            try {
                a = Integer.parseInt(String.valueOf(enter.charAt(i)));
                end = enter.charAt(i) + end;
            } catch (Exception e) {
                break;
            }
        }
        end = String.valueOf(Integer.parseInt(end)+plus);
        return returnstring+end;
    };

    public void stringToJson(String enter, int score_depression, int score_anxiety ,View v) throws JSONException, IOException {
        //We make a local save as well, the idea here is that we can print a graph with local data
        //to save the data and send it later, shared preferences are already used
        //we can use a json file, but txt should be enough for us
        String simpleFileName ="note.txt";
        String verySimpleFileName = "date.txt";
        //READ
        try {
            FileOutputStream create = this.openFileOutput(simpleFileName, MODE_APPEND);
            create.close();
            FileOutputStream create1 = this.openFileOutput(verySimpleFileName, MODE_APPEND);
            create.close();
            // Open stream to read file.
            FileInputStream in = this.openFileInput(simpleFileName);
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s = "";
            String data = "";
            while((s= br.readLine())!= null)  {
                data = s; }
            br.close();
            in.close();

            FileOutputStream out = this.openFileOutput(simpleFileName, MODE_APPEND);
            FileOutputStream out1 = this.openFileOutput(verySimpleFileName, MODE_APPEND);
            //
            if (data == "") {
                enter = "Data1" + "$" +enter +"$"+Integer.toString(score_anxiety)+"$"+Integer.toString(score_depression)+"\n" ;
                out.write(enter.getBytes());
                out1.write(("Data1$"+java.time.LocalDate.now()+"\n").getBytes());
                out.close();
                out1.close();
                Toast.makeText(v.getContext(), "File saved!", Toast.LENGTH_SHORT).show();
            }
            else    {
                String arr[] = data.split("\\$", 2);
                String firstWord = arr[0];
                enter = getTheGoodShared(firstWord, "Data", 1) + "$" + enter+"$"+Integer.toString(score_anxiety)+"$"+Integer.toString(score_depression)+"\n";
                out.write(enter.getBytes());
                out1.write((getTheGoodShared(firstWord, "Data", 1)+"$"+java.time.LocalDate.now()+"\n").getBytes());
                out.close();
                out1.close();
                Toast.makeText(v.getContext(), "File saved!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(v.getContext(),"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewID = 1;

        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

        mLogin.setOnClickListener(clickListenerBoutons);
        mRegister.setOnClickListener(clickListenerBoutons2);
    }



    @SuppressLint("StaticFieldLeak")
    private class SendMessage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {


                //Server and port adress
                client = new Socket("192.168.1.88", 3456);// connect to the server
                OutputStream outputStream = client.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                InputStream inputStream = client.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();

                // read the message from the server
                messager = dataInputStream.readUTF();

                dataOutputStream.close();
                dataInputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }   finally {
                try {
                    client.close();
                } catch (Exception ignored) {
                }
            }
            return null;
        }
    }


    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public class XYValue {

        private double x;
        private double y;

        public XYValue(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public String getTheDate(double itsX){
        String anotherSimpleFileName = "date.txt";
        FileInputStream in = null;
        String result = null;
        try {
            in = this.openFileInput(anotherSimpleFileName);
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s = "";
            String data = "";
            double count = 0;
            while( count != itsX)  {
                data = br.readLine();
                count ++;
            }
            br.close();
            in.close();
            result = data;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void createScatterPlot(final int maxX, final int minX, final int maxY, final int minY) {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");


        xySeriesD.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                /*
                Log.d(TAG, "onTap: You clicked on: (" + dataPoint.getX() +
                        "," + dataPoint.getY() + ")");
                //declare new series
                onClickSeries = new PointsGraphSeries<>();
                onClickSeries.appendData(new DataPoint(dataPoint.getX(),dataPoint.getY()),true, 100);
                onClickSeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
                onClickSeries.setColor(Color.RED);
                onClickSeries.setSize(25f);
                mScatterPlot.removeAllSeries();
                mScatterPlot.addSeries(onClickSeries);
                */

                toastMessage(getTheDate(dataPoint.getX()).split("\\$")[1]);
                /*
                createScatterPlot(maxX, minX, maxY, minY);
                 */
            }
        });

        xySeriesA.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                /*
                Log.d(TAG, "onTap: You clicked on: (" + dataPoint.getX() +
                        "," + dataPoint.getY() + ")");
                //declare new series
                onClickSeries = new PointsGraphSeries<>();
                onClickSeries.appendData(new DataPoint(dataPoint.getX(),dataPoint.getY()),true, 100);
                onClickSeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
                onClickSeries.setColor(Color.RED);
                onClickSeries.setSize(25f);
                mScatterPlot.removeAllSeries();
                mScatterPlot.addSeries(onClickSeries);
                 */
                toastMessage(getTheDate(dataPoint.getX()).split("\\$")[1]);
                /*
                createScatterPlot(maxX, minX, maxY, minY);
                */
            }
        });

        //set some properties
        xySeriesD.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeriesD.setColor(Color.BLUE);
        xySeriesD.setSize(20f);

        xySeriesA.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeriesA.setColor(Color.GREEN);
        xySeriesA.setSize(20f);
        /*
        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        */
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(maxY+5);
        mScatterPlot.getViewport().setMinY(minY-5);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(maxX);
        mScatterPlot.getViewport().setMinX(minX-1);

        mScatterPlot.addSeries(xySeriesD);
        mScatterPlot.addSeries(xySeriesA);

    }

    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");

        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}






