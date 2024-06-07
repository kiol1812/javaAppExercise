package test;
// package src.test;

// import java.awt.*;
// import java.awt.event.*;
// import java.util.*;

// // import test.structure.Pair;

// public class Combo {
//     private static int state;
//     public static void main(String args[]){
//         new TimerUsageCycle().start();
//     }
//     static class StateCycle extends Thread {
//         private final Timer timer = new Timer("State", false);
//         // private static final int chanel = 30;//暫定半秒取消時間 <- 取消系統等之後有空再寫
//         private static final int[] __ = {60};  //硬質時間(表)
//         private static final int[] ___ = {60}; //收招時間(表)
//         @Override
//         public void run(){
//             timer.schedule(new TimerTask() {
//                 @Override
//                 public void run(){//查如何執行緒並行/同步； e.g. 共用timer觸發? / period隨FPS調整?
//                 }
//             }, 0, 17);
//         }
//     }
//     static class TimerUsageCycle extends Thread {
//         private final Timer timer = new Timer("Combo", false);
//         private boolean flag = false;
//         private static Frame frame;
//         private final static int[] dist = {0, 65, 0, 83, 68, 85, 0, 87, 0, 73, 74, 75, 76, 0, 0, 79};
//         private final static String[] motions = {"3304405"};
//         private static boolean[] flags = new boolean[16];
//         private static ArrayList<Integer> pressed;
//         private static ArrayList<Integer> records; //like queue
//         private int pressedSize, recordSize;
//         @Override
//         public void run(){
//             timer.schedule(new TimerTask() {
//                 @Override
//                 public void run(){
//                     if(pressedSize!=pressed.size()){
//                         pressedSize=pressed.size();
//                         // System.out.print(pressedSize+": ");
//                         int record=0;
//                         for(int i=0; i<16; i++){
//                             if(flags[i]){
//                                 // System.out.print((char)(dist[i])+" "+dist[i]+", ");
//                                 record = record*100+i; // can use any number that greater then 16, but movtions will be changed
//                             }
//                         }
//                         records.add(record*100); 
//                         // System.out.println();
//                     }
//                     for(int i=0; i<records.size(); i++){
//                         records.set(i, records.get(i)+1);
//                         if(records.get(i)%100>=60) records.remove(i);
//                     }
//                     if(recordSize!=records.size()){
//                         recordSize=records.size();
//                         String str="";
//                         for(int each:records) str+=String.valueOf(each/100);
//                         // System.out.println(recordSize+": "+str);
//                         for(String m:motions){
//                             if(str.length()>=m.length()){
//                                 for(int i=0; i<str.length()-m.length(); i++){
//                                     String subStr = str.substring(i, i+m.length());
//                                     if(subStr.equals(m)&&state==0){
//                                         System.out.println("fire");
//                                         // state=120;
//                                         break;
//                                     }
//                                 }
//                             }
//                         }
//                     }
//                     // if(state>0) state--;
//                 }
//             }, 0, 17); //(1/60*1000)
//         }
//         public TimerUsageCycle(){
//             pressed = new ArrayList<Integer>(0);
//             records = new ArrayList<Integer>(0);
//             pressedSize=0; recordSize=0; state=0;
//             for(int i=0; i<16; i++) flags[i]=false;
//             frame = new Frame("test");
//             frame.setSize(100, 100);
//             frame.addKeyListener(new KeyAdapter(){
//                 public void keyPressed(KeyEvent e){
//                     int index = e.getKeyCode()%16;
//                     if(!flags[index]) pressed.add(index);
//                     flags[index]=true;
//                 }
//                 public void keyReleased(KeyEvent e){
//                     int index = e.getKeyCode()%16;
//                     flags[index]=false;
//                     pressed.remove(pressed.indexOf(index));
//                 }
//             });
//             frame.addWindowListener(new WindowAdapter(){
//                 @Override
//                 public void windowClosing(WindowEvent e){
//                     System.exit(0);
//                 }
//             });
//             frame.setVisible(true);
//         }
//     }
// }