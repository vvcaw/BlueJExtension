package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.ClassNotFoundException;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;
import bluej.extensions.event.*;
import bluej.extensions.painter.ExtensionClassTargetPainter;
import org.graalvm.compiler.java.BciBlockMapping;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class EditorFinder {

    EditorFinder(BlueJ blueJ) {
        blueJ.addClassListener(new ClassListener() {
            @Override
            public void classStateChanged(ClassEvent classEvent) {
                handleClassStateChange(classEvent);
            }
        });
    }

    void handleClassStateChange(ClassEvent classEvent){

        try {
            Editor e = classEvent.getBClass().getEditor();
            String bClassName = classEvent.getBClass().getName();
            int lineCount = e.getLineCount() -1;
            int lineLenght = e.getLineLength(e.getLineCount() -1);
            String text = e.getText(new TextLocation(0, 0), new TextLocation(e.getLineCount() -1, e.getLineLength(e.getLineCount() -1)));

            //Check if it really is a new class
            if(text.contains("/**") && text.contains("@author") && text.contains("@version")){
                //Check which kind of class it is

                //abstract class
                if(text.contains("abstract")){
                    e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLenght), "abstract class " + bClassName + "{" + "\n    " + bClassName + "(){" + "\n    " + "        " + "\n    " + "}" + "\n" + "}");
                }

                //interface
                if(text.contains("interface")){
                    e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLenght), "interface " + bClassName + "{" + "\n    " + "\n" + "}");
                }

                //enum
                if(text.contains("enum")){
                    e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLenght), "enum " + bClassName + "{" + "\n    " + "\n" + "}");
                }

                //normal class
                else if(text.contains("// Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen")){
                    e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLenght), "class " + bClassName + "{" + "\n    " + bClassName + "(){" + "\n    " + "        " + "\n    " + "}" + "\n" + "}");
                }
            }
        } catch (ProjectNotOpenException | PackageNotFoundException e) {
            e.printStackTrace();
        }
    }
}