package com.example.chess3;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;

public class Board extends AppCompatActivity {

    private static Activity activity1;
    static Piece[][] boardPiece = new Piece[8][8];
    static Piece[][] whiteBoardPiece = new Piece[8][8];
    static Piece[][] blackBoardPiece = new Piece[8][8];
    static View[][] views;
    static Piece[][] whitePiece;
    static Piece[][] blackPiece;
    static GridLayout gridlayout;

    public static double[][] bestKnight = {
            {1, 2, 2, 2, 2, 2, 2, 1},
            {2, 3, 4, 4, 4, 4, 3, 2},
            {2, 4, 6, 6, 6, 6, 4, 2},
            {2, 4, 6, 8, 8, 6, 4, 2},
            {2, 4, 6, 8, 8, 6, 4, 2},
            {2, 4, 6, 6, 6, 6, 4, 2},
            {2, 3, 4, 4, 4, 4, 3, 2},
            {1, 2, 2, 2, 2, 2, 2, 1}
    };
    public static double[][] bestRook =
            {
                    {5, 4, 3, 5, 3, 4, 3, 5},
                    {4, 3, 2, 4, 2, 3, 2, 4},
                    {3, 2, 1, 3, 1, 2, 1, 3},
                    {3, 2, 1, 3, 1, 2, 1, 3},
                    {3, 2, 1, 3, 1, 2, 1, 3},
                    {3, 2, 1, 3, 1, 2, 1, 3},
                    {4, 3, 2, 4, 2, 3, 2, 4},
                    {5, 4, 3, 5, 3, 4, 3, 5}
            };

    public static double[][] bestQueen =  {
            {3, 4, 4, 4, 4, 4, 4, 3},
            {4, 6, 6, 6, 6, 6, 6, 4},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {4, 6, 8, 10, 10, 8, 6, 4},
            {4, 6, 8, 10, 10, 8, 6, 4},
            {4, 6, 8, 8, 8, 8, 6, 4},
            {4, 6, 6, 6, 6, 6, 6, 4},
            {3, 4, 4, 4, 4, 4, 4, 3}
    };
    public static double[][] bestPawnWhite = {
            {7, 7, 7, 7, 7, 7, 7, 7},
            {6, 6, 6, 6, 6, 6, 6, 6},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {4, 4, 4, 4, 4, 4, 4, 4},
            {3, 3, 3, 3, 3, 3, 3, 3},
            {2, 2, 2, 2, 2, 2, 2, 2},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    public static double[][] bestPawnBlack = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4, 4, 4, 4},
            {5, 5, 5, 5, 5, 5, 5, 5},
            {6, 6, 6, 6, 6, 6, 6, 6},
            {7, 7, 7, 7, 7, 7, 7, 7}
    };
    static boolean[][] eating_passing = new boolean[2][8];
    static boolean[][] caslKing = {
            {true, true, true},
            {true, true, true}
    };


    public Board(Activity activity) {

        if (views == null)
            chargeViews(activity);
    }


    static void baurd(View view, movementLocation id) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = activity1.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int screenWidthPx = displayMetrics.widthPixels;

        gridlayout.removeView(view);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.rowSpec = GridLayout.spec(id.getRow());
        layoutParams.columnSpec = GridLayout.spec(id.getCol());

        layoutParams.width = screenWidthPx / 8;
        layoutParams.height = screenWidthPx / 8;
        view.setLayoutParams(layoutParams);
        gridlayout.addView(view);

    }

    private void chargeViews(Activity activity) {
        activity1 = activity;
//        views = new ImageView[8][8];
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                int resId = activity.getResources().getIdentifier("I" + i + j, "movementLocation", activity.getPackageName());
//                views[i][j] = activity.getCurrentFocus().findViewWithTag("I"+i+j);
//                views[i][j].setVisibility(View.VISIBLE);}
//        }
        gridlayout = activity.findViewById(R.id.gridlayout);
        activity1 = activity;
        views = new ImageView[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String tag = "I" + i + j; // احصل على العلامة من الدالة
                views[i][j] = (ImageView) gridlayout.findViewWithTag(tag);

            }
        }
        whitePiece = new Piece[][]{
                {Move.Pawn_white_1, Move.Pawn_white_2, Move.Pawn_white_3, Move.Pawn_white_4, Move.Pawn_white_5, Move.Pawn_white_6, Move.Pawn_white_7, Move.Pawn_white_8},
                {Move.Rook_white_1, Move.Knight_white_1, Move.Bishop_white_1, Move.Queen_white, Move.King_white, Move.Bishop_white_2, Move.Knight_white_2, Move.Rook_white_2}
        };
        blackPiece = new Piece[][]{
                {Move.Rook_black_1, Move.Knight_black_1, Move.Bishop_black_1, Move.Queen_black, Move.King_black, Move.Bishop_black_2, Move.Knight_black_2, Move.Rook_black_2},
                {Move.Pawn_black_1, Move.Pawn_black_2, Move.Pawn_black_3, Move.Pawn_black_4, Move.Pawn_black_5, Move.Pawn_black_6, Move.Pawn_black_7, Move.Pawn_black_8}

        };
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < 2) {
                    boardPiece[i][j] = blackPiece[i][j];
                    blackBoardPiece[i][j] = blackPiece[i][j];

                } else if (i > 5) {
                    boardPiece[i][j] = whitePiece[i - 6][j];
                    whiteBoardPiece[i][j] = whitePiece[i - 6][j];
                } else {
                    boardPiece[i][j] = null;
                    whiteBoardPiece[i][j] = null;
                    blackBoardPiece[i][j] = null;
                }
            }
        }

    }


    static void invisible_views() {
        Board.views[3][5].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                views[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    static void enableWhitePiece(boolean b) {
        if (b) Move.endGame(1);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                whitePiece[i][j].Enable(b);
                if (!b)
                    eating_passing[1][j] = false;
            }
        }
    }

    static void enableBlackPiece(boolean b) {
        if (b) Move.endGame(-1);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                blackPiece[i][j].Enable(b);
                if (!b)
                    eating_passing[0][j] = false;
            }
        }
        if (MainActivity.depth != 0 && b) {
            MainActivity.eng();
        }
    }

    static ArrayList<movementLocation> getAttackingWhitePiece(movementLocation id) {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (whitePiece[i][j].getExistPiece() && !id.equal(whitePiece[i][j].getId()))
                    arrayList.addAll(whitePiece[i][j].move_piece(true));
            }
        }
        return arrayList;
    }

    static ArrayList<movementLocation> getAttackingBlackPiece(movementLocation id) {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (blackPiece[i][j].getExistPiece() && !id.equal(blackPiece[i][j].getId())) {
                    arrayList.addAll(blackPiece[i][j].move_piece(true));
                }
            }
        }
        return arrayList;
    }

    public static boolean isKingInCheck(movementLocation oldLocation, movementLocation newLocation, int color, int m) {
        Piece[][] chessBoard = new Piece[8][8];
        Piece[][] chessboardSameColor = new Piece[8][8];
        Piece[][] chessboardInvertColor = new Piece[8][8];
        Piece[][] cbwhite = new Piece[8][8];
        Piece[][] cbblack = new Piece[8][8];

        movementLocation king = new movementLocation(-1, -1);
        if (color == 1) {
            chessboardSameColor = whiteBoardPiece;
            chessboardInvertColor = blackBoardPiece;
            if (!oldLocation.equal(Move.King_white.getId()))
                king.setId(Move.King_white.getId());
            else {
                king.setId(newLocation);

            }
        } else {
            chessboardSameColor = blackBoardPiece;
            chessboardInvertColor = whiteBoardPiece;
            if (!oldLocation.equal(Move.King_black.getId()))
                king.setId(Move.King_black.getId());
            else {
                king.setId(newLocation);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = boardPiece[i][j];
                cbwhite[i][j] = whiteBoardPiece[i][j];
                cbblack[i][j] = blackBoardPiece[i][j];
            }
        }
        if (m == 0) {
            if (boardPiece[newLocation.getRow()][newLocation.getCol()] == null) {
                boardPiece[newLocation.getRow()][newLocation.getCol()] = boardPiece[oldLocation.getRow()][oldLocation.getCol()];
                boardPiece[oldLocation.getRow()][oldLocation.getCol()] = null;
                chessboardSameColor[newLocation.getRow()][newLocation.getCol()] = chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()];
                chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()] = null;
            } else {

                boardPiece[newLocation.getRow()][newLocation.getCol()] = boardPiece[oldLocation.getRow()][oldLocation.getCol()];
                boardPiece[oldLocation.getRow()][oldLocation.getCol()] = null;
                chessboardSameColor[newLocation.getRow()][newLocation.getCol()] = chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()];
                chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()] = null;
                chessboardInvertColor[newLocation.getRow()][newLocation.getCol()] = null;
            }
        } else if (m == 1) {
            boardPiece[newLocation.getRow()][newLocation.getCol()] = boardPiece[oldLocation.getRow()][oldLocation.getCol()];
            boardPiece[oldLocation.getRow()][oldLocation.getCol()] = null;
            boardPiece[newLocation.getRow() + color][newLocation.getCol()] = null;
            chessboardSameColor[newLocation.getRow()][newLocation.getCol()] = chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()];
            chessboardSameColor[oldLocation.getRow()][oldLocation.getCol()] = null;
            chessboardInvertColor[newLocation.getRow() + color][newLocation.getCol()] = null;

        }
        boolean b;

        if (color == 1)
            b = isIdInList(king, getAttackingBlackPiece(newLocation));
        else b = isIdInList(king, getAttackingWhitePiece(newLocation));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardPiece[i][j] = chessBoard[i][j];
                blackBoardPiece[i][j] = cbblack[i][j];
                whiteBoardPiece[i][j] = cbwhite[i][j];
            }
        }


        return !b;
    }


    public static boolean isIdInList(movementLocation id, ArrayList<movementLocation> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (id.equal(arrayList.get(i).getId()))
                return true;
        }
        return false;
    }
}
