package com.example.chess3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public static Activity context;
    public static Piece piece;
    public static movementLocation movementLocation = new movementLocation();
    public static int depth=0;
    public static Integer de=new Integer(0);



    @SuppressLint("LocalSuppress")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        context = this;

        Move move = new Move(this);
        @SuppressLint("MissingInflatedId")
        Button vs_1 = findViewById(R.id.vs);
        @SuppressLint("MissingInflatedId")
        Button vs_engin = findViewById(R.id.vs_engin);
        EditText de= this.findViewById(R.id.depth);
        vs_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                vs_engin.setEnabled(false);
                vs_1.setEnabled(false);
                move.mov();
            }
        });

        vs_engin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 8; j++) {
                        Board.whitePiece[i][j].performPieceMove();
                    }
                }

                vs_1.setEnabled(false);
                vs_engin.setEnabled(false);
                depth=Integer.parseInt(de.getText().toString());

            }
        });

    }
    public static void eng(){
        Engin engin = new Engin();
        final double dd = Double.NEGATIVE_INFINITY;
        final double d2 = Double.POSITIVE_INFINITY;
        de=new Integer(depth);
        double d = engin.alphaBeta(de, dd, d2, false);
        Log.d(Engin.w + " asad   ", "" + d);
        Log.d(movementLocation.getRow()+"sasasas"+ movementLocation.getCol(),"tag");
        Engin.w = 0;
        if (piece==null)
            Move.endGame(-1);
        if (Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()] == null) {
            Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()] = Board.boardPiece[piece.getId().getRow()][piece.getId().getCol()];
            Board.boardPiece[piece.getId().getRow()][piece.getId().getCol()] = null;
            Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()] = Board.blackBoardPiece[piece.getId().getRow()][piece.getId().getCol()];
            Board.blackBoardPiece[piece.getId().getRow()][piece.getId().getCol()] = null;
            Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()].setId(movementLocation);
            Board.baurd(piece.getImage(), movementLocation);
            Board.enableBlackPiece(false);
            Board.enableWhitePiece(true);

            Log.d("aa11111a","tag");
        } else if (Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()] != null) {
            Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()].setExistPiece(false);
            Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()].getImage().setVisibility(View.GONE);
            Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()] = Board.boardPiece[piece.getId().getRow()][piece.getId().getCol()];
            Board.boardPiece[piece.getId().getRow()][piece.getId().getCol()] = null;

            Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()] = null;

            Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()] = Board.blackBoardPiece[piece.getId().getRow()][piece.getId().getCol()];
            Board.blackBoardPiece[piece.getId().getRow()][piece.getId().getCol()] = null;

            Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()].setId(movementLocation);
            Board.baurd(piece.getImage(), movementLocation);
            Board.enableBlackPiece(false);
            Board.enableWhitePiece(true);

            Log.d("a a222222a","tag");
        }
        if (piece.getType_piece()==6 && piece.getId().getRow()==7){
            piece.setType_piece(2);
            piece.getImage().setImageResource(R.mipmap.queen_black);
        }

        Log.d("aa6666642a","tag");
    }
}