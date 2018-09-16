package five;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.*;

/*
JPanel 创建一个面板，再向这个面板添加组件，然后把这个面板添加到其他容器中。JPanel面板默认布局是FlowLayout布局
 */
public class ChessBoard extends JPanel
{
    public static final  int  Margin=15;//边距 如果成员变量或局部变量被修饰为final，那他就是常量。
    public static final  int  Span=20;//网络宽度
    public static final  int Rows=15;//棋盘行数
    public static final  int Clos=15;//棋盘列数
    int col1=1,row1=1;
    Image image;
    CHess[] chessList=new CHess[(Rows+1)*(Clos+1)];
    int chessCount=0;
    boolean Black=true;//默认黑棋先下；
    private boolean Gamerun=true;
    int computerColor;
    boolean ComputerGo;
    private five f;
    int [][]boardStatus;//记住格子状态


    public ChessBoard(five f)
    {   this.f=f;
        this.addMouseMotionListener(new MouseMonitor());
        image= Toolkit.getDefaultToolkit().getImage("img/board.jpg");//画棋盘;
        this.addMouseListener(new MouseMonitor());
        boardStatus = new int[Clos+1][Rows+1];
        for (int i=0;i<=Clos;i++)
        {
            for (int j=0;j<=Rows;j++)
            {
                boardStatus[i][j]=0;//0,kong;1,heiqi;2,baiqi;


            }
        }

    }

    private boolean has(int col,int row)
    {

        for (int i = 0; i <= chessCount; i++)
        {
            CHess ch=chessList[i];

            if(ch!=null&&ch.getCol()==col&&ch.getRow()==row)
            {
                JOptionPane.showMessageDialog(null, "你已被喝茶", "waring", JOptionPane.ERROR_MESSAGE);

                return true;//已经有棋子

            }
        }
        return false;
    }
    private boolean has(int col,int row,Color color)
    {
        for(int i=0;i<chessCount;i++)
        {
            CHess ch=chessList[i];
            if(ch!=null&&ch.getCol()==col&&ch.getRow()==row&&ch.getColor()==color)
            {
                return true;
            }

        }
        return false;
    }
    private boolean win(int col,int row)
    {
        int continueCount=1;//连续棋子的个数
        Color c=Black?Color.black:Color.white;

        //横向向左寻找
        for(int x=col-1;x>=0;x--){
            if(has(x,row,c)){
                continueCount++;
            }else
                break;
        }
        //横向向右寻找
        for(int x=col+1;x<=Clos;x++){
            if(has(x,row,c)){
                continueCount++;
            }else
                break;
        }
        if(continueCount>=5){
            return true;
        }else
            continueCount=1;

        //继续另一种搜索纵向
        //向上搜索
        for(int y=row-1;y>=0;y--){
            if(has(col,y,c)){
                continueCount++;
            }else
                break;
        }
        //纵向向下寻找
        for(int y=row+1;y<=Rows;y++){
            if(has(col,y,c))
                continueCount++;
            else
                break;

        }
        if(continueCount>=5)
            return true;
        else
            continueCount=1;


        //继续另一种情况的搜索：右上到左下
        //向右上寻找
        for(int x=col+1,y=row-1; y>=0&&x<=Clos; x++,y--){
            if(has(x,y,c)){
                continueCount++;
            }
            else break;
        }
        //向左下寻找
        for(int x=col-1,y=row+1; x>=0&&y<=Rows; x--,y++){
            if(has(x,y,c)){
                continueCount++;
            }
            else break;
        }
        if(continueCount>=5)
            return true;
        else continueCount=1;


        //继续另一种情况的搜索：左上到右下
        //向左上寻找
        for(int x=col-1,y=row-1; x>=0&&y>=0; x--,y--){
            if(has(x,y,c))
                continueCount++;
            else break;
        }
        //右下寻找
        for(int x=col+1,y=row+1; x<=Clos&&y<=Rows; x++,y++){
            if(has(x,y,c))
                continueCount++;
            else break;
        }
        if(continueCount>=5)
            return true;
        else
            return false;
    }
    public  void  paintComponent(Graphics g)//重写paintComponent方法
    {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);//画棋盘
        /*网格的行数比横线少一
          网格的列数比竖线少一
         */
        for(int i=0;i<=Rows;i++)//画横线
        {
            g.drawLine(Margin,Margin+i*Span,Margin+Clos*Span,Margin+i*Span);

        }
        for(int i=0;i<=Clos;i++)//画竖线
        {
            g.drawLine(Margin+i*Span,Margin,Margin+i*Span,Margin+Rows*Span);
        }
        /*
        画特殊点*5
        */
        g.fillRect(Margin+3*Span-2,Margin+3*Span-2,5,5);//x,y为矩形左上角,左下角的点
        g.fillRect(Margin+3*Span-2,Margin+12*Span-2,5,5);//左上角
        g.fillRect(Margin+12*Span-2,Margin+3*Span-2,5,5);//右下角
        g.fillRect(Margin+12*Span-2,Margin+12*Span-2,5,5);//右上角
        g.fillRect(Margin+(Rows/2)*Span-2,Margin+(Clos/2)*Span-2,5,5);//中间点
        /*CHess c1 = new CHess(2,1,Color.BLACK,this);
        CHess c2 = new CHess(5,2,Color.WHITE,this);
        c1.draw(g);
        c2.draw(g);*/
        for(int i =0;i<=chessCount;i++)
        {
            if(chessList[i]!=null)
            {
                chessList[i].draw(g);
            }
        }


    }
    public Dimension getPreferredSize()//接口？
    {
        return new Dimension(Margin*2+Span*Clos,Margin*2+Span*Rows);//获取窗口大小
    }
    class MouseMonitor extends MouseAdapter//下棋
    {
        public void mousePressed(MouseEvent e)
        {
            int col = (e.getX()-Margin+Span/2)/Span;
            int row = (e.getY()-Margin+Span/2)/Span;
            //非法下棋
            if(Gamerun!=true) return;
            if(col<0||col>Clos||row<0||row>Rows||has(col,row))
            {

                return;
            }
            manGo(col,row);
            /*CHess ch = new CHess(row,col,Black?Color.black:Color.white,ChessBoard.this);//位置错了，但算了。

            chessList[chessCount++]=ch;
            repaint();//重新绘制棋盘
            if(win(col,row))
            {
                String colorwin=Black?"黑棋":"白棋";
                String msg=String.format(("恭喜，%s赢了"),colorwin);
                JOptionPane.showMessageDialog(null,msg,"winer",JOptionPane.ERROR_MESSAGE);
                Gamerun=false;
            }
            Black=!Black;
            */
            if(Gamerun!=true) return;
            computerGo();

        }

    }
    public void restartGame()
    {
        for (int i=0;i<=Clos;i++)
        {
            for(int j=0;j<=Rows;j++)
            {
                boardStatus[i][j]=0;
            }
        }
        for (int i=0;i<chessList.length;i++)
        {
            chessList[i]=null;
        }
        Gamerun = true;
        Black=true;
        chessCount=0;
        ComputerGo=f.computerFirst.isSelected();//????
        computerColor=ComputerGo?1:2;
        if(ComputerGo)
        {
            computerGo();
        }
        paintComponent(this.getGraphics());
    }
    private  void computerGo()
    {
        Evaluate e = new Evaluate(this);
        int pos[]=e.getTheBEstPOstion();
        putCHess(pos[0],pos[1],Black?Color.BLACK:Color.WHITE);

    }
    public void backdo()
    {
        if(chessCount<=2)
        {
            return;
        }

            int i=chessList[chessCount-1].getCol();
            int j=chessList[chessCount-1].getRow();
            boardStatus[i][j]=0;
            chessList[chessCount-1]=null;


            i=chessList[chessCount-2].getCol();
            j=chessList[chessCount-2].getRow();
            boardStatus[i][j]=0;
            chessList[chessCount-2]=null;




        chessCount-=2;
        paintComponent(this.getGraphics());

    }
    public void manGo(int col,int row)
    {
        putCHess(col,row,Black?Color.BLACK:Color.WHITE);

    }
    public void putCHess(int col,int row,Color color)
    {
        boardStatus[col][row]=(color==Color.BLACK)?1:2;
        CHess ch=new CHess(row,col,color,ChessBoard.this);
        chessList[chessCount++]=ch;
        paintComponent(this.getGraphics());
        if(win(col,row))
        {
            String colorName=Black?"黑棋":"白棋";
            String msg=String.format("恭喜，%s赢了!",colorName);
            JOptionPane.showMessageDialog(null,msg,"winer",JOptionPane.ERROR_MESSAGE);
            Gamerun=false;
        }
        else
        {
            Black=!Black;
            ComputerGo=!ComputerGo;
        }
    }

}


