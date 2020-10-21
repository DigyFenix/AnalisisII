/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author echacon
 */
public class JPanelConFondo extends JPanel {

    private Image imagen;

    public JPanelConFondo(String rutaFileImagen) {
        super();
        try {
            File pathToFile = new File(rutaFileImagen);
            this.imagen = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(),
                this);

        setOpaque(false);
        super.paint(g);
    }

    
}
