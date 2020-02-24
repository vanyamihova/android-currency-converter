package eu.vanyamihova.currencyconverter;

import android.widget.EditText;

/**
 * @author Vanya Mihova <vanya.mihova89@gmail.com>
 * @since 23.02.2020
 */
final class ViewValueExtractor {


    private ViewValueExtractableListener viewValueExtractableListener;

    ViewValueExtractor(ViewValueExtractableListener viewValueExtractableListener) {
        this.viewValueExtractableListener = viewValueExtractableListener;
    }

    String getRequiredStringField(EditText editText) {
        String value = editText.getText().toString();
        if (value.isEmpty()) {
            viewValueExtractableListener.onValidationError("Има празни полета!");
        }
        return value;
    }

    Double getRequiredAmountField(EditText editText) {
        String value = editText.getText().toString();
        if (value.isEmpty()) {
            viewValueExtractableListener.onValidationError("Има празни полета!");
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            viewValueExtractableListener.onValidationError("Има невалидни данни в полетата!");
        }
        return null;
    }

}
