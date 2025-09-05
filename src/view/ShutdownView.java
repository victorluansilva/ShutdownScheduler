package view;

import model.ScheduleMode;
import  java.awt.event.ActionListener;
import java.util.Date;

public interface ShutdownView {
    //leitura de inputs
    ScheduleMode getMode();
    Date getSelectedDateTime();
    int getCountdownMinutes();
    boolean isRestartSelected();
    boolean isForceSelected();
    // interação
    void setStatus(String text);
    void showError(String message);
    void confirm(String message, String title);
    //listeners
    void onSchedule(ActionListener l);
    void onCancel(ActionListener l);
    void onShutdownNow(ActionListener l);
    void onModeChanged(ActionListener l);
    //util
    void setEnableDateTime(boolean enable);
    void setEnableMinutes(boolean enable);
    void setVisible(boolean enable);
}
