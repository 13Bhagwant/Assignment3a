package com.zybooks.pizzaparty.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zybooks.pizzaparty.R;
import com.zybooks.pizzaparty.databinding.ActivityMainBinding;
import com.zybooks.pizzaparty.model.PizzaCalculator;
import com.zybooks.pizzaparty.viewmodel.PizzaPartyViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
    private RadioGroup mHowHungryRadioGroup;

    PizzaPartyViewModel viewModel = new PizzaPartyViewModel()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

         ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
         binding.setViewModel(viewModel);
         binding.setLifecycleOwner(this);
        // Assign the widgets to class variables
        mNumAttendEditText = findViewById(R.id.attendEditText);
        mNumPizzasTextView = findViewById(R.id.answerTextView);
        mHowHungryRadioGroup = findViewById(R.id.hungryRadioGroup);
    }

    public void calculateClick(View view) {

        // Get how many are attending the party
        int numAttend;
        try {
            String numAttendStr = mNumAttendEditText.getText().toString();
            numAttend = Integer.parseInt(numAttendStr);
        }
        catch (NumberFormatException ex) {
            numAttend = 0;
        }

        // Get hunger level selection
        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
        if (checkedId == R.id.lightRadioButton) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        }
        else if (checkedId == R.id.mediumRadioButton) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }

        // Show the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();
        String totalText = getString(R.string.total_pizzas, totalPizzas);
        mNumPizzasTextView.setText(totalText);
    }
}
