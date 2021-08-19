//====================================================================
//
// Application: Bike Bistro food order application
// Activity:    ActMeal
// Description:
//   This app allows customers order food from Bike Bistro
//
//====================================================================
package com.example.bikebistro;

//Import packages
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ActMeal extends AppCompatActivity {

    //----------------------------------------------------------------
    //constants and variables
    //----------------------------------------------------------------
    public static final String APP_NAME = "Bike Bistro";
    private Toolbar tbrMain;
    private EditText etEntree;
    private EditText etDrink;
    private EditText etDessert;
    private EditText etSubtotal;
    private Spinner spEntree;
    private Spinner spDrink;
    private Spinner spDessert;
    public static final String DOLLAR_FORMAT = "%1.2f";
    public double entreeCost;
    public double drinkCost;
    public double dessertCost;
    public double subtotal;
    public final String STRING_ENTREE="entree cost";
    public final String STRING_DRINK="drink cost";
    public final String STRING_DESSERT="dessert cost";
    public final String STRING_ENTREE_DEFAULT = "not set";
    public final String STRING_DRINK_DEFAULT = "not set";
    public final String STRING_DESSERT_DEFAULT= "not set";
    public double entreeTemp = 0;
    public double drinkTemp = 0;
    public double dessertTemp = 0;
    public String entreeName;
    public String drinkName;
    public String dessertName;


    final String [][]menu = {
            {"(no selection)", "(no selection)", "(no selection)"},
            {"Entree", "Turkey Sandwich", "11.49"},
            {"Entree", "Bacon Sandwich", "11.49"},
            {"Entree", "Beef Hamburger", "10.49"},
            {"Entree", "Cesar Salad", "8.49"},
            {"Entree", "Chicken Rice", "10.49"},
            {"Drink", "Pepsi", "1.99"},
            {"Drink", "Orange Juice", "2.99"},
            {"Drink", "Apple Juice", "2.99"},
            {"Drink", "Beer", "3.99"},
            {"Drink", "Wine", "4.99"},
            {"Dessert", "Ice Cream Sundae", "4.79"},
            {"Dessert", "Black Forest Cake", "5.99"},
            {"Dessert", "Yellow Cake", "3.99"},
            {"Dessert", "Cherry Cake", "3.99"},
            {"Dessert", "Chocolate Pudding", "4.99"}};


    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymeal);


        // Define and connect to toolbar
        tbrMain = findViewById(R.id.tbrMain);
        setSupportActionBar(tbrMain);
        tbrMain.setNavigationIcon(R.mipmap.ic_launcher_new);

        //Define Controls
        etEntree = findViewById(R.id.etEntree);
        etDrink = findViewById((R.id.etDrink));
        etDessert = findViewById(R.id.etDessert);
        etSubtotal = findViewById(R.id.etSubtotal);
        spEntree = findViewById(R.id.spEntree);
        spDrink = findViewById(R.id.spDrink);
        spDessert = findViewById(R.id.spDessert);


        //Generate separate array for entree, drink, dessert
        ArrayList<String> alEntrees =  new ArrayList<>();
        alEntrees.add(menu[0][0]);
        for(int i= 1; i< 6; i++)
        {
            alEntrees.add(menu[i][1]);
        }

        ArrayList<String> alDrinks =  new ArrayList<>();
        alDrinks.add(menu[0][0]);
        for(int i= 6; i< 11; i++)
        {
            alDrinks.add(menu[i][1]);
        }

        ArrayList<String> alDesserts =  new ArrayList<>();
        alDesserts.add(menu[0][0]);
        for(int i= 11; i< 16; i++)
        {
            alDesserts.add(menu[i][1]);
        }

        //Define spinner
        ArrayAdapter<String> spAdapterEntree = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, alEntrees);
        spAdapterEntree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEntree.setAdapter(spAdapterEntree);
        spEntree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String p = "";
                String n = "";
                switch (position) {
                    case 0:
                        p = "0.00";
                        n = "not selected";
                        break;
                    case 1:
                        p = "11.49";
                        n = "Turkey Sandwich";
                        break;
                    case 2:
                        p = "10.49";
                        n = "Bacon Sandwich";
                        break;
                    case 3:
                        p = "10.49";
                        n = "Beef Hamburger";
                        break;
                    case 4:
                        p = "8.49";
                        n = "Cesar Salad";
                        break;
                    case 5:
                        p = "10.49";
                        n = "Chicken Rice";
                        break;
                }
                entreeName = n;
                etEntree.setText(p);
                entreeCost = Double.parseDouble(etEntree.getText().toString());
                if(entreeTemp == 0)
                    subtotal += entreeCost;
                else
                    subtotal = subtotal-entreeTemp+entreeCost;
                etSubtotal.setText(String.format(DOLLAR_FORMAT,subtotal));
                entreeTemp = Double.parseDouble(p);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> spAdapterDrink = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, alDrinks);
        spAdapterDrink.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDrink.setAdapter(spAdapterDrink);
        spDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p = "";
                String n = "";
                switch (position)
                {
                    case 0:
                        p = "0.00";
                        n = "not selected";
                        break;
                    case 1:
                        p = "1.99";
                        n = "Pepsi";
                        break;
                    case 2:
                        p = "2.99";
                        n = "Orange Juice";
                        break;
                    case 3:
                        p = "2.99";
                        n = "Apple Juice";
                        break;
                    case 4:
                        p = "3.99";
                        n = "Beer";
                        break;
                    case 5:
                        p = "4.99";
                        n = "Wine";
                        break;
                }
                drinkName = n;
                etDrink.setText(p);
                drinkCost = Double.parseDouble(etDrink.getText().toString());
                if(drinkTemp == 0)
                    subtotal += drinkCost;
                else
                    subtotal = subtotal-drinkTemp+drinkCost;
                etSubtotal.setText(String.format(DOLLAR_FORMAT,subtotal));
                drinkTemp = Double.parseDouble(p);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spAdapterDessert = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, alDesserts);
        spAdapterDessert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDessert.setAdapter(spAdapterDessert);
        spDessert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p = "";
                String n = "";
                switch (position)
                {
                    case 0:
                        p = "0.00";
                        n = "not selected";
                        break;
                    case 1:
                        p = "4.79";
                        n = "Ice Cream Sundae";
                        break;
                    case 2:
                        p = "5.99";
                        n = "Black Forest Cake";
                        break;
                    case 3:
                        p = "3.99";
                        n = "Yellow Cake";
                        break;
                    case 4:
                        p = "3.99";
                        n = "Cherry Cake";
                        break;
                    case 5:
                        p = "4.99";
                        n = "Chocolate Pudding";
                        break;
                }
                dessertName = n;
                etDessert.setText(p);
                dessertCost = Double.parseDouble(etDessert.getText().toString());
                if(dessertTemp == 0)
                    subtotal += dessertCost;
                else
                    subtotal = subtotal-dessertTemp+dessertCost;
                etSubtotal.setText(String.format(DOLLAR_FORMAT,subtotal));
                dessertTemp = Double.parseDouble(p);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //----------------------------------------------------------------
    // Recall order
    //----------------------------------------------------------------

    public void recallOrder(View v)
    {
        //prompt user for recall order
        AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
        builder.setTitle("Recall Order");
        builder.setMessage("Do you want to recall your last order?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
           //Recall order
            SharedPreferences settings;
            settings = getPreferences(MODE_PRIVATE);
            etEntree.setText(settings.getString(STRING_ENTREE, STRING_ENTREE_DEFAULT));
            etDrink.setText(settings.getString(STRING_DRINK, STRING_DRINK_DEFAULT));
            etDessert.setText(settings.getString(STRING_DESSERT,STRING_DESSERT_DEFAULT));
        });
        builder.setNegativeButton("No",(dialog, which) -> {

        });
        builder.show();
    }

    //----------------------------------------------------------------
    // Reset order
    //----------------------------------------------------------------

    public void resetOrder(View v)
    {
        //Prompt user for order reset
        AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
        builder.setTitle("Reset Order");
        builder.setMessage("Are you sure you want to clear this order?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //Clear order
            etEntree.setText("0.00");
            etDrink.setText("0.00");
            etDessert.setText("0.00");
            etSubtotal.setText("0.00");
            etSubtotal.setText("0.00");
            subtotal=0.00;
            spEntree.setSelection(0);
            spDrink.setSelection(0);
            spDessert.setSelection(0);
            entreeTemp=0;
            drinkTemp=0;
            dessertTemp=0;
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.show();
    }

    //----------------------------------------------------------------
    // Delivery order
    //----------------------------------------------------------------

    public void deliveryOrder(View v)
    {
        //Shared preference
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //Save value in shared preferences
        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(STRING_ENTREE, etEntree.getText().toString());
        editor.putString(STRING_DRINK,etDrink.getText().toString());
        editor.putString(STRING_DESSERT, etDessert.getText().toString());
        editor.commit();


        //Prepare data and open ActDelivery screen
        Intent intent = new Intent(getApplicationContext(),ActDelivery.class);
        intent.putExtra("stringValue",etSubtotal.getText().toString());
        intent.putExtra("stringEntree",entreeName);
        intent.putExtra("stringDrink",drinkName);
        intent.putExtra("stringDessert",dessertName);
        startActivity(intent);


    }
}