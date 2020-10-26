package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.ClassNotFoundException;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;
import bluej.extensions.event.*;
import bluej.extensions.painter.ExtensionClassTargetPainter;
import org.graalvm.compiler.java.BciBlockMapping;
import org.w3c.dom.Text;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class EditorFinder {

    LinkedList<BClass> toHandle;

    EditorFinder(BlueJ blueJ){
        toHandle = new LinkedList();
        blueJ.addClassListener(new ClassListener() {
            @Override
            public void classStateChanged(ClassEvent classEvent) {
                handleClassStateChange(classEvent);
            }
        });
    }

    void handleClassStateChange(ClassEvent classEvent){
        try {
            if (toHandle.size() != 0){
                for (BClass item:toHandle){
                    if (classEvent.getBClass().equals(item)){
                        handleNewClass(item);
                    }
                }
            }
            BClass[] classes = classEvent.getPackage().getClasses();

            boolean isNew = true;

            for (BClass bClass:classes){
                if (bClass.getName().equals(classEvent.getBClass().getName()))
                    isNew = false;
            }
            if (isNew){
                toHandle.add(classEvent.getBClass());
            }
        }
         catch (ProjectNotOpenException | PackageNotFoundException e) { }
    }

    void handleNewClass(BClass bClass){
        try {
            Editor e = bClass.getEditor();
            String bClassName = bClass.getName();
            int lineCount = e.getLineCount() -1;
            int lineLength = e.getLineLength(e.getLineCount() -1);
            String text = e.getText(new TextLocation(0, 0), new TextLocation(e.getLineCount() -1, e.getLineLength(e.getLineCount() -1)));

            //abstract class
            if (text.contains("abstract")){
                e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLength), "abstract class " + bClassName + "{" + "\n    " + bClassName + "(){" + "\n    " + "        " + "\n    " + "}" + "\n" + "}");
                toHandle.clear();
            }

            //interface
            if (text.contains("interface")){
                e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLength), "interface " + bClassName + "{" + "\n    " + "\n" + "}");
                toHandle.clear();
            }

            //enum
            if (text.contains("enum")){
                e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLength), "enum " + bClassName + "{" + "\n    " + "\n" + "}");
                toHandle.clear();
            }

            //normal class
            else if (!text.contains("unit")){
                e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLength), "class " + bClassName + "{" + "\n    " + bClassName + "(){" + "\n    " + "        " + "\n    " + "}" + "\n" + "}");
                toHandle.clear();
            }



        } catch (ProjectNotOpenException | PackageNotFoundException projectNotOpenException) { }
    }
}