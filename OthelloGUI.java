package UI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
public class OthelloGUI extends JFrame implements ActionListener {
	JPanel[] row=new JPanel[9];
	JButton[][] buttons=new JButton[8][8];
	Dimension blockdimDimension=new Dimension(40,40);
	Dimension newgamed=new Dimension(100,45);
	JTextArea display = new JTextArea(2,20);
	int[] xDir = {-1,-1,-1,0,1,1,1,0};
	int yDir[] = {-1,0,1,1,1,0,-1,-1};
	Color player1color=Color.BLACK;
	Color player2color=Color.WHITE;
	JButton newgame;
	Font font = new Font("Times new Roman", Font.BOLD, 14); 
	boolean player1turn;
	boolean player2turn;
	OthelloGUI()
	{

		super("Othello");
		setDesign();
		player1turn=true;
		player2turn=false;
		setSize(480,480);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid=new GridLayout(9,9);
		setLayout(grid);
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
		FlowLayout f2 = new FlowLayout(FlowLayout.LEFT,1,1);
		row[0]=new JPanel();
		display.setText("Player1 turn");
		display.setEditable(false);
		display.setBackground(Color.white);
		display.setFont(font);
		row[0].add(display);
		newgame=new JButton();
		newgame.setPreferredSize(newgamed);
		newgame.setBackground(Color.gray);
		newgame.setText("New Game");
		newgame.addActionListener(this);
		row[0].add(newgame);
		row[0].setLayout(f2);
		add(row[0]);
		for(int i=1;i<row.length;i++)
		{
			row[i]=new JPanel();
			row[i].setLayout(f1);
		}
		int rownum=1;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				buttons[i][j]=new JButton();
				buttons[i][j].setPreferredSize(blockdimDimension);
				buttons[i][j].setBackground(Color.gray);
				buttons[i][j].addActionListener(this);
				row[rownum].add(buttons[i][j]);

			}
			add(row[rownum]);
			rownum++;

		}
		setNew();
		setVisible(true);
	}
	private void setNew() {
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				buttons[i][j].setBackground(Color.gray);
			}
		}
		player1turn=true;
		player2turn=false;
		display.setText("Player1 turn");
		buttons[3][3].setBackground(Color.WHITE);
		buttons[4][4].setBackground(Color.WHITE);
		buttons[3][4].setBackground(Color.BLACK);
		buttons[4][3].setBackground(Color.BLACK);

	}
	private void setDesign() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {   
		}	
	}
	public static void main(String[] args) {

		OthelloGUI newgame=new OthelloGUI();
	}
	public boolean move(Color playercolor, int x, int y){
		if(!check(playercolor,x,y))
		{
			return false;
		}

		for(int i = 0; i<xDir.length; i++){
			int stepX = xDir[i];
			int stepY = yDir[i];
			int currentX = x + stepX;
			int currentY = y + stepY;
			if(currentX>=0&&currentX<=7 && currentY>=0&&currentY<=7&&buttons[currentX][currentY].getBackground()!=playercolor)
			{
			while(currentX>=0&&currentX<=7 && currentY>=0&&currentY<=7){
				if(buttons[currentX][currentY].getBackground()==Color.gray){
					break;
				}else if(buttons[currentX][currentY].getBackground()==playercolor){

					conversion(playercolor,x,y,i);

				}
					currentX =currentX + stepX;
					currentY = currentY+ stepY;
				

			}
			}

		}
		return true;
	}
	private boolean check(Color playerColor,int x,int y) {
		if(buttons[x][y].getBackground()!=Color.gray)
		{
			return false;
		}
		JButton[] allowed=getOptions(playerColor);
		for(int i=0;i<allowed.length;i++)
		{
			if(buttons[x][y]==allowed[i]){
				return true;
			}
		}
		return false;
	}
	private void conversion(Color playercolor, int x,int y,int i) {
		int stepX = xDir[i];
		int stepY = yDir[i];
		buttons[x][y].setBackground(playercolor);
		int currentX = x + stepX;
		int currentY = y + stepY;
		while(currentX>=0&&currentX<=7 && currentY>=0&&currentY<=7 && buttons[currentX][currentY].getBackground()!=playercolor)
		{
			buttons[currentX][currentY].setBackground(playercolor);
			currentX=currentX+stepX;
			currentY=currentY+stepY;
		}
		return;
	}
private JButton[] getOptions(Color playercolor) {
		JButton[] tobereturned;
		ArrayList<JButton> temp=new ArrayList<>();
		for(int i=0;i<=7;i++)
		{
			for(int j=0;j<=7;j++)
			{
				if(buttons[i][j].getBackground()==Color.gray)
				{
				for(int k = 0; k<xDir.length; k++){
					int stepX = xDir[k];
					int stepY = yDir[k];
					int currentX = i + stepX;
					int currentY = j + stepY;
					if(currentX>=0&&currentX<=7 && currentY>=0&&currentY<=7&&buttons[currentX][currentY].getBackground()!=playercolor){
					while(currentX>=0&&currentX<=7 && currentY>=0&&currentY<=7){
						if(buttons[currentX][currentY].getBackground()==Color.gray){
							break;
						}else if(buttons[currentX][currentY].getBackground()==playercolor){
							temp.add(buttons[i][j]);
							k=9;
							break;
						}else{
							currentX =currentX + stepX;
							currentY = currentY+ stepY;
						}

					}
					}
				}
			}
			}
		}
		tobereturned=new JButton[temp.size()];
		int k=0;
		for(int i=0;i<temp.size();i++)
		{
			tobereturned[k]=temp.get(i);
			k++;
		}
		return tobereturned;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==newgame)
		{
			setNew();
		}
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(e.getSource()==buttons[i][j])
				{
					if(player1turn)
					{
						if(move(player1color,i,j))
						{
							if(getOptions(player2color).length==0)
							{
								if(getOptions(player1color).length!=0)
								{
								display.setText("Player 2 turn notpossible. Player 1 turn");
								}
								else
								getwinner();
							}
							else
							{
								display.setText("Player2turn");
								player2turn=true;
								player1turn=false;
							}
						}
					}
					else
					if(player2turn)
					{
						if(move(player2color,i,j))
						{
							if(getOptions(player1color).length==0)
							{
								if(getOptions(player2color).length!=0)
								{
								display.setText("Player 1 turn notpossible.Player 2 turn");
								}
								else
								getwinner();
							}
							else
							{
								display.setText("Player1turn");
								player1turn=true;
								player2turn=false;
							}
						}
					}
				}
//				JButton x[]=getOptions(player1color);
//				System.out.println(check(player1color, i, j));
//				System.out.println(x.length);
//				conversion(player1color,i, j,5);
//				getwinner();
//					move(player1color,i,j);
				}
			
		}
	}
	private void getwinner() {
		int black=0,white=0;
		for(int i=0;i<=7;i++)
		{
			for(int j=0;j<=7;j++)
			{
				if(buttons[i][j].getBackground()==Color.black)
				{
					black++;
				}
				else
					if(buttons[i][j].getBackground()==Color.white)
					{
						white++;
					}
			}
		}
			if(black>white)
			{
				display.setText("Player 1 wins");
			}
			else
				if(black<white)
				{
					display.setText("Player 2 wins");
				}
				else
					display.setText("Draw");
		}
			
}
