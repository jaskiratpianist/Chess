import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
class GameEng implements ActionListener
{
	GameGui gg;
	int currentPos[]={-1,-1};//safe number
	Color VOL=new Color(179,120,211);
	Color RED=new Color(220,20,60);
	Color GREEN=new Color(33,136,104);
	int gameCounter=0;
	
	GameEng(GameGui gg)
	{
		this.gg=gg;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==gg.resetB)
		{
			restoreBoard();
			for(int i=0;i<gg.pp.length;i++)
			{
				for(int j=0;j<gg.pp[i].length;j++)
				{
					gg.pp[i][j].setIcon(null);
				}
			}	
			gg.setDefaultPos();
			currentPos[0]=-1;
			currentPos[1]=-1;
			gameCounter=0;
			gg.p1.setBackground(Color.GREEN);
			gg.p2.setBackground(null);
			
			for(int i=0;i<gg.wh.length;i++)
			{
				for(int j=0;j<gg.wh[i].length;j++)
				{
					gg.wh[i][j].setIcon(null);
					gg.bl[i][j].setIcon(null);
				}
			}
			
			return;
		}
		
		for(int i=0;i<gg.pp.length;i++)
		{
			for(int j=0;j<gg.pp[i].length;j++)	
			{
				if(e.getSource()==gg.pp[i][j] && gg.pp[i][j].getBackground()==GREEN)//deselect the selected board
				{
					restoreBoard();
					currentPos[0]=-1;
					currentPos[1]=-1;
					return;
				}
				
				if(e.getSource()==gg.pp[i][j] && gg.pp[i][j].getIcon()!=null && gg.pp[i][j].getBackground()!=RED)//select the piece and draw its path
				{
					restoreBoard();//in case if any piece is already selected and its path is drawn ,it will restoreBoard after reselect of another piece
					checkPiece(i,j);
					currentPos[0]=i;//waring! dont try to reset!!. can mess up whole program
					currentPos[1]=j;
				}
				if(e.getSource()==gg.pp[i][j] && gg.pp[i][j].getIcon()==null && gg.pp[i][j].getBackground()!=VOL)//reset board if click on empty space
				{
					restoreBoard();
					currentPos[0]=-1;
					currentPos[1]=-1;
				}
				if(e.getSource()==gg.pp[i][j] && gg.pp[i][j].getBackground()==VOL)//move the selected piece
				{
					gg.pp[i][j].setIcon(gg.pp[currentPos[0]][currentPos[1]].getIcon());
					gg.pp[currentPos[0]][currentPos[1]].setIcon(null);
					
					restoreBoard();
					currentPos[0]=-1;
					currentPos[1]=-1;
					gameCounter=gameCounter+1;//even for white and odd for black
					if(gameCounter%2==0)
					{	
						gg.p1.setBackground(Color.GREEN);
						gg.p2.setBackground(null);
					}
					if(gameCounter%2!=0)
					{	
						gg.p2.setBackground(Color.GREEN);
						gg.p1.setBackground(null);
					}
				}
				if(e.getSource()==gg.pp[i][j] && gg.pp[i][j].getBackground()==RED && gg.pp[i][j].getIcon()!=null)
				{
					//more work required
					int selectedPos[]={currentPos[0],currentPos[1]},targetPos[]={i,j};
					exterminatePiece(selectedPos,targetPos);
					gg.pp[i][j].setIcon(gg.pp[currentPos[0]][currentPos[1]].getIcon());
					gg.pp[currentPos[0]][currentPos[1]].setIcon(null);
					
					gameCounter=gameCounter+1;
					restoreBoard();
					currentPos[0]=-1;
					currentPos[1]=-1;
					if(gameCounter%2==0)
					{	
						gg.p1.setBackground(Color.GREEN);
						gg.p2.setBackground(null);
					}
					if(gameCounter%2!=0)
					{	
						gg.p2.setBackground(Color.GREEN);
						gg.p1.setBackground(null);
					}
				}
			}
		}
	}
	
	public void checkPiece(int yAxis,int xAxis)
	{
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.pawn_white)//need more work
		{
			
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				if(yAxis+1<gg.pp.length && gg.pp[yAxis+1][xAxis].getIcon()==null)
				{	
					if(gameCounter==0)
					{	
						gg.pp[yAxis+1][xAxis].setBackground(VOL);
						gg.pp[yAxis+1][xAxis].setBorderPainted(true);
						gg.pp[yAxis+2][xAxis].setBackground(VOL);
						gg.pp[yAxis+2][xAxis].setBorderPainted(true);
					}
					else
					{
						gg.pp[yAxis+1][xAxis].setBackground(VOL);
						gg.pp[yAxis+1][xAxis].setBorderPainted(true);
					}
				}
				if(xAxis+1<8 && yAxis+1<8 && getColorSignature(yAxis+1,xAxis+1)=="BLACK" && gg.pp[yAxis+1][xAxis+1].getIcon()!=null)
				{
					markMove(yAxis+1,xAxis+1,"WHITE");
				}
				if(xAxis>0 && yAxis+1<8 && getColorSignature(yAxis+1,xAxis-1)=="BLACK" && gg.pp[yAxis+1][xAxis-1].getIcon()!=null)
				{
					markMove(yAxis+1,xAxis-1,"WHITE");
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.pawn_black)//need more work
		{
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))	
			{	
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				if(yAxis>0 && gg.pp[yAxis-1][xAxis].getIcon()==null)
				{	
					if(gameCounter==1)
					{	
						gg.pp[yAxis-1][xAxis].setBackground(VOL);
						gg.pp[yAxis-1][xAxis].setBorderPainted(true);
						gg.pp[yAxis-2][xAxis].setBackground(VOL);
						gg.pp[yAxis-2][xAxis].setBorderPainted(true);
					}
					else
					{
						gg.pp[yAxis-1][xAxis].setBackground(VOL);
						gg.pp[yAxis-1][xAxis].setBorderPainted(true);
					}
				}
				if(xAxis+1<8 && yAxis>0 && xAxis+1<gg.pp[yAxis-1].length && getColorSignature(yAxis-1,xAxis+1)=="WHITE" && gg.pp[yAxis-1][xAxis+1].getIcon()!=null)
				{
					markMove(yAxis-1,xAxis+1,"BLACK");
				}
				if(xAxis>0 && yAxis>0 && getColorSignature(yAxis-1,xAxis-1)=="WHITE" && gg.pp[yAxis-1][xAxis-1].getIcon()!=null)
				{
					markMove(yAxis-1,xAxis-1,"BLACK");
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.rook_white || gg.pp[yAxis][xAxis].getIcon()==gg.rook_black)
		{
			String colorSig="";
			if(gg.pp[yAxis][xAxis].getIcon()==gg.rook_black)
			{
				colorSig="BLACK";
			}
			if(gg.pp[yAxis][xAxis].getIcon()==gg.rook_white)
			{
				colorSig="WHITE";
			}
			
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				for(int i=yAxis+1;i<gg.pp.length;i++)
				{
				
					boolean breakFlag=markMove(i,xAxis,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis-1;i>=0;i--)
				{
					boolean breakFlag=markMove(i,xAxis,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis+1;i<gg.pp[yAxis].length;i++)
				{
					boolean breakFlag=markMove(yAxis,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis-1;i>=0;i--)
				{
					boolean breakFlag=markMove(yAxis,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.bishop_white || gg.pp[yAxis][xAxis].getIcon()==gg.bishop_black)
		{
			String colorSig="";
			if(gg.pp[yAxis][xAxis].getIcon()==gg.bishop_black)
			{
				colorSig="BLACK";
			}
			if(gg.pp[yAxis][xAxis].getIcon()==gg.bishop_white)
			{
				colorSig="WHITE";
			}
			
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{	
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				//diagonal
				for(int i=yAxis+1,j=xAxis+1;i<gg.pp.length && j<gg.pp[yAxis].length;i++,j++)//lower half
				{
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis-1,j=xAxis-1;i>=0 && j>=0;i--,j--)//upper half
				{
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis+1,j=xAxis-1;i<gg.pp.length && j>=xAxis-gg.pp[yAxis].length;i++,j--)//lower half:-where y icri x dcri
				{
					if(i<=-1 || j<=-1)//to avoid ArrayIndexOutOfBoundsException -1
					{
						break;
					}
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis+1,j=yAxis-1;i<gg.pp[yAxis].length && j>yAxis-gg.pp.length;i++,j--)//upper half:-where y icri x dcri
				{
					if(i<=-1 || j<=-1)//to avoid ArrayIndexOutOfBoundsException -1
					{
						break;
					}
					boolean breakFlag=markMove(j,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.queen_white || gg.pp[yAxis][xAxis].getIcon()==gg.queen_black)
		{
			String colorSig="";
			if(gg.pp[yAxis][xAxis].getIcon()==gg.queen_black)
			{
				colorSig="BLACK";
			}
			if(gg.pp[yAxis][xAxis].getIcon()==gg.queen_white)
			{
				colorSig="WHITE";
			}
			
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{	
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				//diagonal
				for(int i=yAxis+1,j=xAxis+1;i<gg.pp.length && j<gg.pp[yAxis].length;i++,j++)//lower half
				{
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis-1,j=xAxis-1;i>=0 && j>=0;i--,j--)//upper half
				{
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis+1,j=xAxis-1;i<gg.pp.length && j>=xAxis-gg.pp[yAxis].length;i++,j--)//lower half:-where y icri x dcri
				{
					if(i<=-1 || j<=-1)//to avoid ArrayIndexOutOfBoundsException -1
					{
						break;
					}
					boolean breakFlag=markMove(i,j,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis+1,j=yAxis-1;i<gg.pp[yAxis].length && j>yAxis-gg.pp.length;i++,j--)//upper half:-where y icri x dcri
				{
					if(i<=-1 || j<=-1)//to avoid ArrayIndexOutOfBoundsException -1
					{
						break;
					}
					boolean breakFlag=markMove(j,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				
				//horizontel and vertical
				for(int i=yAxis+1;i<gg.pp.length;i++)
				{
					boolean breakFlag=markMove(i,xAxis,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=yAxis-1;i>=0;i--)
				{
					boolean breakFlag=markMove(i,xAxis,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis+1;i<gg.pp[yAxis].length;i++)
				{
					boolean breakFlag=markMove(yAxis,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
				for(int i=xAxis-1;i>=0;i--)
				{	
					boolean breakFlag=markMove(yAxis,i,colorSig);
					if(breakFlag==true)
					{
						break;
					}
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.knight_white || gg.pp[yAxis][xAxis].getIcon()==gg.knight_black)
		{
			String colorSig="";
			if(gg.pp[yAxis][xAxis].getIcon()==gg.knight_black)
			{
				colorSig="BLACK";
			}
			if(gg.pp[yAxis][xAxis].getIcon()==gg.knight_white)
			{
				colorSig="WHITE";
			}
			
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{	
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				int cox[][]={{2,1,-2,-1,2,1,-2,-1},{1,2,-1,-2,-1,-2,1,2}};
				for(int i=0,j=0;i<cox[0].length && j<cox[1].length;i++,j++)
				{	
					if((yAxis+cox[0][i])<gg.pp.length && (xAxis+cox[1][j])<gg.pp[yAxis].length && (xAxis+cox[1][j])>-1 && (yAxis+cox[0][i])>-1)
					{	
						markMove((yAxis+cox[0][i]),(xAxis+cox[1][j]),colorSig);
					}
				}
			}
		}
		
		if(gg.pp[yAxis][xAxis].getIcon()==gg.king_white || gg.pp[yAxis][xAxis].getIcon()==gg.king_black)
		{
			String colorSig="";
			if(gg.pp[yAxis][xAxis].getIcon()==gg.king_black)
			{
				colorSig="BLACK";
			}
			if(gg.pp[yAxis][xAxis].getIcon()==gg.king_white)
			{
				colorSig="WHITE";
			}
			
			int cox[][]={{0,0,1,-1,1,-1,1,-1},{1,-1,0,0,1,-1,-1,1}};
			if((getColorSignature(yAxis,xAxis)=="WHITE" && gameCounter%2==0) || (getColorSignature(yAxis,xAxis)=="BLACK" && gameCounter%2!=0))
			{	
				gg.pp[yAxis][xAxis].setBackground(GREEN);
				for(int i=0,j=0;i<cox[0].length && j<cox[1].length;i++,j++)
				{	
					if((yAxis+cox[0][i])<gg.pp.length && (xAxis+cox[1][j])<gg.pp[yAxis].length && (xAxis+cox[1][j])>-1 && (yAxis+cox[0][i])>-1)
					{	
						markMove((yAxis+cox[0][i]),(xAxis+cox[1][j]),colorSig);
					}
				}
			}
		}
	}
	
	public boolean markMove(int y,int x,String colorSig)
	{
		if(gg.pp[y][x].getIcon()!=null)
		{
			if((colorSig=="WHITE" && getColorSignature(y,x)=="BLACK") || (colorSig=="BLACK" && getColorSignature(y,x)=="WHITE"))
			{
				gg.pp[y][x].setBackground(RED);	
				gg.pp[y][x].setBorderPainted(true);
				return true;
			}
			else
			{
				//terminate loop and dont change bgcolor(reason because of same colorcode of piece)
				return true;
			}
		}
		else
		{	
			gg.pp[y][x].setBackground(VOL);	
			gg.pp[y][x].setBorderPainted(true);	
			return false;
		}
	}
	
	public void exterminatePiece(int selected[],int target[])
	{
		if(getColorSignature(target[0],target[1])=="BLACK")
		{
			if(gg.pp[target[0]][target[1]].getIcon()==gg.pawn_black)
			{
				for(int i=0;i<gg.bl[0].length;i++)	
				if(gg.bl[0][i].getIcon()==null)	
				{	
					gg.bl[0][i].setIcon(gg.pawn_black);
					break;
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.rook_black)
			{
				if(gg.bl[1][0].getIcon()==null)	
				{	
					gg.bl[1][0].setIcon(gg.rook_black);
				}
				else
				{
					gg.bl[1][7].setIcon(gg.rook_black);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.knight_black)
			{
				if(gg.bl[1][1].getIcon()==null)	
				{	
					gg.bl[1][1].setIcon(gg.knight_black);
				}
				else
				{
					gg.bl[1][6].setIcon(gg.knight_black);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.bishop_black)
			{
				if(gg.bl[1][2].getIcon()==null)	
				{	
					gg.bl[1][2].setIcon(gg.bishop_black);
				}
				else
				{
					gg.bl[1][5].setIcon(gg.bishop_black);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.king_black)
			{
				gg.bl[1][3].setIcon(gg.king_black);
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.queen_black)
			{
				gg.bl[1][4].setIcon(gg.queen_black);
			}
		}
		
		if(getColorSignature(target[0],target[1])=="WHITE")
		{
			if(gg.pp[target[0]][target[1]].getIcon()==gg.pawn_white)
			{
				for(int i=0;i<gg.bl[0].length;i++)	
				if(gg.wh[1][i].getIcon()==null)	
				{	
					gg.wh[1][i].setIcon(gg.pawn_white);
					break;
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.rook_white)
			{
				if(gg.wh[0][0].getIcon()==null)	
				{	
					gg.wh[0][0].setIcon(gg.rook_white);
				}
				else
				{
					gg.wh[0][7].setIcon(gg.rook_white);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.knight_white)
			{
				if(gg.wh[0][1].getIcon()==null)	
				{	
					gg.wh[0][1].setIcon(gg.knight_white);
				}
				else
				{
					gg.wh[0][6].setIcon(gg.knight_white);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.bishop_white)
			{
				if(gg.wh[0][2].getIcon()==null)	
				{	
					gg.wh[0][2].setIcon(gg.bishop_white);
				}
				else
				{
					gg.wh[0][5].setIcon(gg.bishop_white);
				}
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.king_white)
			{
				gg.wh[0][3].setIcon(gg.king_white);
			}
			if(gg.pp[target[0]][target[1]].getIcon()==gg.queen_white)
			{
				gg.wh[0][4].setIcon(gg.queen_white);
			}
		}
	}
	
	public String getColorSignature(int y,int x)
	{
		Icon ii=gg.pp[y][x].getIcon();
		if(ii==gg.king_black||ii==gg.queen_black||ii==gg.bishop_black||ii==gg.knight_black||ii==gg.rook_black||ii==gg.pawn_black)
		{
			return "BLACK";
		}
		else
		{
			return "WHITE";
		}
	}
	
	public void restoreBoard()
	{
		for(int i=0;i<gg.pp.length;i++)
		{
			for(int j=0;j<gg.pp[i].length;j++)
			{
				if(i%2==0)
				{	
					if(j%2==0)
					gg.pp[i][j].setBackground(Color.WHITE);
				
					if(j%2!=0)
					gg.pp[i][j].setBackground(new Color(196,184,168));
				}
				else
				{
					if(j%2==0)
					gg.pp[i][j].setBackground(new Color(196,184,168));
				
					if(j%2!=0)
					gg.pp[i][j].setBackground(Color.WHITE);
				}
				gg.pp[i][j].setBorderPainted(false);
			}
		}
	}
}