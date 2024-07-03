package de.aensin.helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * prüft Eingabefelder auf Integer, und erlaubt nur diese
 */
public class IntegerDokument extends PlainDocument {

    @Override
    public void insertString(int offset, String s, AttributeSet attributSet)
            throws BadLocationException {
        if(s.matches("^\\d+"))
        {
            super.insertString(offset,s,attributSet);
        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
