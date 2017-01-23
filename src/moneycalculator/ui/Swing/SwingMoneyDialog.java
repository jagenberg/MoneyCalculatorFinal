package moneycalculator.ui.Swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.ui.MoneyDialog;


public class SwingMoneyDialog extends JPanel implements MoneyDialog{

    private String amount;
    private Currency currency;
    private final Currency[] currencies;
    

    public SwingMoneyDialog(Currency[] currencies){
        this.currencies = currencies;
        this.add(amount());
        this.add(currency());
        
    }
    
    @Override
    public Money get() {
        return new Money(Double.parseDouble(amount), currency);
    }

    private Component amount() {
        final JTextField field = new JTextField("100");
        field.setColumns(10);
        field.getDocument().addDocumentListener(amountChanged());
        amount = field.getText();
        return field;
    }

    private Component currency() {
        JComboBox combo = new JComboBox(currencies);
        combo.addItemListener(CurrencyChanged());
        currency=(Currency) combo.getSelectedItem();
        return combo;
    }

    private ItemListener CurrencyChanged() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(ItemEvent.DESELECTED == e.getStateChange())return;
                currency = (Currency) e.getItem();
            }
        };
    }

    private DocumentListener amountChanged() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            private void amountChanged(Document document) {
                try{
                    amount = document.getText(0,document.getLength());
                }                
                catch(BadLocationException ex){
                    Logger.getLogger(SwingMoneyDialog.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        };
    }
}
