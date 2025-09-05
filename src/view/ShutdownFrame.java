package view;

import model.ScheduleMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class ShutdownFrame extends JFrame implements ShutdownView {

    private final JRadioButton rbDateTime = new JRadioButton("Por horário (data/hora)", true);
    private final JRadioButton rbCountdown = new JRadioButton("Por contagem (minutos)");
    private final JSpinner dateTimeSpinner;
    private final JSpinner minutesSpinner;
    private final JCheckBox cbRestart = new JCheckBox("Reiniciar em vez de desligar (/r)");
    private final JCheckBox cbForce = new JCheckBox("Forçar fechamento de apps (/f)");

    private final JButton btnSchedule = new JButton("Agendar");
    private final JButton btnCancel = new JButton("Cancelar agendamento");
    private final JButton btnShutdownNow = new JButton("Executar agora");
    private final JLabel statusLabel = new JLabel("Status: pronto.");

    public ShutdownFrame() {
        super("Agendador de Desligamento (Windows) — MVC");

        // Spinners
        Date initial = new Date(System.currentTimeMillis() + 10 * 60 * 1000L);
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(initial, null, null, java.util.Calendar.MINUTE));
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "dd/MM/yyyy HH:mm:ss"));
        minutesSpinner = new JSpinner(new SpinnerNumberModel(15, 1, 5256000, 1));

        // radios
        ButtonGroup group = new ButtonGroup();
        group.add(rbDateTime);
        group.add(rbCountdown);

        JPanel modePanel = new JPanel(new GridLayout(0, 1, 6, 6));
        modePanel.setBorder(new TitledBorder("Modo de agendamento"));
        modePanel.add(rbDateTime);
        modePanel.add(rbCountdown);

        JPanel paramsPanel = new JPanel(new GridBagLayout());
        paramsPanel.setBorder(new TitledBorder("Parâmetros"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx=0; c.gridy=0; c.anchor=GridBagConstraints.LINE_END;
        paramsPanel.add(new JLabel("Data/Hora:"), c);
        c.gridx=1; c.anchor=GridBagConstraints.LINE_START;
        paramsPanel.add(dateTimeSpinner, c);

        c.gridx=0; c.gridy=1; c.anchor=GridBagConstraints.LINE_END;
        paramsPanel.add(new JLabel("Contagem (min):"), c);
        c.gridx=1; c.anchor=GridBagConstraints.LINE_START;
        paramsPanel.add(minutesSpinner, c);

        c.gridx=1; c.gridy=2; c.anchor=GridBagConstraints.LINE_START;
        JPanel opts = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        opts.add(cbRestart);
        opts.add(cbForce);
        paramsPanel.add(opts, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        buttons.add(btnSchedule);
        buttons.add(btnCancel);
        buttons.add(btnShutdownNow);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        JPanel center = new JPanel(new BorderLayout(10,10));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        center.add(modePanel, BorderLayout.WEST);
        center.add(paramsPanel, BorderLayout.CENTER);
        center.add(buttons, BorderLayout.SOUTH);

        setContentPane(new JPanel(new BorderLayout()));
        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);

        // estado inicial
        setEnableDateTime(true);
        setEnableMinutes(false);

        // mudar enable quando o usuário troca modo
        ActionListener toggle = e -> {
            boolean byDate = rbDateTime.isSelected();
            setEnableDateTime(byDate);
            setEnableMinutes(!byDate);
        };
        rbDateTime.addActionListener(toggle);
        rbCountdown.addActionListener(toggle);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 280);
        setLocationRelativeTo(null);
    }

    // ShutdownView impl
    @Override public ScheduleMode getMode() { return rbDateTime.isSelected()? ScheduleMode.BY_DATE_TIME : ScheduleMode.BY_COUNTDOWN; }
    @Override public Date getSelectedDateTime() { return (Date) dateTimeSpinner.getValue(); }
    @Override public int getCountdownMinutes() { return (Integer) minutesSpinner.getValue(); }
    @Override public boolean isRestartSelected() { return cbRestart.isSelected(); }
    @Override public boolean isForceSelected() { return cbForce.isSelected(); }
    @Override public void setStatus(String text) { statusLabel.setText("Status: " + text); }
    @Override public void showError(String message) { JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE); }
    @Override public boolean confirm(String message, String title) {
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    @Override public void onSchedule(ActionListener l) { btnSchedule.addActionListener(l); }
    @Override public void onCancel(ActionListener l) { btnCancel.addActionListener(l); }
    @Override public void onShutdownNow(ActionListener l) { btnShutdownNow.addActionListener(l); }
    @Override public void onModeChanged(ActionListener l) { rbDateTime.addActionListener(l); rbCountdown.addActionListener(l); }
    @Override public void setEnableDateTime(boolean enable) { dateTimeSpinner.setEnabled(enable); }
    @Override public void setEnableMinutes(boolean enable) { minutesSpinner.setEnabled(enable); }
}
