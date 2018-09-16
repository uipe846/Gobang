package five;

public class Evaluate
{
    private static final int Five =50000;
    private static final int liveFour = 5000;
    private static final int chonFour = 1000;
    private static final int liveThree = 500;
    private static final int sleepThree = 100;
    private static final int liveTow = 50;
    private ChessBoard cb;
    private int[][] blackValue;
    private int[][] whiteValue;
    private int[][] staticValue;
    private  static  final  int LargeNumber=10000000;
    private static final int SearchDepth=3;
    private static final int SampleNumber=10;
    public Evaluate(ChessBoard cb)//赋初始值
    {
        this.cb = cb;
        blackValue = new int[ChessBoard.Rows+1][ChessBoard.Clos+1];
        whiteValue = new int[ChessBoard.Rows+1][ChessBoard.Clos+1];
        staticValue = new int[ChessBoard.Rows+1][ChessBoard.Clos+1];
        for (int i=0;i<=ChessBoard.Rows;i++)
        {
            for(int j=0;j<=ChessBoard.Clos;j++)
            {
                blackValue[i][j]=0;
                whiteValue[i][j]=0;
            }
        }
        for(int i=0;i<=(ChessBoard.Rows+1)/2;i++)
        {
            for (int j=0;j<=(ChessBoard.Clos+1)/2;j++)
            {
                staticValue[i][j]=i<j?i:j;//左上
                staticValue[ChessBoard.Rows-i][j]=staticValue[i][j];//列不变，左下
                staticValue[ChessBoard.Rows-i][ChessBoard.Clos-j]=staticValue[i][j];//右下
                staticValue[i][ChessBoard.Clos-j]=staticValue[i][j];//右上
            }
        }
    }
    private int eval(int color,int col,int row,int dir)//在某个方向的估值
    {
        int k,m;
        int value = 0;
        int chCont1=0;
        int spCont1=0;
        int chcont2=0;
        int spCont2=0;
        int chCont3=0;
        int spCont3=0;
        int spCont4=0;
        switch (dir)
        {
            case 1 ://横向

                for (k=col+1;k<=cb.Clos;k++)//正向查找连续相同的棋子
                {
                    if(cb.boardStatus[k][row]==color)
                    {
                        chCont1++;
                    }
                    else
                        break;
                }
                while((k<=cb.Clos)&&(cb.boardStatus[k][row]==0))
                {
                    spCont1++;
                    k++;
                }
                if(spCont1==1)
                {
                    while((k<=cb.Clos)&&(cb.boardStatus[k][row]==color))
                    {
                        chcont2++;
                        k++;
                    }
                    while ((k<=cb.Clos)&&(cb.boardStatus[k][row]==0))
                    {
                        spCont2++;
                        k++;
                    }
                }
                //相反方向
                for (k=col-1;k>=0;k--)
                {
                        if(cb.boardStatus[k][row]==color)
                    {
                        chCont1++;
                    }
                    else
                        break;
                }
                while(k>=0&&(cb.boardStatus[k][row]==0))
                {
                    spCont3++;
                    k--;
                }
                if(spCont3==1)
                {
                    while((k>=0)&&(cb.boardStatus[k][row]==color))
                    {
                        chCont3++;
                        k--;
                    }
                    while((k>=0)&&(cb.boardStatus[k][row]==0))
                    {
                        spCont4++;
                        k--;
                    }
                }

                break;
            case 2://向垂直方向
                for (k=row+1;k<=cb.Rows;k++)//向增加方向
                {
                    if (cb.boardStatus[col][k]==color)
                    {
                        chCont1++;
                    }
                    else break;
                }
                while((k<=cb.Rows)&&(cb.boardStatus[col][k]==0))
                {
                    spCont1++;
                    k++;
                }
                while((k<=cb.Rows)&&(cb.boardStatus[col][k]==color))
                {
                    chcont2++;
                    k++;
                }
                while((k<=cb.Rows)&&(cb.boardStatus[col][k]==0))
                {
                    spCont2++;
                    k++;
                }
                for (k=row-1;k>=0;k++)//向相反的方向
                {
                    if (cb.boardStatus[col][k]==color)
                    {
                        chCont1++;
                    }
                    else break;
                }
                while((k>=0)&&(cb.boardStatus[col][k]==0))//kong
                {
                    spCont3++;
                    k--;
                }
                while((k>=0)&&(cb.boardStatus[col][k]==color))
                {
                    chCont3++;
                    k--;
                }
                while((k>=0)&&(cb.boardStatus[col][k]==0))
                {
                    spCont4++;
                    k--;
                }
                break;
            case 3://从有右下到左上
                int o=0;
            for(k=col+1,o=row+1;(k<=col&&o<=row);k++,o++)//向增加方向
            {
                if(cb.boardStatus[k][o]==color)
                {
                    chCont1++;
                }
                else break;
            }
            while((k<=col&&o<=row)&&(cb.boardStatus[k][o]==0))
            {
                spCont1++;
                k++;
                o++;
            }
            while((k<=col&&o<=row)&&(cb.boardStatus[k][o]==color))
            {
                k++;
                o++;
                chcont2++;
            }
            while((k<=col&&o<=row)&&(cb.boardStatus[k][o]==0))
            {
                spCont2++;
                k++;
                o++;
            }
            for (k=col-1,o=row-1;(k>=0&&o>=0);k--,o--)//从减少的方向
            {
                if(cb.boardStatus[k][o]==color)
                {
                    chCont1++;
                }
                else  break;
            }
            while((k>=0&&o>=0)&&(cb.boardStatus[k][o]==0))
            {
                spCont3++;
                k--;
                o--;
            }
            while((k>=0&&o>=0)&&(cb.boardStatus[k][o]==color))
            {
                chCont3++;
                k--;
                o--;
            }
            while ((k>=0&&o>=0)&&(cb.boardStatus[k][o]==0))
            {
                spCont4++;
                k--;
                o--;
            }
            break;
            case 4://右上到左下
                for (k=col+1,o=row-1;k<=cb.Clos&&o>=0;k++,o--)
                {
                    if (cb.boardStatus[k][o]==color)
                    {
                        chCont1++;
                    }
                    else break;
                }
                while(k<=cb.Clos&&o>=0&&(cb.boardStatus[k][o]==0))
                {
                    spCont1++;
                    k++;
                    o--;
                }
                while (k<=cb.Clos&&o>=0&&(cb.boardStatus[k][o]==color))
                {
                    chcont2++;
                    k++;
                    o--;
                }
                while(k<=cb.Clos&&o>=0&&(cb.boardStatus[k][o]==0))
                {
                    spCont2++;
                    k++;
                    o--;
                }
                for (k=col,o=row;k>=0&&o<=cb.Rows;k--,o++)
                {
                    if (cb.boardStatus[k][o]==color)
                    {
                        chCont1++;
                    }
                    else break;
                }
                while(k>=0&&o<=cb.Rows&&(cb.boardStatus[k][o]==0))
                {
                    spCont3++;
                    k--;
                    o++;
                }
                while(k>=0&&o<=cb.Rows&&(cb.boardStatus[k][o]==color))
                {
                    chCont3++;
                    k--;
                    o++;
                }
                while (k>=0&&o<=cb.Rows&&(cb.boardStatus[k][o]==0))
                {
                    spCont4++;
                    k--;
                    o++;
                }
                break;
        }
        if(chCont1+chcont2+chCont3+spCont1+spCont2+spCont3+spCont4>=5)
        {
            value=getValue(chCont1,chcont2,chCont3,spCont1,spCont2,spCont3,spCont4);
        }
        return value;

    }
    private int getValue(int ch1, int ch2, int ch3, int sp1, int sp2, int sp3, int sp4)
    {
        int value=0;
        switch (ch1)
        {
            case 5:
                value=Five;
                break;
            case 4:
                if(sp3>0&&sp1>0)//0AAAA0
                {
                    value=liveFour;
                }
                else if((sp3==0&&sp1>0)||(sp1==0&&sp3>0))
                {
                    value=chonFour;//0AAAAA
                }
                break;
            case 3:
                if (sp1==1&&sp3==1&&ch2>0&&ch3>0)//AOAAAOA
                {
                    value=liveFour;
                }
                else if ((sp1==1&&ch2>0)||(sp3==1&&ch3>0))//AAAOA
                {
                    value=chonFour;
                }
                else if ((sp1>0&&sp3>1)||(sp1>1&&sp3>0))//OOAAAO
                {
                    value=chonFour;
                }
                else
                    value=sleepThree;
                break;
            case 2:
               if(sp3==1&&sp1==1&&ch3>1&&ch2>1)//AAOAAOAA
               {
                   value=liveFour;
               }
               else if ((sp1==1&&ch2>1)||(sp3==1&&ch3>1))//AAOAA
               {
                   value=chonFour;
               }
               else if ((sp1==1&&ch2==1&&sp2>0&&sp3>0)||(sp1>0&&sp3==1&&ch3==1&&sp4>0))//OAAOAO||OAOAAO
               {
                   value=liveThree;
               }
               if ((sp1 > 0) && (sp3 > 0))
              {
                value=liveTow;
              }
              break;
            case 1:
                if ((sp1==1)&&(ch2>=3)||(sp3==1)&&(ch3>=3))//AOAAA
                {
                    value=chonFour;
                }
                else if (((sp1==1)&&(ch2==2)&&(sp2>0)&&(sp3>0))||(sp3==1)&&(ch3==2)&&(sp1>0)&&(sp4>0))
                {
                    value=liveTow;//OAOAAO
                }
                else if (((sp1==1)&&(ch2==2)&&(sp2>0)&&(sp3>0))||((sp3==1)&&(ch3==2)&&(sp4>0)&&(sp1>0)))
                {//OAOAAO

                value = sleepThree;

                }

                else if ((sp1==1)&&(ch2==1)&&(sp2>0)&&(sp3>0)||(sp3==1)&&(ch3==1)&&(sp4>0)&&(sp1>0))//OAOAOO
                {
                    value=liveTow;
                }
                break;
            default:
                value=0;
                break;

        }

        return value;


    }

    int[]  getTheBEstPOstion()
    {
        for (int i=0;i<=cb.Clos;i++)
        {
            for (int j=0;j<=cb.Rows;j++)
            {
                blackValue[i][j]=0;
                whiteValue[i][j]=0;
                if (cb.boardStatus[i][j]==0)
                {
                    for (int m=1;m<=4;m++)
                    {
                        blackValue[i][j]+=eval(1,i,j,m);
                        whiteValue[i][j]+=eval(1,i,j,m);
                    }
                }
            }
        }
        int maxValue=-LargeNumber;
        int value;
        int[] position=new int[2];
        int  valuePositions[][]=getTheMostValuablePositions();
        for (int i=0;i<valuePositions.length;i++)
        {
            if (valuePositions[i][2]>=Five)
            {
                position[0]=valuePositions[i][0];
                position[1]=valuePositions[i][1];
                break;
            }
            cb.boardStatus[valuePositions[i][0]][valuePositions[i][1]]=cb.computerColor;
            value=min(SearchDepth);
            cb.boardStatus[valuePositions[i][0]][valuePositions[i][1]]=0;
            if (value>maxValue)
            {
                maxValue=value;
                position[0]=valuePositions[i][0];
                position[1]=valuePositions[i][1];
            }
        }
        return position;

    }
    /*int[] getTheBestPostion()
    {
        for (int i=0;i<cb.Clos;i++)//初始化
        {
            for (int j=0;j<cb.Rows;j++)
            {
                blackValue[i][j] = 0;
                whiteValue[i][j] = 0;
                if (cb.boardStatus[i][j] == 0)//空格的价值
                {
                    for (int m = 1; m <= 4; m++) {
                        blackValue[i][j] = eval(2, i, j, m);
                        whiteValue[i][j] = eval(1, i, j, m);
                    }
                }
            }
        }
        int k=0;
        int[][] totalValue=new int[(cb.Clos+1)*(cb.Rows)+1][3];
        for(int i=0;i<=cb.Clos;i++)
        {
            for (int j=0;j<=cb.Rows;j++)
            {
                if (cb.boardStatus[i][j]==0)
                {
                    totalValue[k][0]=i;
                    totalValue[k][1]=j;
                    totalValue[k][2]=whiteValue[i][j]+blackValue[i][j]+staticValue[i][j];
                    k++;
                }
            }

        }
        sort(totalValue);
        k=1;
        int maxValue=totalValue[0][2];
        while(totalValue[k][2]==maxValue)//查找多个最大值
        {
            k++;
        }
        int r = (int)(Math.random()*k);//随机选择一个
        int [] position = new int[2];
        position[0]=totalValue[r][0];//横坐标
        position[1]=totalValue[r][1];//纵坐标
        return position;//返回位置
    }*/
    private int min(int searchDepth)
    {
        if (searchDepth==0)
        {
            return evaluateGame();
        }
        for (int i=0;i<=cb.Clos;i++)
        {
            for (int j=0;j<cb.Rows;j++)
            {
                whiteValue[i][j]=0;
                blackValue[i][j]=0;
                if (cb.boardStatus[i][j]==0)
                {
                    for (int t=1;t<=4;t++)
                    {
                        blackValue[i][j]+=eval(1,i,j,t);
                        whiteValue[i][j]+=eval(2,i,j,t);
                    }
                }
            }
        }
        int bestValue=LargeNumber;
        int  value;
        int valuablePositions[][]=getTheMostValuablePositions();
        for (int i=0;i<valuablePositions.length;i++)
        {
            if (cb.computerColor==1)//黑棋
            {
                if(whiteValue[valuablePositions[i][0]][valuablePositions[i][1]]>=Five)
                {
                    return -10*Five;
                }
            }
            else
            {
                if (blackValue[valuablePositions[i][0]][valuablePositions[i][1]]>=Five)
                {
                    return -10*Five;//
                }
            }
            cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=cb.computerColor==1?2:1;
            value=max(searchDepth-1);
            cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=0;
            if (value<=bestValue)
            {
                bestValue=value;
            }


        }
        return bestValue;
    }
    private int max(int searchDepth)
    {
        if (searchDepth==0)
        {
            return evaluateGame();
        }
        for (int i=0;i<=cb.Clos;i++)
        {
            for (int j=0;j<cb.Rows;j++)
            {
                whiteValue[i][j]=0;
                blackValue[i][j]=0;
                if (cb.boardStatus[i][j]==0)
                {
                    for (int t=1;t<=4;t++)
                    {
                        blackValue[i][j]+=eval(1,i,j,t);
                        whiteValue[i][j]+=eval(2,i,j,t);
                    }
                }
            }
        }
        int bestValue=LargeNumber;
        int  value;
        int valuablePositions[][]=getTheMostValuablePositions();
        for (int i=0;i<valuablePositions.length;i++)
        {
            if (cb.computerColor==1)//黑棋
            {
                if(whiteValue[valuablePositions[i][0]][valuablePositions[i][1]]>=Five)
                {
                    return 10*Five;
                }
            }
            else
            {
                if (blackValue[valuablePositions[i][0]][valuablePositions[i][1]]>=Five)
                {
                    return 10*Five;//
                }
            }
            cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=cb.computerColor==1?2:1;
            value=min(searchDepth-1);
            cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=0;
            if (value<=bestValue)
            {
                bestValue=value;
            }


        }
        return bestValue;
    }
    private void sort(int[][] totalVula)
    {
        for(int i=0;i<totalVula.length-1;i++)
        {
            for (int j=0;j<totalVula.length-1-i;j++)
            {
                int ti,tj,tvalue;
                if (totalVula[j][2]<totalVula[j+1][2])
                {
                    tvalue=totalVula[j][2];
                    totalVula[j][2]=totalVula[j+1][2];
                    totalVula[j+1][2]=tvalue;
                    ti=totalVula[j][0];
                    totalVula[j][0]=totalVula[j+1][0];
                    totalVula[j+1][0]=ti;
                    tj=totalVula[j][1];
                    totalVula[j][1]=totalVula[j+1][1];
                    totalVula[j+1][1]=tj;

                }
            }
        }
    }
   private int evaluateGame()
    {
        int value=0;
        int i,j,k;
        int [] line=new int[cb.Clos+1];
        //水平，对每一行估值
        for (j=0;j<=cb.Rows;j++)
        {
            for (i=0;i<=cb.Clos;i++)
            {
                line[i]=cb.boardStatus[i][j];
            }
            value+=evalLine(line,cb.Clos+1,1);//加上黑方的价值
            value-=evalLine(line,cb.Clos+1,2);//减去白方的价值
        }
        //对每一列估值
        for (i=0;i<=cb.Clos;i++)
        {
            for (j=0;j<=cb.Rows;j++)
            {
                line[j]=cb.boardStatus[i][j];
            }
            value+=evalLine(line,cb.Rows+1,1);
            value-=evalLine(line,cb.Rows+1,2);
        }
        //左下到右下斜线的估值
        for (j=4;j<=cb.Rows;j++)
        {
            for (k=0;k<=j;k++)
            {
                line[k]=cb.boardStatus[k][j-k];
            }
            value+=evalLine(line,j+1,1);
            value-=evalLine(line,j+1,2);
        }
        for (j=1;j<=cb.Rows-4;j++)
        {
            for (k=0;k<=cb.Clos-j;k++)
            {
                line[k]=cb.boardStatus[k+j][cb.Rows-k];
            }
            value+=evalLine(line,cb.Rows+1-j,1);
            value-=evalLine(line,cb.Clos+1-j,2);
        }
        //左上到右下斜线估值
        for (j=0;j<=cb.Rows-4;j++)//不够四
        {
            for (k=0;k<=cb.Rows-j;k++)
            {
                line[k]=cb.boardStatus[k][k+j];
            }
            value+=evalLine(line,cb.Rows+1-j,1);
            value-=evalLine(line,cb.Rows+1-j,2);
        }
        for(i=1;i<=cb.Clos-4;i++)
        {
            for (k=0;k<=cb.Rows-i;k++)
            {
                line[k]=cb.boardStatus[k+i][k];
            }
            value+=evalLine(line,cb.Rows+1-i,1);
            value-=evalLine(line,cb.Rows+1-i,2);
        }
        if (cb.computerColor==1)
        {
            return value;
        }
        else
        {
            return -value;
        }

    }
    int evalLine(int lin[],int num,int color)
    {
        int chess,space1,space2;
        int i,j,k;
        int value=0;
        int begin,end;
        for(i=0;i<num;i++)
            if(lin[i]==color)//相应的颜色
            {
                chess = 1;
                begin = i;
                for (j = begin + 1; (j < num) && (lin[j] == color); j++) {
                    chess++;
                }
                if (chess < 2) {
                    continue;
                }
                end = j - 1;
                space1 = 0;
                space2 = 0;
                for (j = begin - 1; (j < num) && ((lin[j] == 0) || (lin[j] == color)); j++) {
                    space1++;
                }
                for (j = end + 1; (j < num) && ((lin[j] == 0) || (lin[j] == color)); j++) {
                    space2++;
                }
                if (chess + space1 + space2 >= 5) {
                    value += getValue(chess, space1, space2);
                }
                i = end + 1;
            }
            return value;


    }
    private  int getValue(int chessCount,int spaceCount1,int spaceCount2)
    {
        int value=0;
        switch (chessCount)
        {

                case 5:
                    value = Five;
                    break;
                case 4:
                    if( (spaceCount1>0) && (spaceCount2>0) )
                    {
                        value = liveFour;
                    }
                    else
                    {
                        value = chonFour;
                    }
                    break;
                case 3:
                    if( (spaceCount1>0) && (spaceCount2>0) )
                    {
                        value = liveThree;
                    }
                    else
                    {
                        value = sleepThree;
                    }
                    break;
                case 2:
                    if( (spaceCount1>0) && (spaceCount2>0) ) {
                            value=liveTow;
                    }
                    break;
                default:
                    value = 0;
                    break;
            }
        return value;
        }
    private int[][] getTheMostValuablePositions()
    {
        int i,j,k=0;
        int[][] allValue=new int[(cb.Clos+1)*(cb.Rows+1)][3];
        for (i=0;i<cb.Rows;i++)
        {
            for (j=0;j<=cb.Rows;j++)
            {
                if(cb.boardStatus[i][j]==0) {
                    allValue[k][0] = i;
                    allValue[k][1] = j;
                    allValue[k][2] = blackValue[i][j] + whiteValue[i][j] + staticValue[i][j];
                    k++;
                }
            }
        }
        sort(allValue);
        int size=k<SampleNumber?k:SampleNumber;
        int valuablePositions[][]=new int[size][3];
        for (i=0;i<size;i++)
        {
            valuablePositions[i][0]=allValue[i][0];
            valuablePositions[i][1]=allValue[i][1];
            valuablePositions[i][2]=allValue[i][2];

        }
        return  valuablePositions;

    }

}