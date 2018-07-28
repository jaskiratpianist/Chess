import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;
class GameGui
{
	JFrame f;
	JPanel mainP,boardP,sidePR,whiteG,blackG,sidePL;
	
	ImageIcon pawn_white,bishop_white,king_white,queen_white,rook_white,knight_white
				,pawn_black,bishop_black,king_black,queen_black,rook_black,knight_black;
	
	JButton pp[][],bl[][],wh[][],resetB,p1,p2,time;
	
	GameEng ge;
	
	GameGui()
	{
		ge=new GameEng(this);
		f=new JFrame("Chess");
		mainP=new JPanel(new BorderLayout());
		boardP=new JPanel(new GridLayout(8,8));
		pp=new JButton[8][8];
		sidePR=new JPanel(new BorderLayout());
		sidePL=new JPanel(new BorderLayout());
		whiteG=new JPanel(new GridLayout(8,2));
		blackG=new JPanel(new GridLayout(8,2));
		resetB=new JButton("RESET");
		resetB.addActionListener(ge);
		resetB.setBackground(new Color(220,20,60));
		time=new JButton("00:00:00");
		time.setEnabled(false);
		javax.swing.JLabel nameTag=new javax.swing.JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Game Developed By Jaskiat Singh~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		p1=new JButton("Player1(White)");
		p2=new JButton("Player2(Black)");
		p1.setEnabled(false);
		p2.setEnabled(false);
		p1.setBorderPainted(false);
		p2.setBorderPainted(false);
		p1.setBackground(Color.GREEN);
		
		drawBoard();//for drawing the board panel
		initializeChessPieceImageIcon();
		
		mainP.add(boardP,BorderLayout.CENTER);
		mainP.add(sidePR,BorderLayout.EAST);
		mainP.add(sidePL,BorderLayout.WEST);
		mainP.add(nameTag,BorderLayout.SOUTH);
		
		grave();
		
		sidePL.add(whiteG,BorderLayout.CENTER);
		sidePR.add(blackG,BorderLayout.CENTER);
		
		sidePR.add(resetB,BorderLayout.SOUTH);
		sidePL.add(time,BorderLayout.SOUTH);
		
		sidePL.add(p1,BorderLayout.NORTH);
		sidePR.add(p2,BorderLayout.NORTH);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(mainP);
		f.setVisible(true);
		f.setSize(950,700);
	}
	
	public void initializeChessPieceImageIcon()
	{
		//white
		pawn_white=new ImageIcon("images//pawn_white.png");
		bishop_white=new ImageIcon("images//bishop_white.png");
		king_white=new ImageIcon("images//king_white.png");
		queen_white=new ImageIcon("images//queen_white.png");
		rook_white=new ImageIcon("images//rook_white.png");
		knight_white=new ImageIcon("images//knight_white.png");
		
		//black
		pawn_black=new ImageIcon("images//pawn_black.png");
		bishop_black=new ImageIcon("images//bishop_black.png");
		king_black=new ImageIcon("images//king_black.png");
		queen_black=new ImageIcon("images//queen_black.png");
		rook_black=new ImageIcon("images//rook_black.png");
		knight_black=new ImageIcon("images//knight_black.png");
		
		setDefaultPos();
	}
	
	public void grave()
	{
		wh=new JButton[2][8];
		bl=new JButton[2][8];
		
		int count=0;
		while(count<8)
		{	
			for(int i=0;i<2;i++)
			{
				wh[i][count]=new JButton();
				bl[i][count]=new JButton();
				wh[i][count].setBackground(Color.WHITE);
				bl[i][count].setBackground(Color.WHITE);
				
				wh[i][count].setPreferredSize(new Dimension(50,50));
				bl[i][count].setPreferredSize(new Dimension(50,50));
				
				whiteG.add(wh[i][count]);
				blackG.add(bl[i][count]);
			}
			count=count+1;
		}
	}
	
	public void setDefaultPos()
	{
		//white
		pp[0][0].setIcon(rook_white);
		pp[0][7].setIcon(rook_white);
		pp[0][1].setIcon(knight_white);
		pp[0][6].setIcon(knight_white);
		pp[0][2].setIcon(bishop_white);
		pp[0][5].setIcon(bishop_white);
		pp[0][3].setIcon(queen_white);
		pp[0][4].setIcon(king_white);
		for(int i=0;i<=7;i++)
		{
			pp[1][i].setIcon(pawn_white);
		}
		
		//black
		pp[7][0].setIcon(rook_black);
		pp[7][7].setIcon(rook_black);
		pp[7][1].setIcon(knight_black);
		pp[7][6].setIcon(knight_black);
		pp[7][2].setIcon(bishop_black);
		pp[7][5].setIcon(bishop_black);
		pp[7][3].setIcon(queen_black);
		pp[7][4].setIcon(king_black);
		for(int i=0;i<=7;i++)
		{
			pp[6][i].setIcon(pawn_black);
		}
	}
	
	public void drawBoard()
	{
		for(int i=0;i<pp.length;i++)
		{
			for(int j=0;j<pp[i].length;j++)
			{
				pp[i][j]=new JButton();
				if(i%2==0)
				{	
					if(j%2==0)
					pp[i][j].setBackground(Color.WHITE);
					
					if(j%2!=0)
					pp[i][j].setBackground(new Color(196,184,168));
				}
				else
				{
					if(j%2==0)
					pp[i][j].setBackground(new Color(196,184,168));
				
					if(j%2!=0)
					pp[i][j].setBackground(Color.WHITE);
				}					
				pp[i][j].addActionListener(ge);
				pp[i][j].setBorderPainted(false);
				//pp[i][j].setEnabled(false);
				boardP.add(pp[i][j]);
			}
		}
	}
}