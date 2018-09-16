package five;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/*
JToolBar():建立一个新的JToolBar,位置为默认的水平方向.
JToolBar(int orientation):建立一个指定的JToolBar.
JToolBar(String name):建立一个指定名称的JToolBar.
JToolBar(String name,int orientation):建立一个指定名称和位置的JToolBar

JToolBar 类的常用方法
public JButton add(Action a) : 向工具栏中添加一个指派动作的新的Button
public void addSeparator() : 将默认大小的分隔符添加到工具栏的末尾
public Component getComponentAtIndex(int i) : 返回指定索引位置的组件
public int getComponentIndex(Component c) : 返回指定组件的索引
public int getOrientation() : 返回工具栏的当前方向
public boolean isFloatable() : 获取Floatable 属性，以确定工具栏是否能拖动，如果可以则返回true,否则返回false
public boolean isRollover () : 获取rollover 状态，以确定当鼠标经过工具栏按钮时，是否绘制按钮的边框，如果需要绘制则返回true,否则返回false
public void setFloatable(boolean b) : 设置Floatable 属性，如果要移动工具栏，此属性必须设置为true
 */

public class five extends JFrame
{
    private JToolBar toolBar;
    private JButton startButton,backButton,exitButton;
    private ChessBoard boardPanel;
    JCheckBox computerFirst;
    public five()
    {
        super("单机版五子棋");
        toolBar = new JToolBar();
        startButton = new JButton("重新开始");
        backButton = new JButton("悔棋");
        exitButton = new JButton("退出");
        boardPanel = new ChessBoard(this);
        computerFirst = new JCheckBox("智能，机器优先");
        toolBar.add(computerFirst);
        this.add(boardPanel,BorderLayout.CENTER);
        toolBar.add(startButton);
        toolBar.add(exitButton);
        toolBar.add(backButton);
        this.add(toolBar,BorderLayout.NORTH);
        /*borderlayout是一个简单的布局策略，划分为东南西北中五个区域，静态常量为center、north、south、west*/
       //this.setBounds(200,200,300,200);
        /* x - 组件的新 x 坐标
            y - 组件的新 y 坐标
            width - 组件的新 width
            height - 组件的新 height
         */

        this.setLocation(200,200);
        this.pack();//自动调整大小
        this.setResizable(false);//不可改变
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        ActionMonitor monitor=new ActionMonitor();
        startButton.addActionListener(monitor);
        backButton.addActionListener(monitor);
        exitButton.addActionListener(monitor);


    }
    class ActionMonitor implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==startButton)
            {
                boardPanel.restartGame();
            }
            else if(e.getSource()==backButton)
            {
                boardPanel.backdo();
            }
            else if(e.getSource()==exitButton)
            {
                System.exit(0);
            }
        }
    }
    public static void main(String[] args)
    {
        new five();
    }
}

