package view;

import interface_adapter.LoginViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "login in";
    private final LoginViewModel loginViewModel;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
