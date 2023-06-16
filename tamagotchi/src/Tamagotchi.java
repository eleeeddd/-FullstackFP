import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Tamagotchi extends JFrame {
    private JPanel mainPanel;
    private JLabel hambreLabel, sueñoLabel, felicidadLabel, catImageLabel;
    private JProgressBar felicidadBar, limpiezaBar;
    private JButton alimentarButton, sueñoButton, jugarButton, bañoButton;
    private TamagotchiLogic tamagotchi;

    public Tamagotchi() {
        super("Mi Tamagotchi");

        tamagotchi = new TamagotchiLogic();
        tamagotchi.start();

        mainPanel = new JPanel(new GridLayout(5, 2));

        hambreLabel = new JLabel("Hambre: " + tamagotchi.getHambre());
        mainPanel.add(hambreLabel);

        alimentarButton = new JButton("Alimentar");
        alimentarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                            
                tamagotchi.alimentar();
                hambreLabel.setText("Hambre: " + tamagotchi.getHambre());
                showGifImage("comer.gif", 3000);
            }
        });
        mainPanel.add(alimentarButton);

        sueñoLabel = new JLabel("Sueño: " + tamagotchi.getSueño());
        mainPanel.add(sueñoLabel);

        sueñoButton = new JButton("Dormir");
        sueñoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamagotchi.sueño();
                sueñoLabel.setText("Sueño: " + tamagotchi.getSueño());
                showGifImage("dormir.gif", 3000);
            }
        });
        mainPanel.add(sueñoButton);

        felicidadLabel = new JLabel("Felicidad: " + tamagotchi.getFelicidad());
        mainPanel.add(felicidadLabel);

        felicidadBar = new JProgressBar(0, 100);
        felicidadBar.setValue(tamagotchi.getFelicidad());
        felicidadBar.setStringPainted(true);
        mainPanel.add(felicidadBar);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamagotchi.jugar();
                felicidadLabel.setText("Felicidad: " + tamagotchi.getFelicidad());
                felicidadBar.setValue(tamagotchi.getFelicidad());
                showGifImage("jugar.gif", 3000);
            }
        });
        mainPanel.add(jugarButton);

        bañoButton = new JButton("Baño");
        bañoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamagotchi.baño();
                limpiezaBar.setValue(tamagotchi.getLimpieza());
                showGifImage("baño.gif", 3000);
            }
        });
        mainPanel.add(bañoButton);

        catImageLabel = new JLabel();
        showStaticImage("gato.gif");
        mainPanel.add(catImageLabel);

        limpiezaBar = new JProgressBar(0, 100);
        limpiezaBar.setValue(tamagotchi.getLimpieza());
        limpiezaBar.setStringPainted(true);
        mainPanel.add(new JLabel("Limpieza:"));
        mainPanel.add(limpiezaBar);

        add(mainPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(660, 800);
        setVisible(true);
    }

    private void showStaticImage(String imageName) {
        URL imageUrl = getClass().getResource(imageName);
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        catImageLabel.setIcon(imageIcon);
    }

    private void showGifImage(String gifName, int duration) {
        URL gifUrl = getClass().getResource(gifName);
        ImageIcon gifIcon = new ImageIcon(gifUrl);
        catImageLabel.setIcon(gifIcon);
        Timer timer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStaticImage("gato.gif");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private class TamagotchiLogic extends Thread {
        private int hambre, sueño, felicidad, limpieza;

        public TamagotchiLogic() {
            hambre = 50;
            sueño = 50;
            felicidad = 50;
            limpieza = 50;
        }

        public int getHambre() {
            return hambre;
        }

        public int getSueño() {
            return sueño;
        }

        public int getFelicidad() {
            return felicidad;
        }

        public int getLimpieza() {
            return limpieza;
        }

        public void alimentar() {
            hambre += 10;
            if (hambre > 100) hambre = 100;
        }

        public void sueño() {
            sueño += 10;
            if (sueño > 100) sueño = 100;
        }

        public void jugar() {
            felicidad += 10;
            if (felicidad > 100) felicidad = 100;
        }

        public void baño() {
            limpieza += 10;
            if (limpieza > 100) limpieza = 100;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hambre -= 30;
                sueño -= 30;
                felicidad -= 30;
                limpieza -= 30;
                if (hambre <= 0 || sueño <= 0 || felicidad <= 0 || limpieza <= 0) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            showStaticImage("morir.gif");
                            JOptionPane.showMessageDialog(null, "Tu mascota se murio :(");
                            
                            System.exit(0);
                        }
                    });
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        hambreLabel.setText("Hambre: " + hambre);
                        sueñoLabel.setText("Sueño: " + sueño);
                        felicidadLabel.setText("Felicidad: " + felicidad);
                        felicidadBar.setValue(felicidad);
                        limpiezaBar.setValue(limpieza);
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Tamagotchi();
            }
        });
    }
}
