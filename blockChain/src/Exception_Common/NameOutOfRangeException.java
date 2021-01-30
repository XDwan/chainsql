package Exception_Common;

import SwingFrame.Warning;

public class NameOutOfRangeException extends Exception {

    String message;

    public NameOutOfRangeException(String message){
        // 弹出一个窗口
        this.message = message;
        System.out.println(message);
    }

    public void dialogOpen(){
        new Warning(message,"长度超出");
    }
}
