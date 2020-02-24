package eu.vanyamihova.currencyconverter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Vanya Mihova <vanya.mihova89@gmail.com>
 * @since 23.02.2020
 */
final class CurrencyRepository {

    private static final String CURRENCY_STORAGE = "eu.vanyamihova.currencyconverter.currency";
    private static final String CURRENCY_RATE = "eu.vanyamihova.currencyconverter.currency.rate";
    private static final String CURRENCY_AMOUNT = "eu.vanyamihova.currencyconverter.currency.amount";
    private static final String CURRENCY_SYMBOL = "eu.vanyamihova.currencyconverter.currency.symbol";
    private SharedPreferences sharedPreferences;

    CurrencyRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(CURRENCY_STORAGE, Context.MODE_PRIVATE);
    }

    void save(Double rate, Double amount, String symbol) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENCY_RATE, String.valueOf(rate));
        editor.putString(CURRENCY_AMOUNT, String.valueOf(amount));
        editor.putString(CURRENCY_SYMBOL, symbol);
        editor.apply();
    }

    void reset() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENCY_RATE, null);
        editor.putString(CURRENCY_AMOUNT, null);
        editor.putString(CURRENCY_SYMBOL, null);
        editor.apply();
    }

    String getSymbol() {
        return sharedPreferences.getString(CURRENCY_SYMBOL, null);
    }

    Double getRate() {
        try {
            String value = sharedPreferences.getString(CURRENCY_RATE, null);
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    Double getAmount() {
        try {
            String value = sharedPreferences.getString(CURRENCY_AMOUNT, null);
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

}
