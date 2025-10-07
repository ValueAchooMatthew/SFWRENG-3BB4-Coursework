import javax.swing.*;
import java.awt.*;

public class RadioSimulator extends JFrame {
    enum State { OFF, ON_TOP, ON_STATION, SCANNING, ON_BOTTOM }
    private State state = State.OFF;
    private JLabel display;
    private int frequency = 108;
    private Timer scanTimer;

    public RadioSimulator() {
        super("Mini FM Radio");
        display = new JLabel("State: OFF", SwingConstants.CENTER);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 5));
        JButton onBtn = new JButton("On");
        JButton offBtn = new JButton("Off");
        JButton scanBtn = new JButton("Scan");
        JButton resetBtn = new JButton("Reset");
        JButton lockBtn = new JButton("Lock");
        buttons.add(onBtn);
        buttons.add(offBtn);
        buttons.add(scanBtn);
        buttons.add(resetBtn);
        buttons.add(lockBtn);
        add(buttons, BorderLayout.SOUTH);

        scanTimer = new Timer(300, e -> {
            if (state == State.SCANNING) {
                if (frequency > 88) {
                    frequency--;
                } else {
                    state = State.ON_BOTTOM;
                    scanTimer.stop();
                }
                update();
            }
        });

        onBtn.addActionListener(e -> {
            if (state == State.OFF) {
                state = State.ON_TOP;
                frequency = 108;
            }
            update();
        });
        offBtn.addActionListener(e -> {
            state = State.OFF;
            scanTimer.stop();
            update();
        });
        scanBtn.addActionListener(e -> {
            if (state == State.ON_TOP || state == State.ON_STATION || state == State.ON_BOTTOM) {
                state = State.SCANNING;
                scanTimer.start();
            }
            update();
        });
        resetBtn.addActionListener(e -> {
            if (state != State.OFF) {
                state = State.ON_TOP;
                frequency = 108;
                scanTimer.stop();
            }
            update();
        });
        lockBtn.addActionListener(e -> {
            if (state == State.SCANNING) {
                state = State.ON_STATION;
                scanTimer.stop();
            }
            update();
        });

        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void update() {
        if (state == State.OFF) display.setText("State: OFF");
        else display.setText("State: " + state + "  Frequency: " + frequency + " MHz");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RadioSimulator::new);
    }
}

