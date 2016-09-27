package data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nilstes
 */
public class Game implements Serializable {
    private String gameId;
    private Date startedAt;
    private Date lastMoveAt;
    private int numMoves = 0;
    private String inviter;
    private String invitee;
    private boolean inviteAccepted = false;
    private boolean inviteCommunicated = false;
    private String turn;
    private String winner;
    private Player[][] board;
    private int squares;
    
    public Game() {
    }

    public Game(String gameId, String inviter, String invitee, int squares) {
        board = new Player[squares][];
        for(int x=0; x<squares; x++) {
            board[x] = new Player[squares];
            for(int y=0; y<squares; y++) {
                board[x][y] = Player._;
            }         
        }
        this.gameId = gameId;
        this.inviter = inviter;
        this.invitee = invitee;
        this.squares = squares;
        this.startedAt = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isInviteAccepted() {
        return inviteAccepted;
    }

    public void setInviteAccepted(boolean inviteAccepted) {
        this.inviteAccepted = inviteAccepted;
    }

    public boolean isInviteCommunicated() {
        return inviteCommunicated;
    }

    public void setInviteCommunicated(boolean inviteCommunicated) {
        this.inviteCommunicated = inviteCommunicated;
    }

    public int getSquares() {
        return squares;
    }

    public void setSquares(int squares) {
        this.squares = squares;
    }
    
    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getLastMoveAt() {
        return lastMoveAt;
    }

    public void setLastMoveAt(Date lastMoveAt) {
        this.lastMoveAt = lastMoveAt;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String lastMoveBy) {
        this.turn = lastMoveBy;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Player[][] getBoard() {
        return board;
    }

    public void setBoard(Player[][] board) {
        this.board = board;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public void setNumMoves(int numMoves) {
        this.numMoves = numMoves;
    }

    public void addMove(int x, int y, String player) {
        board[y][x] = inviter.equals(player) ? Player.X : Player.O;
        turn = inviter.equals(player) ? invitee : inviter;
        lastMoveAt = new Date();
        numMoves++;
        if(isWin(x, y)) {
            setWinner(player);
        }
    }

    public boolean isWin(int x, int y) {
        return isHorizontalWin(x, y) || 
                isVerticalWin(x, y) || 
                isMainDiagonalWin(x, y) ||
                isBiDiagonalWin(x, y);
    }

    public boolean isHorizontalWin(int x, int y) {
        return isTripleEqualAndInRange(x-2, y, x-1, y, x, y) || 
                isTripleEqualAndInRange(x-1, y, x, y, x+1, y) || 
                isTripleEqualAndInRange(x, y, x+1, y, x+2, y);
    }
    
    public boolean isVerticalWin(int x, int y) {
        return isTripleEqualAndInRange(x, y-2, x, y-1, x, y) || 
                isTripleEqualAndInRange(x, y-1, x, y, x, y+1) || 
                isTripleEqualAndInRange(x, y, x, y+1, x, y+2);
    }
          
    public boolean isMainDiagonalWin(int x, int y) {
        return isTripleEqualAndInRange(x-2, y-2, x-1, y-1, x, y) || 
                isTripleEqualAndInRange(x-1, y-1, x, y, x+1, y+1) || 
                isTripleEqualAndInRange(x, y, x+1, y+1, x+2, y+2);
    }
        
    public boolean isBiDiagonalWin(int x, int y) {
        return isTripleEqualAndInRange(x-2, y+2, x-1, y+1, x, y) || 
                isTripleEqualAndInRange(x-1, y+1, x, y, x+1, y-1) || 
                isTripleEqualAndInRange(x, y, x+1, y-1, x+2, y-2);
    }
    
    private boolean isTripleEqualAndInRange(int x1, int y1, int x2, int y2, int x3, int y3) {
        if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x3 < 0 || y3 < 0) return false;
        if(x1 >= squares || y1 >= squares || x2 >= squares || y2 >= squares || x3 >= squares || y3 >= squares) return false;
        return board[y1][x1] == board[y2][x2] && board[y2][x2] == board[y3][x3];
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", startedAt=" + startedAt + ", lastMoveAt=" + lastMoveAt + ", numMoves=" + numMoves + ", inviter=" + inviter + ", invitee=" + invitee + ", turn=" + turn + ", winner=" + winner + ", board=" + board + ", squares=" + squares + '}';
    }
}
