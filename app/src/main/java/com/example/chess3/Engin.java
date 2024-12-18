package com.example.chess3;

import android.util.Log;

import java.util.ArrayList;

public class Engin {
    static double w = 0;


    public double alphaBeta(int depth, double alpha, double beta, boolean isMax) {
        if (depth == 0) {
            w++;
            Log.d(w + "  ", "sss");
            return evaluation();
        }
        Piece[][] piecesMove;
        Piece[][] boardPiece;
        Piece[][] sameBoardPiece;
        Piece[][] invertBoardPiece;
        ArrayList<movementLocation> arrayList = new ArrayList<>();
        Boolean castleKing[][] = new Boolean[2][3];
        Boolean eatingPassing[][] = new Boolean[2][8];
        movementLocation id = new movementLocation();
        Piece piece = new Piece();
        boolean bb = true;
        double max;
        double min;
        double s;
        int ll;
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
        if (isMax) {
            boardPiece = Board.boardPiece;
            sameBoardPiece = Board.whiteBoardPiece;
            invertBoardPiece = Board.blackBoardPiece;
            piecesMove = Board.whitePiece;
            ll = 0;
        } else {
            boardPiece = Board.boardPiece;
            sameBoardPiece = Board.blackBoardPiece;
            invertBoardPiece = Board.whiteBoardPiece;
            piecesMove = Board.blackPiece;
            ll = 7;
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (piecesMove[i][j].getExistPiece()) {
                    arrayList.addAll(piecesMove[i][j].move_piece(false));
                    for (int k = 0; k < arrayList.size(); k++) {

                        for (int l = 0; l < 2; l++) {
                            for (int m = 0; m < 3; m++) {
                                castleKing[l][m] = Board.caslKing[l][m];
                            }
                        }
                        for (int l = 0; l < 2; l++) {
                            for (int m = 0; m < 8; m++) {
                                eatingPassing[l][m] = Board.eating_passing[l][m];
                            }
                        }
                        if (boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] == null) {
                            bb = true;
                            id.setId(piecesMove[i][j].getId());
                            boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = boardPiece[id.getRow()][id.getCol()];
                            boardPiece[id.getRow()][id.getCol()] = null;
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = sameBoardPiece[id.getRow()][id.getCol()];
                            sameBoardPiece[id.getRow()][id.getCol()] = null;
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setId(arrayList.get(k));
                        } else if (invertBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] != null) {
                            bb = false;
                            id.setId(piecesMove[i][j].getId());
                            piece = boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
                            boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = boardPiece[id.getRow()][id.getCol()];
                            boardPiece[id.getRow()][id.getCol()] = null;
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = sameBoardPiece[id.getRow()][id.getCol()];
                            sameBoardPiece[id.getRow()][id.getCol()] = null;
                            invertBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(false);
                            invertBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = null;
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setId(arrayList.get(k));
                        }
                        if (piecesMove[i][j].getType_piece() == 6 && piecesMove[i][j].getId().getRow() == ll) {
                            piecesMove[i][j].setType_piece(2);
                            ll = 99;
                        }



                        s = alphaBeta(depth - 1, alpha, beta, !isMax);


                        if (ll == 99) {
                            piecesMove[i][j].setType_piece(6);
                            ll = 0;
                        }

                        boardPiece[id.getRow()][id.getCol()] = boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
                        if (bb) {
                            boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = null;
                            sameBoardPiece[id.getRow()][id.getCol()] = sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = null;
                        } else {
                            boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = piece;

                            sameBoardPiece[id.getRow()][id.getCol()] = sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
                            sameBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = null;

                            invertBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()] = piece;

                            invertBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(true);

                        }
                        sameBoardPiece[id.getRow()][id.getCol()].setId(id);

                        for (int l = 0; l < 2; l++) {
                            for (int m = 0; m < 3; m++) {
                                Board.caslKing[l][m] = castleKing[l][m];
                            }
                        }

                        for (int l = 0; l < 2; l++) {
                            for (int m = 0; m < 8; m++) {
                                Board.eating_passing[l][m] = eatingPassing[l][m];
                            }
                        }


                        if (isMax) {
                            max = Math.max(max, s);
                            alpha = Math.max(s, alpha);

                        } else {
                            if (min > s && depth == MainActivity.depth) {
                                MainActivity.movementLocation.setId(arrayList.get(k));
                                MainActivity.piece = piecesMove[i][j];
                            }
                            min = Math.min(min, s);

                            beta = Math.min(s, beta);

                        }

                        if (alpha >= beta) {

                            if (isMax) {

                                return max;

                            } else {

                                return min;
                            }
                        }

                    }

                }
                arrayList = new ArrayList<>();


            }
        }
        if (isMax) {

            return max;

        } else {

            return min;
        }

    }

//    public double  a(int depth, double alpha, double beta, boolean isMax) {
//        if (depth==0){
//            w++;
//            Log.d(w+"  ","sss");
//            return evaluation();}
//        movementLocation movementLocation=new movementLocation();
//        Piece piece = null;
//        boolean aa = false;
//        double a = Double.NEGATIVE_INFINITY;
//        double b=Double.POSITIVE_INFINITY;
//        ArrayList<movementLocation> arrayList=new ArrayList<>();
//        if (isMax){
//            for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (Board.whitePiece[i][j].getExistPiece()){
//                        Log.d(Board.whitePiece[i][j].getType_piece()+" type piece","ssss");
//                        arrayList.addAll(Board.whitePiece[i][j].move_piece(false));
//                        for (int k = 0; k < arrayList.size(); k++) {
//
//                            if (Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]==null){
//                                aa=true;
//                                movementLocation.setId(Board.whitePiece[i][j].getId());
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setId(arrayList.get(k));
//
//                            } else if (Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]!=null) {
//                               aa=false;
//                                movementLocation.setId(Board.whitePiece[i][j].getId());
//                                piece=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(false);
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                            }
//                        a=a(depth-1,alpha,beta,!isMax);
//                            if (aa){
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()].setId(movementLocation);
//                            }else{
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=piece;
//                                Board.whiteBoardPiece[movementLocation.getRow()][movementLocation.getCol()]= Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=piece;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(true);
//                            }
//                            if (a>alpha)
//                                alpha=a;
//                            if (alpha>=beta)
//                                return beta;
//
//                        }
//                        arrayList=new ArrayList<>();
//
//                    }
//                }
//            }
//
//            if (alpha<=beta)
//                return alpha;
//            else return beta;
//        }else {
//            for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (Board.blackPiece[i][j].getExistPiece()){
//                        arrayList.addAll(Board.blackPiece[i][j].move_piece(false));
//                        for (int k = 0; k < arrayList.size(); k++) {
//
//                            if (Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]==null){
//                                aa=true;
//                                movementLocation.setId(Board.blackPiece[i][j].getId());
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setId(arrayList.get(k).getId());
//                            } else if (Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]!=null) {
//                                aa=false;
//                                movementLocation.setId(Board.blackPiece[i][j].getId());
//                                piece=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()];
//                                Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=null;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(false);
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                            }
//                            bb=b;
//                            b=a(depth-1,alpha,beta,!isMax);
//                            if (b<=bb && depth==2){
//                                MainActivity.movementLocation.setId(arrayList.get(k));
//                                piece=Board.blackPiece[i][j];
//                            }
//
//                                if (aa){
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()].setId(movementLocation);
//                            }else{
//                                Board.boardPiece[movementLocation.getRow()][movementLocation.getCol()]=Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.boardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=piece;
//                                Board.blackBoardPiece[movementLocation.getRow()][movementLocation.getCol()]= Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()];
//                                Board.blackBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=null;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()]=piece;
//                                Board.whiteBoardPiece[arrayList.get(k).getRow()][arrayList.get(k).getCol()].setExistPiece(true);
//                            }
//                            if (b<beta)
//                                beta=b;
//                            if (alpha>=beta)
//                                return beta;
//                        }
//                        arrayList=new ArrayList<>();
//
//
//                    }
//                }
//
//            }
//            if(depth==5){
//
//            }
//            if (alpha>=beta)
//                return alpha;
//            else return beta;
//        }
//    }


    static double evaluation() {
        double s = 0;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 8; j++)
                if (Board.whitePiece[i][j].getExistPiece()) {
                    if (Board.whitePiece[i][j].getType_piece() == 1 && !Board.whitePiece[i][j].getExistPiece()) {
                        return -5000;
                    } else if (Board.whitePiece[i][j].getType_piece() == 2) {
                        s = s + 10;
                    } else if (Board.whitePiece[i][j].getType_piece() == 3) {
                        s = s + 3.3;
                    } else if (Board.whitePiece[i][j].getType_piece() == 4) {
                        s = s +3;
                    } else if (Board.whitePiece[i][j].getType_piece() == 5) {
                        s = s +5;
                    } else if (Board.whitePiece[i][j].getType_piece() == 6) {
                        s = s +1 ;
                    }

                }

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 8; j++)
                if (Board.blackPiece[i][j].getExistPiece()) {
                    if (Board.blackPiece[i][j].getType_piece() == 1 && !Board.blackPiece[i][j].getExistPiece()) {
                        return 5000;
                    }
                    if (Board.blackPiece[i][j].getType_piece() == 2) {
                        s = s - 10;
                    } else if (Board.blackPiece[i][j].getType_piece() == 3) {
                        s = s - 3.3;
                    } else if (Board.blackPiece[i][j].getType_piece() == 4) {
                        s = s -3;
                    } else if (Board.blackPiece[i][j].getType_piece() == 5) {
                        s = s - 5;
                        ;
                    } else if (Board.blackPiece[i][j].getType_piece() == 6) {
                        s = s - 1;
                    }

                }
        return s;
    }

}