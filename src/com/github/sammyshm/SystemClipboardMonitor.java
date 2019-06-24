package com.github.sammyshm;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 剪贴板监控器
 * 负责对剪贴板文本的监控和操作
 * 由于监控需要一个对象作为ClipboardOwner，故不能用静态类
 *
 */
public class SystemClipboardMonitor implements ClipboardOwner {
    static int times = 0;
    static String head = readHead();
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public SystemClipboardMonitor() {
        //如果剪贴板中有文本，则将它的ClipboardOwner设为自己
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            clipboard.setContents(clipboard.getContents(null), this);
        }
    }

    /************
     * 测试代码 *
     * **********
     */
    public static void main(String[] args) {
        SystemClipboardMonitor temp = new SystemClipboardMonitor();
        new JFrame().setVisible(true); // 软件窗口
    }

    /**********************************************
     * 如果剪贴板的内容改变，则系统自动调用此方法 *
     **********************************************
     */
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // 如果不暂停一下，经常会抛出IllegalStateException
        // 猜测是操作系统正在使用系统剪切板，故暂时无法访问
        System.out.println("重新捕捉！");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = null;
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            try {
                text = (String) clipboard.getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String te = "";
        String re = doText(text,"颜色");
        if(re != null)
            te = te.concat(re+'\n'+'\n');
        re = doText(text,"尺码");
        if(re != null)
            te = te.concat(re+'\n'+'\n');
        re = doText(text,"面料");
        if(re != null)
            te = te.concat(re+'\n'+'\n');

        re = doText(text,"产品类别");
        if(re != null)
            System.out.println(re);
        if(te == "")
            te = text;
        else{
            long l = System.currentTimeMillis();
            Date date = new Date(l);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM- dd HH:mm:ss");
            System.out.println("有效捕捉:"+(++times)+"\n时间："+dateFormat.format(date));
            te = head + te;
        }
        //System.out.println(te);
        String clearedText = te; // 自定义的处理方法
        //System.out.println(te);
        // 存入剪贴板，并注册自己为所有者
        // 用以监控下一次剪贴板内容变化
        StringSelection tmp = new StringSelection(clearedText);
        clipboard.setContents(tmp, this);
    }
    public static String doText(String a,String b){
        int index,i;
        index=a.lastIndexOf(b);
        i = index;
        if( i == -1)
            return null;
        while(a.charAt(i) != '\n')
            i++;
        return a.substring(index,i);
    }
    public static String readHead(){//获取前置文本并返回
        String re = null;
        String res = "";
        FileReader fr = null;
        try {
            fr = new FileReader("head.txt");
            BufferedReader br = new BufferedReader(fr);
            do {
                re = br.readLine();
                if(re != null)
                    res = res + re + '\n';
            }while(re != null);
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res + '\n';
    }
}