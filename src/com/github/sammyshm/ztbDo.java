package com.github.sammyshm;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ztbDo {

    public static void main(String[] args) throws InterruptedException {
        String text = null;
        //while (true) {
            String text0 = text;
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            // 获取剪贴板中的内容
            Transferable trans = clipboard.getContents(null);

            if (trans != null) {
                // 判断剪贴板中的内容是否支持文本
                if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        // 获取剪贴板中的文本内容
                        text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
           //if(text != text0){
            String te = "";
            text = getClipboardString();
            //if((text != null)&&(text != te))
                //System.out.println(text)
            String re = doText(text,"颜色");
            if(re != null)
                te = te.concat(re+'\n'+'\n');
            //System.out.println(re);
            re = doText(text,"尺码");
            if(re != null)
                te = te.concat(re+'\n'+'\n');
            //System.out.println(re);
            re = doText(text,"面料");
            if(re != null)
                te = te.concat(re+'\n'+'\n');
            System.out.println(te);
            setClipboardString(te);
            // 从剪贴板中获取文本（粘贴）
            //String text = getClipboardString();
        }//}
   // }

    /**
     * 把文本设置到剪贴板（复制）
     */
    public static String doText(String a,String b){
        int index,i;
        index=a.lastIndexOf(b);
        i = index;
        while(a.charAt(i) != '\n')
            i++;
        return a.substring(index,i);
    }
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    /**
     * 从剪贴板中获取文本（粘贴）
     */
    public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);

        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}