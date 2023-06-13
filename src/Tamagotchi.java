import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Tamagotchi extends JFrame {
    private JPanel mainPanel;
    private JLabel hambreLabel, SueñoLabel, FelicidadLabel, catImageLabel;
    private JProgressBar FelicidadBar;
    private JButton alimentarButton, SueñoButton, jugarButton;
    private TamagotchiLogic tamagotchi;

    public Tamagotchi() {
        super("Mi Tamagotchi");

        tamagotchi = new TamagotchiLogic();
        tamagotchi.start();

        mainPanel = new JPanel(new GridLayout(4, 2));

        hambreLabel = new JLabel("hambre: " + tamagotchi.gethambre());
        mainPanel.add(hambreLabel);

        alimentarButton = new JButton("alimentar");
        alimentarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                            
                tamagotchi.alimentar();
                hambreLabel.setText("hambre: " + tamagotchi.gethambre());
                showGifImage("gato c.gif", 3000);
            }
        });
        mainPanel.add(alimentarButton);

        SueñoLabel = new JLabel("Sueño: " + tamagotchi.getSueño());
        mainPanel.add(SueñoLabel);

        SueñoButton = new JButton("Dormir");
        SueñoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamagotchi.Sueño();
                SueñoLabel.setText("Sueño " + tamagotchi.getSueño());
            }
        });
        mainPanel.add(SueñoButton);

        FelicidadLabel = new JLabel("Felicidad: " + tamagotchi.getFelicidad());
        mainPanel.add(FelicidadLabel);

        FelicidadBar = new JProgressBar(0, 100);
        FelicidadBar.setValue(tamagotchi.getFelicidad());
        FelicidadBar.setStringPainted(true);
        mainPanel.add(FelicidadBar);

        jugarButton = new JButton("jugar");
        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamagotchi.jugar();
                FelicidadLabel.setText("Felicidad: " + tamagotchi.getFelicidad());
                FelicidadBar.setValue(tamagotchi.getFelicidad());
                showGifImage("gato v1.gif", 3000);
            }
        });
        mainPanel.add(jugarButton);

        catImageLabel = new JLabel();
        showStaticImage("gato.png");
        mainPanel.add(catImageLabel);

        add(mainPanel);
        

        setUndecorated(true);
        setSize(660,800);
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
                showStaticImage("gato.png");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Tamagotchi();
            }
        });
    }

    private class TamagotchiLogic extends Thread {
        private int hambre, Sueño, Felicidad;

        public TamagotchiLogic() {
            hambre = 50;
            Sueño = 50;
            Felicidad = 50;
        }

        public int gethambre() {
            return hambre;
        }

        public int getSueño() {
            return Sueño;
        }

        public int getFelicidad() {
            return Felicidad;
        }

        public void alimentar() {
            hambre += 10;
            if (hambre > 100) hambre = 100;
        }

        public void Sueño() {
            Sueño += 10;
            if (Sueño > 100) Sueño = 100;
        }

        public void jugar() {
            Felicidad += 10;
            if (Felicidad > 100) Felicidad = 100;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hambre -= 5;
                Sueño -= 5;
                Felicidad += 5;
                if (hambre <= 0 || Sueño <= 0 || Felicidad <= 0) {
                    JOptionPane.showMessageDialog(Tamagotchi.this, "tu tamagochi hizo la kurt cobain  :(");
                    System.exit(0);
                }
                hambreLabel.setText("Hambre: " + hambre);
                SueñoLabel.setText("Sueño: " + Sueño);
                FelicidadLabel.setText("Felicidad: " + Felicidad);
                FelicidadBar.setValue(Felicidad);
            }
        }
    }
}