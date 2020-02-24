package eu.vanyamihova.currencyconverter;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * @author Vanya Mihova <vanya.mihova89@gmail.com>
 * @since 23.02.2020
 */
final class CurrencyConverterModel {

    private Double rate;
    private Double amount;
    private String symbol;
    private DecimalFormat formatter = new DecimalFormat("0.00");

    void populate(Double rate, Double amount, String symbol) {
        this.rate = rate;
        this.symbol = symbol;
        this.amount = amount;
    }

    Optional<String> getResult() {
        if (rate == null || amount == null || symbol == null || symbol.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(String.format("%s %s", formatter.format(rate * amount), symbol));
    }

}
