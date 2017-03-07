/*
    ActivityMain.java
    David Wartenbe

    Main activity of application.
*/

package com.example.david.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class ActivityMain extends Activity {

    //Defaults to false, when settings are changed it will reflect that after intent
    private boolean DIFFICULTY_INTERMEDIATE = false;
    private final char PLAYER = 'X';
    private final char AI = 'O';
    private final char TIE = 'A';
    private final CharSequence POPUP_MESSAGE = "This position has been taken";

    public void clearBoard() {
        //Refresh the game
        TextView[] tv = returnTextView();
        for (TextView aTv : tv) {
            aTv.setText(R.string.unselected_slot);
            aTv.setBackgroundColor(getResources().getColor(R.color.offWhite));
        }
    }

    public void setAISlot(TextView tv) {
        //Used for when AI picks a slot
        tv.setText(R.string.computer_slot);
        tv.setBackgroundColor(getResources().getColor(R.color.aiColor));
    }

    public TextView[] returnTextView() {
        //Build an array of the TextViews and return it
        TextView tvOne = (TextView) findViewById(R.id.slotOne);
        TextView tvTwo = (TextView) findViewById(R.id.slotTwo);
        TextView tvThree = (TextView) findViewById(R.id.slotThree);
        TextView tvFour = (TextView) findViewById(R.id.slotFour);
        TextView tvFive = (TextView) findViewById(R.id.slotFive);
        TextView tvSix = (TextView) findViewById(R.id.slotSix);
        TextView tvSeven = (TextView) findViewById(R.id.slotSeven);
        TextView tvEight = (TextView) findViewById(R.id.slotEight);
        TextView tvNine = (TextView) findViewById(R.id.slotNine);

        TextView tvArry[] = new TextView[9];
        tvArry[0] = tvOne;
        tvArry[1] = tvTwo;
        tvArry[2] = tvThree;
        tvArry[3] = tvFour;
        tvArry[4] = tvFive;
        tvArry[5] = tvSix;
        tvArry[6] = tvSeven;
        tvArry[7] = tvEight;
        tvArry[8] = tvNine;

        return tvArry;
    }

    public void showWin(char player) {
        //Used when confirmed win or tie happens
        //Displays popup dialog box showing winner or tie
        if (player == PLAYER) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("You Won!");
            dlgAlert.setTitle("Congratulations!");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearBoard();
                        }
                    });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        } else if (player == AI) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("You lost.");
            dlgAlert.setTitle("Terrible News");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearBoard();
                        }
                    });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        } else if (player == TIE) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("The game is a Tie.");
            dlgAlert.setTitle("Draw");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearBoard();
                        }
                    });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();
        }
    }

    public boolean checkWin(char p) {
        //Based on parameter player, will check if either player or AI won the game
        TextView[] tv = returnTextView();
        CharSequence player = Character.toString(p);

        //row
        for (int x = 0; x <= 6; x += 3){
            if (tv[x].getText().equals(player)
                    && tv[x + 1].getText().equals(player)
                    && tv[x + 2].getText().equals(player)) {
                return true;
            }
        }

        //column
        for (int x = 0; x < 3; x++){
            if (tv[x].getText().equals(player)
                    && tv[x + 3].getText().equals(player)
                    && tv[x + 6].getText().equals(player)) {
                return true;
            }
        }

        //diagonal
        if (tv[0].getText().equals(player)
                && tv[4].getText().equals(player)
                && tv[8].getText().equals(player)){
            return true;
        }
        else if (tv[2].getText().equals(player)
                && tv[4].getText().equals(player)
                && tv[6].getText().equals(player)){
            return true;
        }

        return false;
    }

    public boolean checkIfAvailable(View view) {
        //Check to see if the slot chosen by the player is available
        TextView tv = (TextView) view;
        return tv.getText().toString().equals(getString(R.string.unselected_slot));
    }

    public void playAdvancedAI() {
        //Advanced logic. Fall through moves based on priority
        TextView[] tv = returnTextView();

        //OFFENSIVE AI
        //Split Pair
        //Left and Right
        //check for O on outside, fill middle
        if (tv[0].getText().toString().equals(Character.toString(AI)) && tv[2].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[1])) {
            setAISlot(tv[1]);
        } else if (tv[3].getText().toString().equals(Character.toString(AI)) && tv[5].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        } else if (tv[6].getText().toString().equals(Character.toString(AI)) && tv[8].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[7])) {
            setAISlot(tv[7]);
        }
        //Top and Bottom
        else if (tv[0].getText().toString().equals(Character.toString(AI)) && tv[6].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[3])) {
            setAISlot(tv[3]);
        } else if (tv[1].getText().toString().equals(Character.toString(AI)) && tv[7].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        } else if (tv[2].getText().toString().equals(Character.toString(AI)) && tv[8].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[5])) {
            setAISlot(tv[5]);
        }

        //ADJACENT
        //Left to right
        else if (tv[0].getText().toString().equals(Character.toString(AI)) && tv[1].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        } else if (tv[3].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[5])) {
            setAISlot(tv[5]);
        } else if (tv[6].getText().toString().equals(Character.toString(AI)) && tv[7].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        }
        //Right to Left
        else if (tv[1].getText().toString().equals(Character.toString(AI)) && tv[2].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        } else if (tv[4].getText().toString().equals(Character.toString(AI)) && tv[5].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[3])) {
            setAISlot(tv[3]);
        } else if (tv[8].getText().toString().equals(Character.toString(AI)) && tv[7].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        }
        //Top to Bottom
        else if (tv[0].getText().toString().equals(Character.toString(AI)) && tv[3].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        } else if (tv[1].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[7])) {
            setAISlot(tv[7]);
        } else if (tv[2].getText().toString().equals(Character.toString(AI)) && tv[5].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        }
        //Bottom to Top
        else if (tv[6].getText().toString().equals(Character.toString(AI)) && tv[3].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        } else if (tv[7].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[1])) {
            setAISlot(tv[1]);
        } else if (tv[8].getText().toString().equals(Character.toString(AI)) && tv[5].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        }

        //DIAGONAL
        //Top to Bottom
        else if (tv[0].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        } else if (tv[2].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        }
        //Bottom to Top
        else if (tv[6].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        } else if (tv[8].getText().toString().equals(Character.toString(AI)) && tv[4].getText().toString().equals(Character.toString(AI)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        }

        //
        //DEFENSIVE AI
        //
        //1 and 3 go 2
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[2].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[1])) {
            setAISlot(tv[1]);
        }
        //if 1 and 7 fo 4
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[3])) {
            setAISlot(tv[3]);
        }
        //if 1 and 9 go 5
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        }
        //if 3 and 7 go 5
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        }
        //if 7 and 5 go 3
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        }
        //if 9 and 6 go 3
        else if (tv[8].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        }
        //if 3 and 5 go 7
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        }
        //if 2 and 8 go 5
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        }
        //if 3 and 9 go 6
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[5])) {
            setAISlot(tv[5]);
        }
        //if 4 and 6 go 5
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4])) {
            setAISlot(tv[4]);
        }
        //if 3 and 6 go 9
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        }
        //if 1 and 2 go 3
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[1].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[2]);
        }
        //if 1 and 4 go 7
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[3].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        }
        //if 1 and 5 go 9
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        }
        //if 2 and 3 go 1
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[2].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        }
        //if 4 and 7 go 1
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        }
        //if 5 and 9 go 1
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[0])) {
            setAISlot(tv[0]);
        }
        //if 4 and 5 go 6
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[5])) {
            setAISlot(tv[5]);
        }
        //if 7 and 8 go 9
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[8])) {
            setAISlot(tv[8]);
        }
        //if 5 and 6 go 4
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[3])) {
            setAISlot(tv[3]);
        }
        //if 8 and 9 go 7
        else if (tv[7].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[6])) {
            setAISlot(tv[6]);
        }
        //if 2 and 5 go 8
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[7])) {
            setAISlot(tv[7]);
        }
        //if 5 and 8 go 2
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[1])) {
            setAISlot(tv[1]);
        }
        //if 7 and 9 go 8
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[7])) {
            setAISlot(tv[7]);
        }
        //if 5 and 7 go 3
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2])) {
            setAISlot(tv[7]);
        } else {
            randomAISlot();
        }

        //AI picked a slot, check to see if it won
        if (checkDraw()) {
            showWin(TIE);
        }
        if (checkWin(AI)) {
            showWin(AI);
        }
    }

    public void randomAISlot(){
        TextView[] tv = returnTextView();
        Random randomGen = new Random();
        int slot = randomGen.nextInt(9);

        switch (slot) {
            case 0:
                if (checkIfAvailable(tv[0])) {
                    setAISlot(tv[0]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 1:
                if (checkIfAvailable((tv[1]))) {
                    setAISlot(tv[1]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 2:
                if (checkIfAvailable(tv[2])) {
                    setAISlot(tv[2]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 3:
                if (checkIfAvailable(tv[3])) {
                    setAISlot(tv[3]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 4:
                if (checkIfAvailable(tv[4])) {
                    setAISlot(tv[4]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 5:
                if (checkIfAvailable(tv[5])) {
                    setAISlot(tv[5]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 6:
                if (checkIfAvailable(tv[6])) {
                    setAISlot(tv[6]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 7:
                if (checkIfAvailable(tv[7])) {
                    setAISlot(tv[7]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
            case 8:
                if (checkIfAvailable(tv[8])) {
                    setAISlot(tv[8]);
                    if (checkWin(AI)) showWin(AI);
                }
                else randomAISlot();
                break;
        }
    }

    public void playIntermediateAI() {
        //This is the Intermediate AI, pretty much just defensive AI.
        TextView[] tv = returnTextView();

        if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[2].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[1]))
        {
            setAISlot(tv[1]);
        }
        //if 1 and 7 fo 4
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[3]))
        {
            setAISlot(tv[3]);
        }
        //if 1 and 9 go 5
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4]))
        {
            setAISlot(tv[4]);
        }
        //if 3 and 7 go 5
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4]))
        {
            setAISlot(tv[4]);
        }
        //if 7 and 5 go 3
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2]))
        {
            setAISlot(tv[2]);
        }
        //if 9 and 6 go 3
        else if (tv[8].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2]))
        {
            setAISlot(tv[2]);
        }
        //if 3 and 5 go 7
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[6]))
        {
            setAISlot(tv[6]);
        }
        //if 2 and 8 go 5
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4]))
        {
            setAISlot(tv[4]);
        }
        //if 3 and 9 go 6
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[5]))
        {
            setAISlot(tv[5]);
        }
        //if 4 and 6 go 5
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[4]))
        {
            setAISlot(tv[4]);
        }
        //if 3 and 6 go 9
        else if (tv[2].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[8]))
        {
            setAISlot(tv[8]);
        }
        //if 1 and 2 go 3
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[1].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[2]))
        {
            setAISlot(tv[2]);
        }
        //if 1 and 4 go 7
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[3].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[6]))
        {
            setAISlot(tv[6]);
        }
        //if 1 and 5 go 9
        else if (tv[0].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER)) &&
                checkIfAvailable(tv[8]))
        {
            setAISlot(tv[8]);
        }
        //if 2 and 3 go 1
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[2].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[0]))
        {
            setAISlot(tv[0]);
        }
        //if 4 and 7 go 1
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[0]))
        {
            setAISlot(tv[0]);
        }
        //if 5 and 9 go 1
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[0]))
        {
            setAISlot(tv[0]);
        }
        //if 4 and 5 go 6
        else if (tv[3].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[5]))
        {
            setAISlot(tv[5]);
        }
        //if 7 and 8 go 9
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[8]))
        {
            setAISlot(tv[8]);
        }
        //if 5 and 6 go 4
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[5].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[3]))
        {
            setAISlot(tv[3]);
        }
        //if 8 and 9 go 7
        else if (tv[7].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[6]))
        {
            setAISlot(tv[6]);
        }
        //if 2 and 5 go 8
        else if (tv[1].getText().toString().equals(Character.toString(PLAYER)) && tv[4].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[7]))
        {
            setAISlot(tv[7]);
        }
        //if 5 and 8 go 2
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[7].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[1]))
        {
            setAISlot(tv[1]);
        }
        //if 7 and 9 go 8
        else if (tv[6].getText().toString().equals(Character.toString(PLAYER)) && tv[8].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[7]))
        {
            setAISlot(tv[7]);
        }
        //if 5 and 7 go 3
        else if (tv[4].getText().toString().equals(Character.toString(PLAYER)) && tv[6].getText().toString().equals(Character.toString(PLAYER))&&
                checkIfAvailable(tv[2]))
        {
            setAISlot(tv[7]);
        }else {
            Random randomGen = new Random();
            int slot = randomGen.nextInt(8);

            switch (slot) {
                case 0:
                    if (checkIfAvailable(tv[0])) {
                        setAISlot(tv[0]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 1:

                    if (checkIfAvailable((tv[1]))) {
                        setAISlot(tv[1]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 2:

                    if (checkIfAvailable(tv[2])) {
                        setAISlot(tv[2]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 3:

                    if (checkIfAvailable(tv[3])) {
                        setAISlot(tv[3]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 4:

                    if (checkIfAvailable(tv[4])) {
                        setAISlot(tv[4]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 5:

                    if (checkIfAvailable(tv[5])) {
                        setAISlot(tv[5]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 6:

                    if (checkIfAvailable(tv[6])) {
                        setAISlot(tv[6]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 7:

                    if (checkIfAvailable(tv[7])) {
                        setAISlot(tv[7]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
                case 8:

                    if (checkIfAvailable(tv[8])) {
                        setAISlot(tv[8]);
                        if (checkWin(AI)) {
                            showWin(AI);
                        }
                    }else {
                        playIntermediateAI();
                    }
                    break;
            }
        }
        if (checkDraw()) {
            showWin(TIE);
        }
        if (checkWin(AI)) {
            showWin(AI);
        }

    }

    public boolean checkDraw() {
        TextView[] tv = returnTextView();

        //If no slot is available return true: Nothing available, Game is a draw, End Game
        return (!tv[0].getText().toString().equals(getString(R.string.unselected_slot))) && (!tv[1].getText().toString().equals(getString(R.string.unselected_slot)))
                && (!tv[2].getText().toString().equals(getString(R.string.unselected_slot))) && (!tv[3].getText().toString().equals(getString(R.string.unselected_slot)))
                && (!tv[4].getText().toString().equals(getString(R.string.unselected_slot))) && (!tv[5].getText().toString().equals(getString(R.string.unselected_slot)))
                && (!tv[6].getText().toString().equals(getString(R.string.unselected_slot))) && (!tv[7].getText().toString().equals(getString(R.string.unselected_slot)))
                && (!tv[8].getText().toString().equals(getString(R.string.unselected_slot)));
    }

    public void clickSlot(View view) {
        //Essentially the starting method that begins the game
        //Check if slot is available, if it is set it to the user
        //Check win
        //Check draw
        //If noting else run the AI
        TextView tv = (TextView) findViewById(view.getId());

        if (tv.getText().toString().equals(getString(R.string.unselected_slot))) {
            tv.setText(getString(R.string.user_slot));
            tv.setBackgroundColor(getResources().getColor(R.color.playerColor));

            if (checkWin(PLAYER)) {
                showWin(PLAYER);
            }
            else if (checkDraw()) {
                showWin(TIE);
            }else if (DIFFICULTY_INTERMEDIATE) {
                playIntermediateAI();
            } else {
                playAdvancedAI();
            }

        //If the user picks a slot that is unavailable, a popup message will inform the user
        }else{
            //Context context = getApplicationContext();
            //toast.cancel();
            Toast toast = Toast.makeText(getApplicationContext(), POPUP_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extra = getIntent().getExtras();
        if (extra == null) return; //Should not be needed but stops app from crashing

        String message = extra.getString(Intent.EXTRA_TEXT);
        if (message != null && message.equals("Intermediate")) {
            DIFFICULTY_INTERMEDIATE = true;
        } else if (message != null && message.equals("Impossible")) {
            DIFFICULTY_INTERMEDIATE = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
            How Intent and difficulty settings are intertwined:
             From main we grab the current state of DIFFICULTY_INTERMEDIATE variable,
             then based on the bool we send a different message to ActivitySettings
             so that the correct radio button it checked since Impossible is default.

             From settings we do a similar thing but the deciding factor here is whether
             the intermediate radio button is checked. When we send the intent back to main
             we will be sending the current difficulty value to be assigned.

             All this allows for a difficulty to be set and have the game reflect it.
         */
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ActivitySettings.class);
            intent.setAction(Intent.EXTRA_TEXT);
            if (DIFFICULTY_INTERMEDIATE) {
                intent.putExtra(Intent.EXTRA_TEXT, "Intermediate");
            } else {
                intent.putExtra(Intent.EXTRA_TEXT, "Impossible");
            }

            startActivity(intent);
        }
        else if (id == R.id.action_about){
            Intent intent = new Intent(this, ActivityAbout.class);
            startActivity(intent);
        }
        else if (id == R.id.action_restart) {
            clearBoard();
        }

        return super.onOptionsItemSelected(item);
    }

}
