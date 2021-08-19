//====================================================================
//
// Application: Bike Bistro food order application
// Activity:    ActDelivery
// Description:
//   This app allows customers order food from Bike Bistro
//
//====================================================================
package com.example.bikebistro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//--------------------------------------------------------------------
// class ActDelivery
//--------------------------------------------------------------------

public class ActDelivery extends AppCompatActivity {

    // Declare control variables
    private Toolbar tbrDelivery;
    private SeekBar sbDistance;
    private EditText etCustomerName;
    private EditText etDeliveryAddress;
    private EditText etPhoneNUmber;
    private EditText etSubtotalFromActMeal;
    private EditText etTax;
    private EditText etDistance;
    private EditText etDeliveryCost;
    private EditText etTip;
    private EditText etTotalCost;
    private EditText etETA;
    public static final double TAX_RATE = 0.06;
    public static final double DELIVERY_COST1 = 3.0;
    public static final double DELIVERY_COST2 = 5.0;
    public static final String DOLLAR_FORMAT = "%1.2f";
    public double tax;
    public double totalCost;
    public double subtotalCost;
    public double deliveryCostTemp=0;
    public double tipCost;
    public double tipCostTemp=0;
    public final String STRING_NAME="customer name";
    public final String STRING_ADDRESS="customer address";
    public final String STRING_PHONE="customer phone";
    public final String STRING_DISTANCE="customer distance";
    public final String STRING_TIP="customer tip";
    public final String STRING_ETA="delivery time";
    public final String STRING_NAME_DEFAULT="not set";
    public final String STRING_ADDRESS_DEFAULT="not set";
    public final String STRING_PHONE_DEFAULT="not set";
    public final String STRING_DISTANCE_DEFAULT="not set";
    public final String STRING_TIP_DEFAULT="not set";
    public final String STRING_ETA_DEFAULT="not set";
    public String entreeName;
    public String drinkName;
    public String dessertName;

    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laydelivery);

        // Define and connect to toolbar
        tbrDelivery = findViewById(R.id.tbrDelivery);
        setSupportActionBar(tbrDelivery);
        tbrDelivery.setNavigationIcon(R.mipmap.ic_launcher_new);

        //Define controls
        etCustomerName = (EditText)findViewById(R.id.edCustomerName);
        etDeliveryAddress = (EditText)findViewById(R.id.etDeliveryAddress);
        etPhoneNUmber = (EditText)findViewById(R.id.etPhoneNUmber);
        etSubtotalFromActMeal = (EditText)findViewById(R.id.etSubtotalFromActMeal);
        etTax = (EditText)findViewById(R.id.etTax);
        sbDistance = (SeekBar)findViewById(R.id.sbDistance);
        etDistance = (EditText)findViewById(R.id.etDistance);
        etDeliveryCost =(EditText)findViewById(R.id.etDeliveryCost);
        etTip = (EditText)findViewById(R.id.etTip);
        etTotalCost= (EditText)findViewById(R.id.etTotalCost);
        etETA = (EditText)findViewById(R.id.etETA);

        //Receive data sent from calling activity
        Bundle extras = getIntent().getExtras();
        String s = extras.getString("stringValue");
        entreeName = extras.getString("stringEntree");
        drinkName = extras.getString("stringDrink");
        dessertName = extras.getString("stringDessert");
        etSubtotalFromActMeal.setText(s);

        //Calculate tax cost
        subtotalCost = Double.parseDouble(s);
        tax = subtotalCost*TAX_RATE;
        etTax.setText(String.format(DOLLAR_FORMAT,tax));

        //Adding subtotal and tax to total cost
        double subtotalAndTax = subtotalCost + tax;
        etTotalCost.setText(String.format(DOLLAR_FORMAT, subtotalAndTax));


        //Define seek bar
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etDistance.setText(String.valueOf(progress));

                //Calculate delivery cost
                double distanceDouble = Double.parseDouble(etDistance.getText().toString());
                double deliveryCost=0;
                if(distanceDouble<= 10)
                    deliveryCost = DELIVERY_COST1;
                if(distanceDouble>10)
                    deliveryCost = DELIVERY_COST2;

                etDeliveryCost.setText(Double.toString(deliveryCost));

                //Adding delivery cost to total cost
                totalCost = Double.parseDouble(etTotalCost.getText().toString());
                if(deliveryCostTemp==0)
                    totalCost += deliveryCost;
                else
                    totalCost = totalCost-deliveryCostTemp +deliveryCost;
                etTotalCost.setText(String.format(DOLLAR_FORMAT,totalCost));
                deliveryCostTemp = deliveryCost;

                //Adding tips to total cost
                etTip.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tipCost = Double.parseDouble(etTip.getText().toString());
                        totalCost = Double.parseDouble(etTotalCost.getText().toString());
                        if(tipCostTemp==0)
                            totalCost += tipCost;
                        else
                            totalCost = totalCost-tipCostTemp+tipCost;
                        etTotalCost.setText(String.format(DOLLAR_FORMAT, totalCost));
                        tipCostTemp= tipCost;

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                //Calculate ETA time
                double deliveryTime = distanceDouble*2;
                etETA.setText(Double.toString(deliveryTime));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //----------------------------------------------------------------
    // Recall delivery
    //----------------------------------------------------------------

    public void recallDelivery(View v)
    {
        //prompt user for recall order
        AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
        builder.setTitle("Recall Delivery Order");
        builder.setMessage("Do you want to recall your last delivery order?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //Recall order
            SharedPreferences settings;
            settings = getPreferences(MODE_PRIVATE);
            etCustomerName.setText(settings.getString(STRING_NAME, STRING_NAME_DEFAULT));
            etDeliveryAddress.setText(settings.getString(STRING_ADDRESS, STRING_ADDRESS_DEFAULT));
            etPhoneNUmber.setText(settings.getString(STRING_PHONE,STRING_PHONE_DEFAULT));
            etDistance.setText(settings.getString(STRING_DISTANCE, STRING_DISTANCE_DEFAULT));
            etTip.setText((settings.getString(STRING_TIP, STRING_TIP_DEFAULT)));
            etETA.setText(settings.getString(STRING_ETA,STRING_ETA_DEFAULT));
        });
        builder.setNegativeButton("No",(dialog, which) -> {

        });
        builder.show();

    }

    //----------------------------------------------------------------
    // Reset delivery
    //----------------------------------------------------------------

    public void resetDelivery(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Reset Delivery Information");
        builder.setMessage("Are you sure you want to reset delivery information");
        builder.setPositiveButton("Yes", (dialog, which) -> {
           //Clear delivery information
           etPhoneNUmber.setText("");
           etCustomerName.setText("");
           etDeliveryAddress.setText("");
           etTip.setText("");
           etDistance.setText("");
           etDeliveryCost.setText("");
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.show();
    }

    //----------------------------------------------------------------
    // Submit delivery
    //----------------------------------------------------------------

    public void submitDelivery(View v)
    {
        //Save value to shared preferences
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //Save value in shared preferences
        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(STRING_NAME, etCustomerName.getText().toString());
        editor.putString(STRING_ADDRESS,etDeliveryAddress.getText().toString());
        editor.putString(STRING_PHONE, etPhoneNUmber.getText().toString());
        editor.putString(STRING_DISTANCE, etDistance.getText().toString());
        editor.putString(STRING_TIP, etTip.getText().toString());
        editor.putString(STRING_ETA, etETA.getText().toString());
        editor.commit();

        //Show order summary
        //dialog box show name, address, phone, three selections, subtotal, tax, distance, delivery charge, tip, total, and ETA
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Order Confirmation");
        builder.setMessage("Customer Name: "+ etCustomerName.getText().toString()+
                "\nCustomer Address: "+etDeliveryAddress.getText().toString()+
                "\nPhone Number: "+ etPhoneNUmber.getText().toString()+
                "\nEntree: "+ entreeName+
                "\nDrinks: "+ drinkName+
                "\nDessert: "+ dessertName+
                "\nSubtotal ($): "+ etSubtotalFromActMeal.getText().toString()+
                "\nTax ($): "+ etTax.getText().toString()+
                "\nDelivery Distance (miles): "+ etDistance.getText().toString()+
                "\nDelivery Cost($): "+ etDeliveryCost.getText().toString()+
                "\nTip ($): "+etTip.getText().toString()+
                "\nTotal Cost ($): "+etTotalCost.getText().toString()+
                "\nEstimate Delivery Time (minutes): "+etETA.getText().toString());
        builder.show();
    }

}