package de.aensin.helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;


/**
 * Die kLasse verhindert falsche Einträge beim Einfügen oder Updaten der Fitnessdaten
 * es können bur nummerische Werte in die Textfelder eingetragen  werden
 */
public class DoubleDokument extends PlainDocument {

    @Override
    public void insertString(int offset, String s, AttributeSet attributSet)
        throws BadLocationException{
        if(s.matches("^+(\\d*\\.?\\d*)"))
        {
            super.insertString(offset,s,attributSet);
        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
