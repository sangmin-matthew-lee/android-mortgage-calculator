package edu.sjsu.andriod.example.project1sangminlee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private TextView interestRateText;
    private SeekBar interestRate;
    private RadioButton fifteen;
    private RadioButton twenty;
    private RadioButton thirty;
    private CheckBox taxBox;
    private Button calBtn;
    private Button uninstall;
    private TextView result;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.editTextNumberDecimal);
        interestRateText = findViewById(R.id.textView3);
        interestRate = findViewById(R.id.seekBar);
        fifteen = findViewById(R.id.radioButton2);
        twenty = findViewById(R.id.radioButton3);
        thirty = findViewById(R.id.radioButton4);
        taxBox = findViewById(R.id.checkBox);
        calBtn = findViewById(R.id.button);
        uninstall = findViewById(R.id.button2);
        result = findViewById(R.id.textView5);
        test = findViewById(R.id.textView6);

        //get interest rate
        interestRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                interestRateText.setText("Interest Rate: "+ (Float.toString((float)(progress)/10)) + "%");
            }
        });

        uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){

        //check userInput is empty
        if(userInput.getText().length() == 0) {
            result.setText("Please enter the principle.\nThen Press CALCULATE for monthly payments.");
        }else{  //userInput is not null

            String[] part = userInput.getText().toString().split("\\.");
            int digit = part[1].length();

            if( digit > 2){     //then check userInput has more than two decimal digits.
                result.setText("Please enter a valid number. 2 decimal digits max.\nThen Press CALCULATE for monthly payments");
            }else{

                //get interest rate
                float interest = interestRate.getProgress();
                interest/=10;

                //get loan term
                int loanTerm = 0;
                if(fifteen.isChecked()){
                   loanTerm = 15;
                }
                else if(twenty.isChecked()){
                    loanTerm = 20;
                }else if(thirty.isChecked()){
                    loanTerm =30;
                }

                //check checkbox.
                float taxAndIns = 0;
                float calculation = 0;
                float mortgage = 0;
                float principle = Float.parseFloat(userInput.getText().toString());     //P
                int numOfMonth = loanTerm*12;       //N
                float monthlyInterest = (interest)/1200;        //J
                if(taxBox.isChecked()){
                    taxAndIns = principle * (float)0.001;       //T
                }

                //calculate
                if(interest == 0){
                    calculation = (principle / numOfMonth) + taxAndIns;
                }
                else{
                    float coke = (float)Math.pow( (1+monthlyInterest), -numOfMonth);
                    calculation = ( (principle*monthlyInterest) / (1-coke) ) + taxAndIns;
                }
                mortgage = (float) Math.round( (calculation)*100) / 100f;

                result.setText("Montyly Payment $:" + mortgage);
                //calculate 2 cases

            }//else-digit check
        }//else-userInput is not null


    }//onClick

}   //class