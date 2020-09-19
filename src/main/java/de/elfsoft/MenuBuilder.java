package de.elfsoft;

import bluej.extensions.BClass;
import bluej.extensions.BObject;
import bluej.extensions.BPackage;
import bluej.extensions.MenuGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBuilder extends MenuGenerator {
    private BPackage curPackage;
    private BClass curClass;
    private BObject curObject;

    public JMenuItem getToolsMenuItem(BPackage aPackage)
    {
        return new JMenuItem(new SimpleAction("Click Tools", "Tools menu:"));
    }

    public JMenuItem getClassMenuItem(BClass aClass)
    {
        return new JMenuItem(new SimpleAction("Click Class", "Class menu:"));
    }

    public JMenuItem getObjectMenuItem(BObject anObject)
    {
        return new JMenuItem(new SimpleAction("Click Object", "Object menu:"));
    }

    // These methods will be called when
    // each of the different menus are about to be invoked.
    public void notifyPostToolsMenu(BPackage bp, JMenuItem jmi)
    {
        System.out.println("Post on Tools menu");
        curPackage = bp ; curClass = null ; curObject = null;
    }

    public void notifyPostClassMenu(BClass bc, JMenuItem jmi)
    {
        System.out.println("Post on Class menu");
        curPackage = null ; curClass = bc ; curObject = null;
    }

    public void notifyPostObjectMenu(BObject bo, JMenuItem jmi)
    {
        System.out.println("Post on Object menu");
        curPackage = null ; curClass = null ; curObject = bo;
    }

    // A utility method which pops up a dialog detailing the objects
    // involved in the current (SimpleAction) menu invocation.
    private void showCurrentStatus(String header)
    {
        try
        {
            if (curObject != null)
                curClass = curObject.getBClass();
            if (curClass != null)
                curPackage = curClass.getPackage();

            String msg = header;
            if (curPackage != null)
                msg += "\nCurrent Package = " + curPackage;
            if (curClass != null)
                msg += "\nCurrent Class = " + curClass;
            if (curObject != null)
                msg += "\nCurrent Object = " + curObject;
            JOptionPane.showMessageDialog(null, msg);
        }
        catch (Exception exc)
        {
            JOptionPane.showMessageDialog(null, "FAIL!");
        }
    }

    // The nested class that instantiates the different (simple) menus.
    class SimpleAction extends AbstractAction {
        private String msgHeader;

        public SimpleAction(String menuName, String msg)
        {
            putValue(AbstractAction.NAME, menuName);
            msgHeader = msg;
        }
        public void actionPerformed(ActionEvent anEvent)
        {
            showCurrentStatus(msgHeader);
        }
    }
}
