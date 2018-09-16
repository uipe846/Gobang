package five;

import java.awt.*;

public class CHess
{
    public static final int D = ChessBoard.Span*2-2;
    private int col;
    private int row;
    private Color color;
    ChessBoard cb;
    public CHess(int row,int col,Color color,ChessBoard cb)
    {
        this.cb=cb;
        this.col=col;
        this.row=row;
        this.color=color;
    }
    public int getCol()
    {
        return col;
    }
    public int getRow()
    {
        return row;
    }
    public  Color getColor() {
        return color;
    }
    public void draw(Graphics g)
    {
        color = getColor();
        int xpos = col*cb.Span+cb.Margin;
        int ypos = row*cb.Span+cb.Margin;
        Graphics2D g0 = (Graphics2D) g;
        //RadialGradientPaint paint = null;
        int x = xpos+D/4;
        int y = ypos+D/4;
        if(color == Color.black)
        {
            g0.setColor(Color.black);
        }
        else if(color == Color.white)
        {
            g0.setColor(Color.white);
        }
        g0.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        g0.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g0.fillArc(x-cb.Span,y-cb.Span,cb.Span-2,cb.Span-2,0,360);

    }
}



