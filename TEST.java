import static org.junit.jupiter.api.Assertions.*;

class TEST {

    @org.junit.jupiter.api.Test
    void tileCountTest(){
        new GameBoard(9, 9, 10);
        assertEquals(71, GameBoard.getTiles());
    }

    @org.junit.jupiter.api.Test
    void flagTest(){
        new GameBoard(9, 9, 10);
        Logic.setFlag(1,1);
        assertTrue(GameBoard.getGrid(1,1)>=20 );
    }
    @org.junit.jupiter.api.Test
    void bombTest(){
        new GameBoard(9, 9, 81);
        Logic.open(1,1);
        assertEquals(-1, GameBoard.getTiles());
    }

}
