package com.example.chess3;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Piece extends AppCompatActivity {

    private int row;
    private int col;
    private int color;
    private int type_piece;
    private ImageView imageView;
    private Piece[][] boardPiece = Board.boardPiece;
    private Piece[][] chessboardSameColor;
    private Piece[][] chessboardInvertColor;
    private int mov_dir; //اتجاه حركة البيدق اذا ماكان البدق ابيض -1 و أذ ا كان اسود 1
    private boolean w;
    private boolean b;
    private LinearLayout linearLayout;
    private ImageView queen;
    private ImageView bishop;
    private ImageView knight;
    private ImageView rook;
    private boolean existPiece;
    private movementLocation k;

    private movementLocation[][] lc;

    private movementLocation[] r;
    private boolean[] castle;

    public Piece() {
        this.existPiece = false;
    }

    public Piece(int row, int col, int color, int type_piece, ImageView imageView) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.type_piece = type_piece;
        this.imageView = imageView;
        existPiece = true;
        if (color == 1) {
            chessboardSameColor = Board.whiteBoardPiece;
            chessboardInvertColor = Board.blackBoardPiece;

            w = false;
            b = true;
            if (type_piece == 6) {
                linearLayout = MainActivity.context.findViewById(R.id.dev_pawn_white);
                queen = MainActivity.context.findViewById(R.id.dev_queen_white);
                bishop = MainActivity.context.findViewById(R.id.dev_bishop_white);
                knight = MainActivity.context.findViewById(R.id.dev_knight_white);
                rook = MainActivity.context.findViewById(R.id.dev_rook_white);
            }
        } else {
            chessboardSameColor = Board.blackBoardPiece;
            chessboardInvertColor = Board.whiteBoardPiece;

            w = true;
            b = false;
            if (type_piece == 6) {
                linearLayout = MainActivity.context.findViewById(R.id.dev_pawn_black);
                queen = MainActivity.context.findViewById(R.id.dev_queen_black);
                bishop = MainActivity.context.findViewById(R.id.dev_bishop_black);
                knight = MainActivity.context.findViewById(R.id.dev_knight_black);
                rook = MainActivity.context.findViewById(R.id.dev_rook_black);
            }
        }
    }

    public int getType_piece() {
        return type_piece;
    }

    public void setType_piece(int type_piece) {
        this.type_piece = type_piece;
    }

    public void setExistPiece(boolean b) {
        this.existPiece = b;
    }

    public boolean getExistPiece() {
        return existPiece;
    }

    public void Enable(boolean b) {
        imageView.setEnabled(b);
    }

    public ImageView getImage() {
        return imageView;
    }

    public void setId(movementLocation id) {
        Engin.evaluation();


        if (Math.abs(row - id.getRow()) == 2 && type_piece == 6) {

            if (color == 1)
                Board.eating_passing[0][col] = true;
            else
                Board.eating_passing[1][col] = true;

        }

        movementLocation kb = new movementLocation(0, 4);
        movementLocation rb1 = new movementLocation(0, 0);
        movementLocation rb2 = new movementLocation(0, 7);

        movementLocation kw = new movementLocation(7, 4);
        movementLocation rw1 = new movementLocation(7, 0);
        movementLocation rw2 = new movementLocation(7, 7);

        if (!kb.equal(Board.blackPiece[0][4].getId()) || !Board.blackPiece[0][4].existPiece)
            Board.caslKing[0][2] = false;
        if (!rb1.equal(Board.blackPiece[0][0].getId()) || !Board.blackPiece[0][0].existPiece)
            Board.caslKing[0][0] = false;
        if (!rb2.equal(Board.blackPiece[0][7].getId()) || !Board.blackPiece[0][7].existPiece)
            Board.caslKing[0][1] = false;

        if (!kw.equal(Board.whitePiece[1][4].getId()) || !Board.whitePiece[1][4].existPiece)
            Board.caslKing[1][2] = false;
        if (!rw1.equal(Board.whitePiece[1][0].getId()) || !Board.whitePiece[1][0].existPiece)
            Board.caslKing[1][0] = false;
        if (!rw2.equal(Board.whitePiece[1][7].getId()) || !Board.whitePiece[1][7].existPiece)
            Board.caslKing[1][1] = false;

        this.row = id.getRow();
        this.col = id.getCol();
    }

    public movementLocation getId() {
        return new movementLocation(row, col);
    }

    public void performPieceMove() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Board.invisible_views();
                ArrayList<movementLocation> arrayList = move_piece(false);
                if (type_piece == 6)
                    eating_passing();
                if (type_piece == 1 && Board.isKingInCheck(getId(), getId(), color, 0))
                    castleKing();
                for (int i = 0; i < arrayList.size(); i++) {
                    int ii = i;

                    Board.views[arrayList.get(i).getRow()][arrayList.get(i).getCol()].setVisibility(View.VISIBLE);
                    Board.views[arrayList.get(i).getRow()][arrayList.get(i).getCol()].bringToFront();

                    if (Board.boardPiece[arrayList.get(i).getRow()][arrayList.get(i).getCol()] == null) {
                        Board.views[arrayList.get(i).getRow()][arrayList.get(i).getCol()].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                boardPiece[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()] = Board.boardPiece[row][col];
                                boardPiece[row][col] = null;


                                chessboardSameColor[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()] = chessboardSameColor[row][col];
                                chessboardSameColor[row][col] = null;


                                setId(arrayList.get(ii));
                                Board.baurd(imageView, getId());
                                Board.invisible_views();
                                if ((row == 0 || row == 7) && type_piece == 6)
                                    dev_pawn();
                                Board.enableWhitePiece(w);
                                Board.enableBlackPiece(b);

                            }
                        });
                    }
                    if (Board.boardPiece[arrayList.get(i).getRow()][arrayList.get(i).getCol()] != null) {
                        Board.views[arrayList.get(i).getRow()][arrayList.get(i).getCol()].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boardPiece[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()].setExistPiece(false);
                                boardPiece[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()].imageView.setVisibility(View.GONE);
                                boardPiece[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()] = boardPiece[row][col];
                                boardPiece[row][col] = null;

                                chessboardSameColor[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()] = chessboardSameColor[row][col];
                                chessboardSameColor[row][col] = null;
                                chessboardInvertColor[arrayList.get(ii).getRow()][arrayList.get(ii).getCol()] = null;


                                setId(arrayList.get(ii));
                                Board.baurd(imageView, getId());
                                Board.invisible_views();
                                if ((row == 0 || row == 7) && type_piece == 6)
                                    dev_pawn();
                                Board.enableWhitePiece(w);
                                Board.enableBlackPiece(b);

                            }
                        });
                    }
                }
            }
        });
    }


    private void dev_pawn() {
        linearLayout.setVisibility(View.VISIBLE);
        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_piece = 2;
                if (color == 1)
                    imageView.setImageResource(R.mipmap.queen_white);
                if (color == -1)
                    imageView.setImageResource(R.mipmap.queen_black);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_piece = 3;
                if (color == 1)
                    imageView.setImageResource(R.mipmap.bishop_white);
                if (color == -1)
                    imageView.setImageResource(R.mipmap.bishop_black);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_piece = 4;
                if (color == 1)
                    imageView.setImageResource(R.mipmap.knight_white);
                if (color == -1)
                    imageView.setImageResource(R.mipmap.knight_black);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_piece = 5;
                if (color == 1)
                    imageView.setImageResource(R.mipmap.rook_white);
                if (color == -1)
                    imageView.setImageResource(R.mipmap.rook_black);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    public ArrayList<movementLocation> move_piece(boolean b) {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        ArrayList<movementLocation> arrayList1 = new ArrayList<>();
        if (type_piece == 1)
            arrayList.addAll(king_move());
        if (type_piece == 2)
            arrayList.addAll(queen_move());
        if (type_piece == 3)
            arrayList.addAll(bishop_move());
        if (type_piece == 4)
            arrayList.addAll(knight_move());
        if (type_piece == 5)
            arrayList.addAll(rook_move());
        if (type_piece == 6)
            arrayList.addAll(Pawn_move(b));
        if (b)
            return arrayList;
        for (int i = 0; i < arrayList.size(); i++) {
            if (Board.isKingInCheck(getId(), arrayList.get(i), color, 0))
                arrayList1.add(arrayList.get(i));
        }
        return arrayList1;
    }

    private ArrayList<movementLocation> row() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = row + 1; i < 8; i++) {
            if (chessboardSameColor[i][col] != null && chessboardSameColor[i][col].getExistPiece())
                break;
            if (chessboardInvertColor[i][col] != null && chessboardInvertColor[i][col].getExistPiece()) {
                arrayList.add(new movementLocation(i, col));
                break;
            }
            arrayList.add(new movementLocation(i, col));
        }
        for (int i = row - 1; i >= 0; i--) {
            if (chessboardSameColor[i][col] != null && chessboardSameColor[i][col].getExistPiece())
                break;
            if (chessboardInvertColor[i][col] != null && chessboardInvertColor[i][col].getExistPiece()) {
                arrayList.add(new movementLocation(i, col));
                break;
            }
            arrayList.add(new movementLocation(i, col));
        }
        return arrayList;
    }

    private ArrayList<movementLocation> col() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = col + 1; i < 8; i++) {
            if (chessboardSameColor[row][i] != null && chessboardSameColor[row][i].getExistPiece())
                break;
            if (chessboardInvertColor[row][i] != null && chessboardInvertColor[row][i].getExistPiece()) {
                arrayList.add(new movementLocation(row, i));
                break;
            }
            arrayList.add(new movementLocation(row, i));
        }
        for (int i = col - 1; i >= 0; i--) {
            if (chessboardSameColor[row][i] != null && chessboardSameColor[row][i].getExistPiece())
                break;
            if (chessboardInvertColor[row][i] != null && chessboardInvertColor[row][i].getExistPiece()) {
                arrayList.add(new movementLocation(row, i));
                break;
            }
            arrayList.add(new movementLocation(row, i));
        }
        return arrayList;
    }

    private ArrayList<movementLocation> first_diagonal_movement() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--) {
            if (chessboardSameColor[i][j] != null && chessboardSameColor[i][j].getExistPiece())
                break;
            if (chessboardInvertColor[i][j] != null && chessboardInvertColor[i][j].getExistPiece()) {
                arrayList.add(new movementLocation(i, j));
                break;
            }
            arrayList.add(new movementLocation(i, j));
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++) {
            if (chessboardSameColor[i][j] != null && chessboardSameColor[i][j].getExistPiece())
                break;
            if (chessboardInvertColor[i][j] != null && chessboardInvertColor[i][j].getExistPiece()) {
                arrayList.add(new movementLocation(i, j));
                break;
            }
            arrayList.add(new movementLocation(i, j));
        }
        return arrayList;
    }

    private ArrayList<movementLocation> second_diagonal_movement() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        for (int i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++) {
            if (chessboardSameColor[i][j] != null && chessboardSameColor[i][j].getExistPiece())
                break;
            if (chessboardInvertColor[i][j] != null && chessboardInvertColor[i][j].getExistPiece()) {
                arrayList.add(new movementLocation(i, j));
                break;
            }
            arrayList.add(new movementLocation(i, j));
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chessboardSameColor[i][j] != null && chessboardSameColor[i][j].getExistPiece())
                break;
            if (chessboardInvertColor[i][j] != null && chessboardInvertColor[i][j].getExistPiece()) {
                arrayList.add(new movementLocation(i, j));
                break;
            }
            arrayList.add(new movementLocation(i, j));
        }
        return arrayList;

    }

    private ArrayList<movementLocation> king_move() {
        ArrayList<movementLocation> arrayList = new ArrayList();

        if (row + 1 < 8)
            if (boardPiece[row + 1][col] == null || chessboardInvertColor[row + 1][col] != null && chessboardInvertColor[row + 1][col].getExistPiece())
                arrayList.add(new movementLocation(row + 1, col));
        if (row - 1 >= 0)
            if (boardPiece[row - 1][col] == null || chessboardInvertColor[row - 1][col] != null && chessboardInvertColor[row - 1][col].getExistPiece())
                arrayList.add(new movementLocation(row - 1, col));
        if (col + 1 < 8)
            if (boardPiece[row][col + 1] == null || chessboardInvertColor[row][col + 1] != null && chessboardInvertColor[row][col + 1].getExistPiece())
                arrayList.add(new movementLocation(row, col + 1));
        if (col - 1 >= 0)
            if (boardPiece[row][col - 1] == null || chessboardInvertColor[row][col - 1] != null && chessboardInvertColor[row][col - 1].getExistPiece())
                arrayList.add(new movementLocation(row, col - 1));
        if (row + 1 < 8 && col + 1 < 8)
            if (boardPiece[row + 1][col + 1] == null || chessboardInvertColor[row + 1][col + 1] != null && chessboardInvertColor[row + 1][col + 1].getExistPiece())
                arrayList.add(new movementLocation(row + 1, col + 1));

        if (row + 1 < 8 && col - 1 >= 0)
            if (boardPiece[row + 1][col - 1] == null || chessboardInvertColor[row + 1][col - 1] != null && chessboardInvertColor[row + 1][col - 1].getExistPiece())
                arrayList.add(new movementLocation(row + 1, col - 1));
        if (row - 1 >= 0 && col - 1 >= 0)
            if (boardPiece[row - 1][col - 1] == null || chessboardInvertColor[row - 1][col - 1] != null && chessboardInvertColor[row - 1][col - 1].getExistPiece())
                arrayList.add(new movementLocation(row - 1, col - 1));
        if (row - 1 >= 0 && col + 1 < 8)
            if (boardPiece[row - 1][col + 1] == null || chessboardInvertColor[row - 1][col + 1] != null && chessboardInvertColor[row - 1][col + 1] != null && chessboardInvertColor[row - 1][col + 1].getExistPiece())
                arrayList.add(new movementLocation(row - 1, col + 1));

        return arrayList;
    }

    private ArrayList<movementLocation> queen_move() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        arrayList.addAll(row());
        arrayList.addAll(col());
        arrayList.addAll(first_diagonal_movement());
        arrayList.addAll(second_diagonal_movement());
        return arrayList;
    }

    private ArrayList<movementLocation> bishop_move() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        arrayList.addAll(first_diagonal_movement());
        arrayList.addAll(second_diagonal_movement());
        return arrayList;
    }

    private ArrayList<movementLocation> knight_move() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();


        int i = row + 2;
        int j = col;
        if (i < 8) {
            if (j + 1 < 8) {
                if (boardPiece[i][j + 1] == null || chessboardInvertColor[i][j + 1] != null && chessboardInvertColor[i][j + 1].getExistPiece())
                    arrayList.add(new movementLocation(i, j + 1));
            }
            if (j - 1 >= 0)
                if (boardPiece[i][j - 1] == null || chessboardInvertColor[i][j - 1] != null && chessboardInvertColor[i][j - 1].getExistPiece())
                    arrayList.add(new movementLocation(i, j - 1));
        }
        i = row - 2;
        j = col;
        if (i >= 0) {
            if (j + 1 < 8) {
                if (boardPiece[i][j + 1] == null || chessboardInvertColor[i][j + 1] != null && chessboardInvertColor[i][j + 1].getExistPiece())
                    arrayList.add(new movementLocation(i, j + 1));
            }
            if (j - 1 >= 0)
                if (boardPiece[i][j - 1] == null || chessboardInvertColor[i][j - 1] != null && chessboardInvertColor[i][j - 1].getExistPiece())
                    arrayList.add(new movementLocation(i, j - 1));
        }
        i = row;
        j = col + 2;
        if (j < 8) {
            if (i + 1 < 8) {
                if (boardPiece[i + 1][j] == null || chessboardInvertColor[i + 1][j] != null && chessboardInvertColor[i + 1][j].getExistPiece())
                    arrayList.add(new movementLocation(i + 1, j));
            }
            if (i - 1 >= 0)
                if (boardPiece[i - 1][j] == null || chessboardInvertColor[i - 1][j] != null && chessboardInvertColor[i - 1][j].getExistPiece())
                    arrayList.add(new movementLocation(i - 1, j));
        }

        i = row;
        j = col - 2;
        if (j >= 0) {
            if (i + 1 < 8) {
                if (boardPiece[i + 1][j] == null || chessboardInvertColor[i + 1][j] != null && chessboardInvertColor[i + 1][j].getExistPiece())
                    arrayList.add(new movementLocation(i + 1, j));
            }
            if (i - 1 >= 0)
                if (boardPiece[i - 1][j] == null || chessboardInvertColor[i - 1][j] != null && chessboardInvertColor[i - 1][j].getExistPiece())
                    arrayList.add(new movementLocation(i - 1, j));
        }
        return arrayList;
    }

    private ArrayList<movementLocation> rook_move() {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        arrayList.addAll(row());
        arrayList.addAll(col());
        return arrayList;
    }

    private ArrayList<movementLocation> Pawn_move(boolean b) {
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        if (color == 1)
            mov_dir = -1;
        else mov_dir = 1;
        if (!b) {
            if (row + mov_dir < 8 && row + mov_dir >= 0) {
                if (boardPiece[row + mov_dir][col] == null) {
                    arrayList.add(new movementLocation(row + mov_dir, col));
                    if (row == 1 && color == -1 || row == 6 && color == 1)
                        if (row + 2 * mov_dir < 8 && row + 2 * mov_dir >= 0)
                            if (boardPiece[row + 2 * mov_dir][col] == null) {
                                arrayList.add(new movementLocation(row + 2 * mov_dir, col));
                            }
                }
            }
        }
        if (row + mov_dir < 8 && row + mov_dir >= 0) {
            if (col + 1 < 8 && chessboardInvertColor[row + mov_dir][col + 1] != null && chessboardInvertColor[row + mov_dir][col + 1].getExistPiece())
                arrayList.add(new movementLocation(row + mov_dir, col + 1));
            if (col - 1 >= 0 && chessboardInvertColor[row + mov_dir][col - 1] != null && chessboardInvertColor[row + mov_dir][col - 1].getExistPiece())
                arrayList.add(new movementLocation(row + mov_dir, col - 1));
        }
        return arrayList;
    }

    private void eating_passing() {
        Board.invisible_views();
        movementLocation id = null;
        if (row == 3 && color == 1) {
            if (col + 1 < 8)
                if (Board.eating_passing[1][col + 1])
                    id = new movementLocation(row - 1, col + 1);
            if (col - 1 >= 0)
                if (Board.eating_passing[1][col - 1])
                    id = new movementLocation(row - 1, col - 1);
        }
        if (row == 4 && color == -1) {
            if (col + 1 < 8)
                if (Board.eating_passing[0][col + 1])
                    id = new movementLocation(row + 1, col + 1);
            if (col - 1 >= 0)
                if (Board.eating_passing[0][col - 1])
                    id = new movementLocation(row + 1, col - 1);
        }
        if (id != null)
            if (Board.isKingInCheck(getId(), id, color, 1)) {
                int i = color;
                movementLocation idd = id;
                Board.views[id.getRow()][id.getCol()].setVisibility(View.VISIBLE);
                Board.views[id.getRow()][id.getCol()].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boardPiece[idd.getRow() + i][idd.getCol()].setExistPiece(false);
                        boardPiece[idd.getRow() + i][idd.getCol()].imageView.setVisibility(View.GONE);
                        boardPiece[idd.getRow() + i][idd.getCol()] = null;
                        boardPiece[idd.getRow()][idd.getCol()] = boardPiece[row][col];
                        boardPiece[row][col] = null;

                        chessboardSameColor[idd.getRow()][idd.getCol()] = chessboardSameColor[row][col];
                        chessboardSameColor[row][col] = null;
                        chessboardInvertColor[idd.getRow() + i][idd.getCol()] = null;


                        Board.baurd(imageView, idd);
                        setId(idd);
                        Board.invisible_views();
                        Board.enableWhitePiece(w);
                        Board.enableBlackPiece(b);
                    }
                });
            }
    }


    private void castleKing() {
        Board.invisible_views();

        if (color == 1) {
            k = new movementLocation(7, 4);
            r = new movementLocation[]{new movementLocation(7, 7), new movementLocation(7, 0)};
            lc = new movementLocation[][]{
                    {new movementLocation(7, 6), new movementLocation(7, 5)},
                    {new movementLocation(7, 2), new movementLocation(7, 3), new movementLocation(7, 1)}
            };
            castle = new boolean[]{Board.caslKing[1][1], Board.caslKing[1][0], Board.caslKing[1][2]};

        } else {
            k = new movementLocation(0, 4);
            r = new movementLocation[]{new movementLocation(0, 7), new movementLocation(0, 0)};
            lc = new movementLocation[][]{
                    {new movementLocation(0, 6), new movementLocation(0, 5)},
                    {new movementLocation(0, 2), new movementLocation(0, 3), new movementLocation(0, 1)}
            };
            castle = new boolean[]{Board.caslKing[0][1], Board.caslKing[0][0], Board.caslKing[0][2]};
        }

        for (int i = 0; i < 2; i++) {
            int ii = i;
            if (castle[2]) {
                if ((i == 1 && boardPiece[lc[i][2].getRow()][lc[i][2].getCol()] == null) || i == 0)
                    if (boardPiece[lc[i][0].getRow()][lc[i][0].getCol()] == null && boardPiece[lc[i][1].getRow()][lc[i][1].getCol()] == null && castle[i] && Board.isKingInCheck(new movementLocation(0, 4), lc[i][0], color, 0) && Board.isKingInCheck(new movementLocation(0, 4), lc[i][1], color, 0)) {
                        Board.views[lc[i][0].getRow()][lc[i][0].getCol()].setVisibility(View.VISIBLE);
                        Board.views[lc[i][0].getRow()][lc[i][0].getCol()].bringToFront();
                        Log.d(Board.caslKing[0][0] + " 1", Board.caslKing[0][1] + " 2 " + Board.caslKing[0][2]);
                        Board.views[lc[i][0].getRow()][lc[i][0].getCol()].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boardPiece[lc[ii][0].getRow()][lc[ii][0].getCol()] = boardPiece[k.getRow()][k.getCol()];
                                boardPiece[k.getRow()][k.getCol()] = null;
                                boardPiece[lc[ii][1].getRow()][lc[ii][1].getCol()] = boardPiece[r[ii].getRow()][r[ii].getCol()];
                                boardPiece[r[ii].getRow()][r[ii].getCol()] = null;
                                chessboardSameColor[lc[ii][0].getRow()][lc[ii][0].getCol()] = chessboardSameColor[k.getRow()][k.getCol()];
                                chessboardSameColor[k.getRow()][k.getCol()] = null;
                                chessboardSameColor[lc[ii][1].getRow()][lc[ii][1].getCol()] = chessboardSameColor[r[ii].getRow()][r[ii].getCol()];
                                chessboardSameColor[r[ii].getRow()][r[ii].getCol()] = null;
                                chessboardSameColor[lc[ii][1].getRow()][lc[ii][1].getCol()].setId(lc[ii][1]);
                                setId(lc[ii][0]);
                                Board.baurd(imageView, lc[ii][0]);
                                Board.baurd(chessboardSameColor[lc[ii][1].getRow()][lc[ii][1].getCol()].imageView, lc[ii][1]);
                                Board.invisible_views();
                                Board.enableWhitePiece(w);
                                Board.enableBlackPiece(b);
                            }
                        });
                    }
            }
        }
    }
}
