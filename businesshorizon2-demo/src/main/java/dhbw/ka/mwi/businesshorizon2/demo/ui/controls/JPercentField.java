package dhbw.ka.mwi.businesshorizon2.demo.ui.controls;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.Position.Bias;
import java.text.NumberFormat;
import java.text.ParseException;

//Angepasst von http://stackoverflow.com/a/7599686
public class JPercentField extends JSpinner {


    public JPercentField() {
        setModel(new SpinnerNumberModel(0d, 0d, 1d, 0.01d));
        initSpinnerTextField();
    }

    private void initSpinnerTextField() {
        final DocumentFilter digitOnlyFilter = new PercentDocumentFilter(getMaximumDigits());
        final NavigationFilter navigationFilter = new BlockLastCharacterNavigationFilter(getTextField());
        getTextField().setFormatterFactory(
                new DefaultFormatterFactory(new PercentNumberFormatter(createPercentFormat(), navigationFilter,
                        digitOnlyFilter)));
        //getTextField().setColumns(6);
    }

    private static int getMaximumDigits() {
        return Integer.toString(100).length() + 2;
    }

    private JFormattedTextField getTextField() {
        final JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor) getEditor();
        return jsEditor.getTextField();
    }

    private static NumberFormat createPercentFormat() {
        final NumberFormat format = NumberFormat.getPercentInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(getMaximumDigits());
        format.setMaximumFractionDigits(2);
        return format;
    }

    private static final class PercentNumberFormatter extends NumberFormatter {

        private final NavigationFilter navigationFilter;
        private final DocumentFilter digitOnlyFilter;

        private PercentNumberFormatter(final NumberFormat format, final NavigationFilter navigationFilter,
                                       final DocumentFilter digitOnlyFilter) {
            super(format);
            this.navigationFilter = navigationFilter;
            this.digitOnlyFilter = digitOnlyFilter;
        }

        @Override
        protected NavigationFilter getNavigationFilter() {
            return navigationFilter;
        }

        @Override
        protected DocumentFilter getDocumentFilter() {
            return digitOnlyFilter;
        }

        @Override
        public Class<?> getValueClass() {
            return Double.class;
        }

        @Override
        public Object stringToValue(final String text) throws ParseException {
            final Double value = (Double) super.stringToValue(text);
            return Math.max(0, Math.min(1, value));
        }
    }

    /**
     * NavigationFilter that avoids navigating beyond the percent sign.
     */
    private static final class BlockLastCharacterNavigationFilter extends NavigationFilter {

        private final JFormattedTextField textField;

        private BlockLastCharacterNavigationFilter(final JFormattedTextField textField) {
            this.textField = textField;
        }

        @Override
        public void setDot(final FilterBypass fb, final int dot, final Bias bias) {
            super.setDot(fb, correctDot(dot), bias);
        }

        @Override
        public void moveDot(final FilterBypass fb, final int dot, final Bias bias) {
            super.moveDot(fb, correctDot(dot), bias);
        }

        private int correctDot(final int dot) {
            // Avoid selecting the percent sign
            final int lastDot = Math.max(0, textField.getText().length() - 1);
            return dot > lastDot ? lastDot : dot;
        }
    }

    private static final class PercentDocumentFilter extends DocumentFilter {

        private final int maxiumDigits;

        private PercentDocumentFilter(final int maxiumDigits) {
            this.maxiumDigits = maxiumDigits;
        }

        @Override
        public void insertString(final FilterBypass fb, final int offset, final String text, final AttributeSet attrs)
                throws BadLocationException {
            // Mapping an insert as a replace without removing
            replace(fb, offset, 0, text, attrs);
        }

        @Override
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            // Mapping a remove as a replace without inserting
            replace(fb, offset, length, "", SimpleAttributeSet.EMPTY);
        }

        @Override
        public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs)
                throws BadLocationException {
            final int replaceLength = correctReplaceLength(fb, offset, length);
            final String cleanInput = truncateInputString(fb, filterDigits(text), replaceLength);
            super.replace(fb, offset, replaceLength, cleanInput, attrs);
        }

        /**
         * Removes all non-digit characters
         */
        private static String filterDigits(final String text) {
            final StringBuilder sb = new StringBuilder(text);
            for (int i = 0, n = sb.length(); i < n; i++) {
                if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != ',') {
                    sb.deleteCharAt(i);
                }
            }
            return sb.toString();
        }

        /**
         * Removes all characters with which the resulting text would exceed the maximum number of digits
         */
        private String truncateInputString(final FilterBypass fb, final String filterDigits, final int replaceLength) {
            final StringBuilder sb = new StringBuilder(filterDigits);
            final int currentTextLength = fb.getDocument().getLength() - replaceLength - 1;
            for (int i = 0; i < sb.length() && currentTextLength + sb.length() > maxiumDigits; i++) {
                sb.deleteCharAt(i);
            }
            return sb.toString();
        }

        private static int correctReplaceLength(final FilterBypass fb, final int offset, final int length) {
            if (offset + length >= fb.getDocument().getLength()) {
                // Don't delete the percent sign
                return offset + length - fb.getDocument().getLength();
            }
            return length;
        }
    }

}