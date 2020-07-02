package utils;

import sun.swing.FilePane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileChooser extends JFileChooser {

    public String selectFile(String title, String initPath, int JFileChooserType) {
        this.setCurrentDirectory(new File(initPath));
        this.setDialogTitle(title);
        this.setFileSelectionMode(JFileChooserType);
        this.setAcceptAllFileFilterUsed(false);
        return this.showOpenDialog((Component)null) == 0 ? this.getSelectedFile().getAbsolutePath() : null;
    }

    public void updateUI() {
        LookAndFeel old = UIManager.getLookAndFeel();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable var6) {
            old = null;
        }

        super.updateUI();
        if (old != null) {
            FilePane filePane = findFilePane(this);
            filePane.setViewType(1);
            filePane.setViewType(0);
            Color background = UIManager.getColor("Label.background");
            this.setBackground(background);
            this.setOpaque(true);

            try {
                UIManager.setLookAndFeel(old);
            } catch (UnsupportedLookAndFeelException var5) {
                ;
            }
        }

    }

    private static FilePane findFilePane(Container parent) {
        Component[] var1 = parent.getComponents();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Component comp = var1[var3];
            if (FilePane.class.isInstance(comp)) {
                return (FilePane)comp;
            }

            if (comp instanceof Container) {
                Container cont = (Container)comp;
                if (cont.getComponentCount() > 0) {
                    FilePane found = findFilePane(cont);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }
}