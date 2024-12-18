package com.example.chess3;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Move extends AppCompatActivity {
    public static ImageView pawn_white_1;
    public static ImageView pawn_white_2;
    public static ImageView pawn_white_3;
    public static ImageView pawn_white_4;
    public static ImageView pawn_white_5;
    public static ImageView pawn_white_6;
    public static ImageView pawn_white_7;
    public static ImageView pawn_white_8;

    public static ImageView rook_white_1;
    public static ImageView rook_white_2;
    public static ImageView knight_white_1;
    public static ImageView knight_white_2;
    public static ImageView bishop_white_1;
    public static ImageView bishop_white_2;
    public static ImageView queen_white;
    public static ImageView king_white;

    public static ImageView pawn_black_1;
    public static ImageView pawn_black_2;
    public static ImageView pawn_black_3;
    public static ImageView pawn_black_4;
    public static ImageView pawn_black_5;
    public static ImageView pawn_black_6;
    public static ImageView pawn_black_7;
    public static ImageView pawn_black_8;
    public static ImageView rook_black_1;
    public static ImageView rook_black_2;
    public static ImageView knight_black_1;
    public static ImageView knight_black_2;
    public static ImageView bishop_black_1;
    public static ImageView bishop_black_2;
    public static ImageView queen_black;
    public static ImageView king_black;

    static Piece Pawn_white_1;
    static Piece Pawn_white_2;
    static Piece Pawn_white_3;
    static Piece Pawn_white_4;
    static Piece Pawn_white_5;
    static Piece Pawn_white_6;
    static Piece Pawn_white_7;
    static Piece Pawn_white_8;
    static Piece Rook_white_1;
    static Piece Rook_white_2;
    static Piece Knight_white_1;
    static Piece Knight_white_2;
    static Piece Bishop_white_1;
    static Piece Bishop_white_2;
    static Piece Queen_white;
    static Piece King_white;

    static Piece Pawn_black_1;
    static Piece Pawn_black_2;
    static Piece Pawn_black_3;
    static Piece Pawn_black_4;
    static Piece Pawn_black_5;
    static Piece Pawn_black_6;
    static Piece Pawn_black_7;
    static Piece Pawn_black_8;
    static Piece Rook_black_1;
    static Piece Rook_black_2;
    static Piece Knight_black_1;
    static Piece Knight_black_2;
    static Piece Bishop_black_1;
    static Piece Bishop_black_2;
    static Piece Queen_black;
    static Piece King_black;
    private Activity activity;
    static EditText end_game;

//    GridLayout gridLayout;
    androidx.gridlayout.widget.GridLayout gridLayout;
    public static final String[][] whitePieceNames = {
            {"pawn_white_1", "pawn_white_2", "pawn_white_3", "pawn_white_4", "pawn_white_5", "pawn_white_6", "pawn_white_7", "pawn_white_8"},
            {"rook_white_1", "knight_white_1", "bishop_white_1", "queen_white", "king_white", "bishop_white_2", "knight_white_2", "rook_white_2"}
    };
    public static final String[][] blackPieceNames = {
            {"rook_black_1", "knight_black_1", "bishop_black_1", "queen_black", "king_black", "bishop_black_2", "knight_black_2", "rook_black_2"},
            {"pawn_black_1", "pawn_black_2", "pawn_black_3", "pawn_black_4", "pawn_black_5", "pawn_black_6", "pawn_black_7", "pawn_black_8"}

    };



    private int getResourceIdByName(String name) {
        return activity.getResources().getIdentifier(name, "mipmap", activity.getPackageName());
    }
    public Move(Activity activity) {
        this.activity=activity;
        gridLayout=activity.findViewById(R.id.gridlayout);
        end_game=activity.findViewById(R.id.game_over);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int screenWidthPx = displayMetrics.widthPixels;
        ImageView imageView ;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                imageView = new ImageView(activity); // إنشاء كائن ImageView داخل الحلقة
                imageView.setTag("I" + i + "" + j);

                androidx.gridlayout.widget.GridLayout.LayoutParams layoutParams = new androidx.gridlayout.widget.GridLayout.LayoutParams();
                layoutParams.rowSpec = androidx.gridlayout.widget.GridLayout.spec(i);
                layoutParams.columnSpec = androidx.gridlayout.widget.GridLayout.spec(j);
                layoutParams.width = screenWidthPx / 8;
                layoutParams.height = screenWidthPx / 8;
                imageView.setLayoutParams(layoutParams);

                // تعيين صورة إلى كل ImageView
                imageView.setImageResource(R.mipmap.pngegg);
                imageView.setVisibility(View.INVISIBLE);
                gridLayout.addView(imageView);
                // تعيين الصورة ورؤية العنصر حسب الشروط
                if (i == 0 || i == 1) {
                    imageView = new ImageView(activity); // إنشاء كائن ImageView داخل الحلقة

                    layoutParams = new androidx.gridlayout.widget.GridLayout.LayoutParams();
                    layoutParams.rowSpec = androidx.gridlayout.widget.GridLayout.spec(i);
                    layoutParams.columnSpec = androidx.gridlayout.widget.GridLayout.spec(j);
                    layoutParams.width = screenWidthPx / 8;
                    layoutParams.height = screenWidthPx / 8;
                    imageView.setTag("@+movementLocation/"+blackPieceNames[i][j]);
                    if (i==1)
                        imageView.setImageResource(getResourceIdByName("@mipmap/pawn_black"));
                    else if (j==0||j==7)
                        imageView.setImageResource(getResourceIdByName("@mipmap/rook_black"));
                    else if (j==1||j==6)
                        imageView.setImageResource(getResourceIdByName("@mipmap/knight_black"));
                    else if (j==2||j==5)
                        imageView.setImageResource(getResourceIdByName("@mipmap/bishop_black"));
                    else imageView.setImageResource(getResourceIdByName("@mipmap/"+blackPieceNames[i][j]));

                    imageView.setVisibility(View.VISIBLE);
                    imageView.setLayoutParams(layoutParams);

                    gridLayout.addView(imageView);
                }
                if (i == 6 || i == 7) {
                    imageView = new ImageView(activity); // إنشاء كائن ImageView داخل الحلقة

                    layoutParams = new androidx.gridlayout.widget.GridLayout.LayoutParams();
                    layoutParams.rowSpec = androidx.gridlayout.widget.GridLayout.spec(i);
                    layoutParams.columnSpec = androidx.gridlayout.widget.GridLayout.spec(j);
                    layoutParams.width = screenWidthPx / 8;
                    layoutParams.height = screenWidthPx / 8;
                    imageView.setTag("@+movementLocation/"+whitePieceNames[i-6][j]);
                    if (i==6)
                    imageView.setImageResource(getResourceIdByName("@mipmap/pawn_white"));
                    else if (j==0||j==7)
                        imageView.setImageResource(getResourceIdByName("@mipmap/rook_white"));
                    else if (j==1||j==6)
                        imageView.setImageResource(getResourceIdByName("@mipmap/knight_white"));
                    else if (j==2||j==5)
                        imageView.setImageResource(getResourceIdByName("@mipmap/bishop_white"));
                    else imageView.setImageResource(getResourceIdByName("@mipmap/"+whitePieceNames[i-6][j]));


                    imageView.setVisibility(View.VISIBLE);
                    imageView.setLayoutParams(layoutParams);

                    gridLayout.addView(imageView);
                }

            }
        }
        // Pawn White
        ImageView pawn_white_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_1");
        ImageView pawn_white_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_2");
        ImageView pawn_white_3 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_3");
        ImageView pawn_white_4 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_4");
        ImageView pawn_white_5 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_5");
        ImageView pawn_white_6 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_6");
        ImageView pawn_white_7 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_7");
        ImageView pawn_white_8 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_white_8");

// Rook White
        ImageView rook_white_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/rook_white_1");
        ImageView rook_white_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/rook_white_2");

// Knight White
        ImageView knight_white_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/knight_white_1");
        ImageView knight_white_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/knight_white_2");

// Bishop White
        ImageView bishop_white_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/bishop_white_1");
        ImageView bishop_white_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/bishop_white_2");

// Queen White
        ImageView queen_white = (ImageView) gridLayout.findViewWithTag("@+movementLocation/queen_white");

// King White
        ImageView king_white = (ImageView) gridLayout.findViewWithTag("@+movementLocation/king_white");

// Pawn Black
        ImageView pawn_black_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_1");
        ImageView pawn_black_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_2");
        ImageView pawn_black_3 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_3");
        ImageView pawn_black_4 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_4");
        ImageView pawn_black_5 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_5");
        ImageView pawn_black_6 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_6");
        ImageView pawn_black_7 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_7");
        ImageView pawn_black_8 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/pawn_black_8");

// Rook Black
        ImageView rook_black_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/rook_black_1");
        ImageView rook_black_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/rook_black_2");

// Knight Black
        ImageView knight_black_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/knight_black_1");
        ImageView knight_black_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/knight_black_2");

// Bishop Black
        ImageView bishop_black_1 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/bishop_black_1");
        ImageView bishop_black_2 = (ImageView) gridLayout.findViewWithTag("@+movementLocation/bishop_black_2");

// Queen Black
        ImageView queen_black = (ImageView) gridLayout.findViewWithTag("@+movementLocation/queen_black");

// King Black
        ImageView king_black = (ImageView) gridLayout.findViewWithTag("@+movementLocation/king_black");

        Pawn_white_1 = new Piece(6, 0, 1, 6, pawn_white_1);
        Pawn_white_2 = new Piece(6, 1, 1, 6, pawn_white_2);
        Pawn_white_3 = new Piece(6, 2, 1, 6, pawn_white_3);
        Pawn_white_4 = new Piece(6, 3, 1, 6, pawn_white_4);
        Pawn_white_5 = new Piece(6, 4, 1, 6, pawn_white_5);
        Pawn_white_6 = new Piece(6, 5, 1, 6, pawn_white_6);
        Pawn_white_7 = new Piece(6, 6, 1, 6, pawn_white_7);
        Pawn_white_8 = new Piece(6, 7, 1, 6, pawn_white_8);
        Rook_white_1 = new Piece(7, 0, 1, 5, rook_white_1);
        Rook_white_2 = new Piece(7, 7, 1, 5, rook_white_2);
        Knight_white_1 = new Piece(7, 1, 1, 4, knight_white_1);
        Knight_white_2 = new Piece(7, 6, 1, 4, knight_white_2);
        Bishop_white_1 = new Piece(7, 2, 1, 3, bishop_white_1);
        Bishop_white_2 = new Piece(7, 5, 1, 3, bishop_white_2);
        Queen_white = new Piece(7, 3, 1, 2, queen_white);
        King_white = new Piece(7, 4, 1, 1, king_white);

        Pawn_black_1 = new Piece(1, 0, -1, 6, pawn_black_1);
        Pawn_black_2 = new Piece(1, 1, -1, 6, pawn_black_2);
        Pawn_black_3 = new Piece(1, 2, -1, 6, pawn_black_3);
        Pawn_black_4 = new Piece(1, 3, -1, 6, pawn_black_4);
        Pawn_black_5 = new Piece(1, 4, -1, 6, pawn_black_5);
        Pawn_black_6 = new Piece(1, 5, -1, 6, pawn_black_6);
        Pawn_black_7 = new Piece(1, 6, -1, 6, pawn_black_7);
        Pawn_black_8 = new Piece(1, 7, -1, 6, pawn_black_8);
        Rook_black_1 = new Piece(0, 0, -1, 5, rook_black_1);
        Rook_black_2 = new Piece(0, 7, -1, 5, rook_black_2);
        Knight_black_1 = new Piece(0, 1, -1, 4, knight_black_1);
        Knight_black_2 = new Piece(0, 6, -1, 4, knight_black_2);
        Bishop_black_1 = new Piece(0, 2, -1
                , 3, bishop_black_1);
        Bishop_black_2 = new Piece(0, 5, -1, 3, bishop_black_2);
        Queen_black = new Piece(0, 3, -1, 2, queen_black);
        King_black = new Piece(0, 4, -1, 1, king_black);
        Log.d((king_black==null)+" sa","eqw");
        Board board = new Board(activity);

    }
   public static ArrayList<movementLocation> movementWhitePiece(){
        ArrayList<movementLocation> arrayList=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.whitePiece[i][j].getExistPiece())
                    arrayList.addAll(Board.whitePiece[i][j].move_piece(false));
            }
        }
    return arrayList;
    }
    public static ArrayList<movementLocation> movementBlackPiece(){
        ArrayList<movementLocation> arrayList=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.blackPiece[i][j].getExistPiece())
                    arrayList.addAll(Board.blackPiece[i][j].move_piece(false));
            }
        }
        return arrayList;
    }
    static void endGame(int color){

        if (MainActivity.depth==MainActivity.de){
            ArrayList<movementLocation> arrayList = new ArrayList<>();

            if (color == 1)
                arrayList.addAll(movementWhitePiece());
            else arrayList.addAll(movementBlackPiece());


            if (arrayList.size() == 0 || !Move.King_black.getExistPiece() || !Move.King_white.getExistPiece()) {

                Board.enableWhitePiece(false);
                Board.enableBlackPiece(false);
                end_game.setVisibility(View.VISIBLE);
            }
        }
    }

public void mov(){
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 8; j++) {
            Board.whitePiece[i][j].performPieceMove();
            Board.blackPiece[i][j].performPieceMove();
        }
    }
}
}
