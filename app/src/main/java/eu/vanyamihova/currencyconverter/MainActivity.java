package eu.vanyamihova.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

/**
 * @author Vanya Mihova <vanya.mihova89@gmail.com>
 * @since 23.02.2020
 */
public class MainActivity extends AppCompatActivity implements ViewValueExtractableListener {

    private ViewValueExtractor viewValueExtractor;
    private CurrencyConverterModel currencyConverterModel;
    private CurrencyRepository currencyRepository;

    private EditText rateEditText;
    private EditText symbolEditText;
    private EditText amountEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyRepository = new CurrencyRepository(this);
        viewValueExtractor = new ViewValueExtractor(this);
        currencyConverterModel = new CurrencyConverterModel();

        setupView();
        loadDataFromStorage();
    }

    private void setupView() {
        rateEditText = findViewById(R.id.rate);
        symbolEditText = findViewById(R.id.symbol);
        amountEditText = findViewById(R.id.amount);
        resultTextView = findViewById(R.id.result);
        Button submitButton = findViewById(R.id.submit);
        Button resetButton = findViewById(R.id.reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractValues();
                buildResult();
            }
        });
    }

    private void loadDataFromStorage() {
        Double rate = currencyRepository.getRate();
        Double amount = currencyRepository.getAmount();
        String symbol = currencyRepository.getSymbol();

        if (rate == null || amount == null || symbol == null) {
            return;
        }
        rateEditText.setText(String.valueOf(rate));
        amountEditText.setText(String.valueOf(amount));
        symbolEditText.setText(symbol);
        currencyConverterModel.populate(rate, amount, symbol);
        buildResult();
    }

    private void extractValues() {
        resetResultMessage();
        Double rate = viewValueExtractor.getRequiredAmountField(rateEditText);
        Double amount = viewValueExtractor.getRequiredAmountField(amountEditText);
        String symbol = viewValueExtractor.getRequiredStringField(symbolEditText);
        currencyConverterModel.populate(rate, amount, symbol);
        currencyRepository.save(rate, amount, symbol);
    }

    @Override
    public void onValidationError(String message) {
        resultTextView.setTextColor(getColor(R.color.colorErrorMessage));
        resultTextView.setText(message);
    }

    private void buildResult() {
        Optional<String> result = currencyConverterModel.getResult();
        if (result.isPresent()) {
            resultTextView.setText(String.format("Резултат: %s", result.get()));
        }
    }

    private void resetResultMessage() {
        resultTextView.setTextColor(getColor(R.color.colorPrimary));
        resultTextView.setText("Резултат:");
    }

    private void resetFields() {
        rateEditText.setText("");
        amountEditText.setText("");
        symbolEditText.setText("");
        resetResultMessage();
        currencyRepository.reset();
    }

}
