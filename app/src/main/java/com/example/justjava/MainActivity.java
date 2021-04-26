package com.example.justjava;
    // Importacions de librerias
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;

    // Clase principal
public class MainActivity extends AppCompatActivity {

    int quantity=1; // Declaracion de la variabe principal quantity

    @Override
    // Metodo onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Metodo para incrementar la cantidad de cafes que se quieren comprar
    public void increment(View view){
        if (quantity == 10) { // if para limitar la cantida maxima de cafes por ordenar
            Toast.makeText(this, "You cannot have more than 10 coffes", Toast.LENGTH_SHORT).show(); // Mensaje que se muestra si se pasa el limite
            return;
        }
        quantity = quantity +1; // Actualizar el valor de quantity mas 1
        displayQuantity(quantity); // Mostrar cantidad en pantalla
    }

    // Metodo para decrementar la cantidad  de cafes que se quieren comprar
    public void decrement(View view){
        if (quantity == 1) { // if para limitar la cantida minima de cafes por ordenar
            Toast.makeText(this, "You cannot have less than 1 coffes", Toast.LENGTH_SHORT).show(); // Mensaje que se muestra si se pasa el limite
            return;
        }
        quantity = quantity -1; // Actualizar el valor de quantity menos 1
        displayQuantity(quantity); // Mostrar cantidad en pantalla
    }

    // Metodo llamado al hacer clic en el boton Order
    public void submitOrder(View view) {
        // SEagrega y extrae el texto
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        // Averigüe si el usuario quiere crema batida
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        // Averiguar si el usuario quiere cobertura de chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calcular el precio
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Mostrar el resumen del pedido en la pantalla
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for: "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    // Metodo que genera el precio total de los cafes
    private int calculatePrice (boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5; //Declara variable con precio inicial

        if (addWhippedCream){  // if para ver si el checkbox WhippedCream fue seleccionado o no
            basePrice = basePrice + 1; // Si esto es asi se le suma 1 a la variable basePrice
        }

        if (addChocolate){ // if para ver si el checkbox Chocolate fue seleccionado o no
            basePrice = basePrice + 2; // Si esto es asi se le suma 2 a la variable basePrice
        }

        return quantity * basePrice; // Regresa la cantidad de cafes por el precio
    }

    // Metodo que crea mensaje para mostrar despues de ordenar
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
       String priceMessage = "Name: " + name; // Se crea el mensaje nombre + el nombre
        priceMessage += "\nAdd whipped cream? " + addWhippedCream; // Se crea el mensaje Add whipped cream? mas true o fallse
        priceMessage += "\nAdd whipped chocolate? " + addChocolate; // Se crea el mensaje Add chocolate? mas true o fallse
        priceMessage = priceMessage + "\nQuantity: "+ quantity; // Se crea el mensaje Quantity mas la cantidad de cafes
        priceMessage = priceMessage + "\nTotal: $ "+ price; // Se crea el mensaje Total: mas el precio tatal calculado anteriormente
        priceMessage = priceMessage + "\nthank you!"; // Se crea el mensaje Thank You para mostrarlo
        return priceMessage;
    }



    // Metodo que muestra la cantidad de cafes
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    // Metodo que muestra el precio de los cafes pedidos
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    // Metodo que muestra el mensaje de la orden despues de ordenar
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
}

// Creado por Axel Ruiz