package de.elfsoft;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import bluej.extensions.ExtensionException;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;


public class lol extends Extension implements PackageListener {

    public boolean isCompatible() {
        return true;
    }

    public void startup(BlueJ blueJ) {
        MenuBuilder myMenus = new MenuBuilder();
        blueJ.setMenuGenerator(myMenus);
        System.out.println("Hello");
        blueJ.addPackageListener(this);
    }

    public String getName() {
        return "Hello World Extension";
    }

    public String getVersion() {
        return "0.0.1";
    }

    public void packageOpened(PackageEvent packageEvent) {
        try {
            System.out.println ("Project " + packageEvent.getPackage().getProject().getName() + " opened.");
        } catch (ExtensionException e) {
            System.out.println("Project closed by BlueJ");
        }
    }

    public void packageClosing(PackageEvent packageEvent) {

    }
}


