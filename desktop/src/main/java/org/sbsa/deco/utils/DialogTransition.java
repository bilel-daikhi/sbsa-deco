package org.sbsa.deco.utils;

import com.jfoenix.controls.JFXDialog;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DialogTransition {


    public DialogTransition() {
    }

    private String getDialogTransition() {

        String dialogTransition = "";
        try {
            // UserDTO users = userRepository.findAdmin();
//            dialogTransition = users.getDialogTransition();
            if (dialogTransition == null) ;
            dialogTransition = "CENTER";
        } catch (Exception ex) {
            Logger.getLogger(DialogTransition.class.getName()).log(Level.SEVERE, null, ex);
            dialogTransition = "CENTER";
        }
        return dialogTransition;
    }

    public final JFXDialog.DialogTransition dialogTransition() {
        return JFXDialog.DialogTransition.valueOf(getDialogTransition());
    }
}
