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
                board[x][y] = Player.e;
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
    }
    
    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", startedAt=" + startedAt + ", lastMoveAt=" + lastMoveAt + ", numMoves=" + numMoves + ", inviter=" + inviter + ", invitee=" + invitee + ", turn=" + turn + ", winner=" + winner + ", board=" + board + ", squares=" + squares + '}';
    }
}
