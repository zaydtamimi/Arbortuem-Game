package comp1110.ass2;

import java.sql.Array;
import java.util.*;

public class Arboretum {

    /** initial State of the game
     *
     */
    public static ArrayList<String> initialState(){
        ArrayList<String> init = new ArrayList<>();
        // Turn
        init.add("A");
        // aArbortuem
        init.add("");
        // aDiscrad
        init.add("");
        // bArbortuem
        init.add("");
        // bDiscard
        init.add("");
        // Get random order of Deck
        List<String> decklist = new ArrayList<>(Arrays.asList("a1","a2","a3","a4","a5","a6","a7","a8","b1","b2","b3","b4","b5","b6","b7","b8","c1","c2","c3","c4","c5","c6","c7","c8","d1","d2","d3","d4","d5","d6","d7","d8","j1","j2","j3","j4","j5","j6","j7","j8","m1","m2","m3","m4","m5","m6","m7","m8"));
        Collections.shuffle(decklist);
        Random rand = new Random();
        String aHand = "";
        String bHand = "";
        for(int i = 0; i < 2; i++) {
            String hand = "";
            for (int j = 0; j < 7; j++) {
                int randomcard = rand.nextInt(48 - i * 7 - j);
                hand += decklist.get(randomcard);
                decklist.remove(decklist.get(randomcard));
            }
            // Hand
            if (i == 0)
                aHand = hand;
            else
                bHand = hand;
        }
        String deck = "";
        for (int i = 0;  i < decklist.size(); i++){
            deck += decklist.get(i);
        }
        init.add(deck);
        init.add(aHand);
        init.add(bHand);
        for(int i = 0; i< 7; i++)
            System.out.println(init.get(i));
        return init;
    }

    /**
     * A hiddenState string array is well-formed if it complies with the following rules:
     *
     * hiddenState[0] - Deck
     *                      A number of 2-character card substrings sorted alphanumerically as defined below
     *                      For example: "a3a8b5b6c2c7d1d3d7d8m1"
     *
     * hiddenState[1] - Player A's hand
     *                      0th character is 'A'
     *                      Followed by 7, 8 or 9 2-character card substrings sorted alphanumerically.
     *                      For example a full hand String might look like: "Ab3b4c1j1m2m5m8"
     *
     * hiddenState[2] - Player B's hand
     *                      0th character is 'B'
     *                      Followed by 7, 8 or 9 2-character _card_ substrings
     *                      For example: "Ba6b7b8c8d2j2j8"
     *
     * Where:
     * "card substring" - A 2-character string that represents a single card.
     *                     0th character is 'a', 'b', 'c', 'd', 'j', or 'm' representing the card species.
     *                     1st character is a sequential digit from '1' to '8', representing the value of the card.
     *                     For example: "d7"
     *
     * "alphanumerical(ly)" - This means that cards are sorted first alphabetically and then numerically.
     *                     For example, "m2" appears before "m5" because 2 < 5
     *                     Whilst "b4" appears before "c1" because alphabetical ordering takes precedence over
     *                     numerical ordering.
     *
     * Exceptions:
     *      - If the deck is empty, hiddenState[0] will be the empty string ""
     *      - If a player's hand is empty, then the corresponding hiddenState[i] will contain only the player's ID.
     *      For example: if player A's hand is empty then hiddenState[1] will be "A" with no other characters.
     *
     * @param hiddenState the hidden state array.
     * @return true if the hiddenState array is well-formed, false if it is not well-formed.
     *
     */
    public static boolean isHiddenStateWellFormed(String[] hiddenState) {

        if(hiddenState==null||hiddenState.length==0)
        {
            return false;
        }


        if(hiddenState.length!=3)
        {
            return false;
        }


        if((hiddenState[0]==null||hiddenState[0].length()==0)&&(hiddenState[1].length()==0)&&(hiddenState[2].length()==0) )                     //judge the hiddenState[0]
        {

            return false;
        }
        if((hiddenState[1].length()==0&&hiddenState[2].length()!=0)||(hiddenState[2].length()==0&&hiddenState[1].length()!=0))
        {
            return false;
        }

        if (hiddenState[0]!=null&&hiddenState[0].length()!=0){
            if(hiddenState[0].length()%2!=0){

                return false;
            }
            else{
                char[] test_hiddenState0=hiddenState[0].toCharArray();
                for(int i=0;i<test_hiddenState0.length;i++)
                {
                    if(i%2==0)
                    {
                        if(test_hiddenState0[i]!='a'&&test_hiddenState0[i]!='b'&&test_hiddenState0[i]!='c'&&test_hiddenState0[i]!='d'&&test_hiddenState0[i]!='j'&&test_hiddenState0[i]!='m')
                        {

                            return false;
                        }
                        if(i-2>0) {
                            if (test_hiddenState0[i] < test_hiddenState0[i - 2]) {
                                return false;

                            }
                        }
                    }
                    else
                    {
                        if(test_hiddenState0[i]<'1'||test_hiddenState0[i]>'8')
                        {return false;}
                        if(i-3>0) {
                            if (test_hiddenState0[i-1] == test_hiddenState0[i - 3])
                            {
                                if(test_hiddenState0[i]<test_hiddenState0[i-2])
                                {
                                    return false;
                                }


                            }
                        }
                    }
                }
            }



            char[] test_hiddenStateA=hiddenState[1].toCharArray();    //judge the hiddenState[1]


            if(test_hiddenStateA[0]!='A')
            {
                return false;
            }
            else
            {
                for(int i=1;i< test_hiddenStateA.length;i++)
                {

                    if(i%2==1)
                    {
                        if(test_hiddenStateA[i]!='a'&&test_hiddenStateA[i]!='b'&&test_hiddenStateA[i]!='c'&&test_hiddenStateA[i]!='d'&&test_hiddenStateA[i]!='j'&&test_hiddenStateA[i]!='m')
                        {
                            return false;
                        }

                        if(i-2>0) {
                            if (test_hiddenStateA[i] < test_hiddenStateA[i - 2]) {
                                return false;

                            }
                        }

                    }
                    if(i%2==0)
                    {
                        if(test_hiddenStateA[i]<'1'||test_hiddenStateA[i]>'8')
                        {
                            return false;
                        }
                        if(i-3>0) {
                            if (test_hiddenStateA[i-1] == test_hiddenStateA[i - 3])
                            {
                                if(test_hiddenStateA[i]<test_hiddenStateA[i-2])
                                {
                                    return false;
                                }


                            }
                        }
                    }
                }
            }



            char[] test_hiddenStateB=hiddenState[2].toCharArray();    //judge the hiddenState[2]
            if(test_hiddenStateB[0]!='B')
            {
                return false;
            }

            for(int i=1;i< test_hiddenStateB.length;i++)
            {
                if(i%2==1)
                {
                    if(test_hiddenStateB[i]!='a'&&test_hiddenStateB[i]!='b'&&test_hiddenStateB[i]!='c'&&test_hiddenStateB[i]!='d'&&test_hiddenStateB[i]!='j'&&test_hiddenStateB[i]!='m')
                    {
                        return false;
                    }
                    if(i-2>0) {
                        if (test_hiddenStateB[i] < test_hiddenStateB[i - 2]) {
                            return false;

                        }
                    }

                }
                if(i%2==0)
                {
                    if(test_hiddenStateB[i]<'1'||test_hiddenStateB[i]>'8')
                    {
                        return false;
                    }
                    if(i-3>0) {
                        if (test_hiddenStateB[i-1] == test_hiddenStateB[i - 3])
                        {
                            if(test_hiddenStateB[i]<test_hiddenStateB[i-2])
                            {
                                return false;
                            }


                        }
                    }
                }
            }

        }


        return true;
        //FIXME TASK 3
    }


    /**
     * A sharedState string array is well-formed if it complies with the following rules:
     *
     * sharedState[0] - a single character ID string, either "A" or "B" representing whose turn it is.
     * sharedState[1] - Player A's arboretum
     *                     0th character is 'A'
     *                     Followed by a number of 8-character _placement_ substrings as defined below that occur in
     *                       the order they were played. Note: these are NOT sorted alphanumerically.
     *                     For example: "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01"
     *
     * sharedState[2] - Player A's discard
     *                    0th character is 'A'
     *                    Followed by a number of 2-character _card_ substrings that occur in the order they were
     *                    played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Aa7c4d6"
     *
     * sharedState[3] - Player B's arboretum
     *                    0th character is 'B'
     *                    Followed by a number of 8-character _placement_ substrings that occur in the order they
     *                    were played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Bj5C00C00j6S01C00j7S02W01j4C00W01m6C00E01m3C00W02j3N01W01"
     *
     * sharedState[4] - Player B's discard
     *                    0th character is 'B'
     *                    Followed by a number of 2-character _card_ substrings that occur in the order they were
     *                    played. Note: these are NOT sorted alphanumerically.
     *                    For example: "Bb2d4c5a1d5"
     *
     * Where: "card substring" and "alphanumerical" ordering are defined above in the javaDoc for
     * isHiddenStateWellFormed and placement substrings are defined as follows:
     *
     * "placement substring" - An 8-character string that represents a card placement in a player's arboretum.
     *                  - 0th and 1st characters are a 2-character card substring
     *                  - 2nd character is 'N' for North, 'S' for South, or 'C' for Centre representing the
     *                    direction of this card relative to the first card played.
     *                  - 3rd and 4th characters are a 2-digit number from "00" to "99" representing the distance
     *                    from the first card played to this card, in the direction of the 2nd character.
     *                  - 5th character is 'E' for East, 'W' for West, or 'C' for Centre representing the
     *                    direction of this card relative to the first card played.
     *                  - 6th and 7th characters are a 2-digit number from "00" to "99" representing the distance
     *                    from the first card played to this card, in the direction of the 3rd character.
     *                  For example: "a1C00C00b3N01C00" says that card "a1" was played first and card "b3" was played
     *                  one square north of the first card (which was "a1").
     *
     * Exceptions:
     * If a player's discard or arboretum are empty, (ie: there are no cards in them), then the corresponding string
     * should contain only the player ID.
     * For example:
     *  - If Player A's arboretum is empty, then sharedState[1] would be "A" with no other characters.
     *  - If Player B's discard is empty, then sharedState[4] would be "B" with no other characters.
     *
     * @param sharedState the shared state array.
     * @return true if the sharedState array is well-formed, false if it is not well-formed.
     */
    public static boolean isSharedStateWellFormed(String[] sharedState) {

        //General Check
        if(sharedState == null||sharedState.length != 5) {
            return false;
        }
        //Check SharedState[0]
        if (!sharedState[0].equals("A") && !sharedState[0].equals("B")) {
            return false;
        }
        //Check SharedState[1]
        char[] test_SharedStateA1 = sharedState[1].toCharArray();
        if (test_SharedStateA1[0] != 'A') {
            return false;
        }
        if (test_SharedStateA1.length > 1) {
            if ((test_SharedStateA1.length-1) % 8 != 0) {
                return false;
            }
            for (int i = 1; i < test_SharedStateA1.length; i+=8) {
                if (test_SharedStateA1[i] != 'a' && test_SharedStateA1[i] != 'b' && test_SharedStateA1[i] != 'c' && test_SharedStateA1[i] != 'd' && test_SharedStateA1[i] != 'j' && test_SharedStateA1[i] != 'm') {
                    return false;
                }
            }
            for (int i = 2; i < test_SharedStateA1.length; i+=8) {
                if (!(Character.getNumericValue(test_SharedStateA1[i]) <= 8) || !(Character.getNumericValue(test_SharedStateA1[i]) >= 1)) {
                    return false;
                }
            }
            for (int i = 3; i < test_SharedStateA1.length; i+=8) {
                if (test_SharedStateA1[i] != 'N' && test_SharedStateA1[i] != 'S' && test_SharedStateA1[i] != 'C') {
                    return false;
                }
            }
            for (int i = 4; i < test_SharedStateA1.length; i+=8) {
                if (!Character.isDigit(test_SharedStateA1[i]) || !Character.isDigit(test_SharedStateA1[i + 1])) {
                    return false;
                }
            }

            for (int i = 6; i < test_SharedStateA1.length; i+=8) {
                if (test_SharedStateA1[i] != 'E' && test_SharedStateA1[i] != 'W' && test_SharedStateA1[i] != 'C') {
                    return false;
                }
            }

            for (int i = 7; i < test_SharedStateA1.length; i+=8) {
                if (!Character.isDigit(test_SharedStateA1[i]) || !Character.isDigit(test_SharedStateA1[i + 1])) {
                    return false;
                }
            }
        }
        //Check SharedState[2]
        char[] test_SharedStateA2 = sharedState[2].toCharArray();
        if (test_SharedStateA2[0] != 'A') {
            return false;
        }
        if (test_SharedStateA2.length > 1) {
            if ((test_SharedStateA2.length-1) % 2 != 0) {
                return false;
            }
            for (int i = 1; i < test_SharedStateA2.length; i+=2) {
                if (test_SharedStateA2[i] != 'a' && test_SharedStateA2[i] != 'b' && test_SharedStateA2[i] != 'c' && test_SharedStateA2[i] != 'd' && test_SharedStateA2[i] != 'j' && test_SharedStateA2[i] != 'm') {
                    return false;
                }
            }
            for (int i = 2; i < test_SharedStateA2.length; i+=2) {
                if (!(Character.getNumericValue(test_SharedStateA2[i]) <= 8) || !(Character.getNumericValue(test_SharedStateA2[i]) >= 1)) {
                    return false;
                }
            }
        }
        //Check SharedState[3]
        char[] test_SharedStateB1 = sharedState[3].toCharArray();
        if (test_SharedStateB1[0] != 'B') {
            return false;
        }
        if (test_SharedStateB1.length > 1) {
            if ((test_SharedStateB1.length-1) % 8 != 0) {
                return false;
            }
            for (int i = 1; i < test_SharedStateB1.length; i+=8) {
                if (test_SharedStateB1[i] != 'a' && test_SharedStateB1[i] != 'b' && test_SharedStateB1[i] != 'c' && test_SharedStateB1[i] != 'd' && test_SharedStateB1[i] != 'j' && test_SharedStateB1[i] != 'm') {
                    return false;
                }
            }
            for (int i = 2; i < test_SharedStateB1.length; i+=8) {
                if (!(Character.getNumericValue(test_SharedStateB1[i]) <= 8) || !(Character.getNumericValue(test_SharedStateB1[i]) >= 1)) {
                    return false;
                }
            }
            for (int i = 3; i < test_SharedStateB1.length; i+=8) {
                if (test_SharedStateB1[i] != 'N' && test_SharedStateB1[i] != 'S' && test_SharedStateB1[i] != 'C') {
                    return false;
                }
            }
            for (int i = 4; i < test_SharedStateB1.length; i+=8) {
                if (!Character.isDigit(test_SharedStateB1[i]) || !Character.isDigit(test_SharedStateB1[i + 1])) {
                    return false;
                }
            }

            for (int i = 6; i < test_SharedStateB1.length; i+=8) {
                if (test_SharedStateB1[i] != 'E' && test_SharedStateB1[i] != 'W' && test_SharedStateB1[i] != 'C') {
                    return false;
                }
            }

            for (int i = 7; i < test_SharedStateB1.length; i+=8) {
                if (!Character.isDigit(test_SharedStateB1[i]) || !Character.isDigit(test_SharedStateB1[i + 1])) {
                    return false;
                }
            }
        }
        //Check SharedState[4]
        char[] test_SharedStateB2 = sharedState[4].toCharArray();
        if (test_SharedStateB2[0] != 'B') {
            return false;
        }
        if (test_SharedStateB2.length > 1) {
            if ((test_SharedStateB2.length-1) % 2 != 0) {
                return false;
            }
            for (int i = 1; i < test_SharedStateB2.length; i+=2) {
                if (test_SharedStateB2[i] != 'a' && test_SharedStateB2[i] != 'b' && test_SharedStateB2[i] != 'c' && test_SharedStateB2[i] != 'd' && test_SharedStateB2[i] != 'j' && test_SharedStateB2[i] != 'm') {
                    return false;
                }
            }
            for (int i = 2; i < test_SharedStateB2.length; i+=2) {
                if (!(Character.getNumericValue(test_SharedStateB2[i]) <= 8) || !(Character.getNumericValue(test_SharedStateB2[i]) >= 1)) {
                    return false;
                }
            }
        }
        return true;
        //FIXME TASK 4
    }

    /**
     * Given a deck string, draw a random card from the deck.
     * You may assume that the deck string is well-formed.
     *
     * @param deck the deck string.
     * @return a random cardString from the deck. If the deck is empty, return the empty string "".
     * TASK 5 by Xiaodan Du
     */
    public static String drawFromDeck(String deck) {
        int len = deck.length(); // Numbers of cards in deck
        String type = "abcdjm";  // Chars that presents the type
        String num = "12345678"; // Numbers of each types of cards
        for (int i = 0; i < (len / 2); i++){  // Judge deck string is well-formed(one char with one number and both in type and num string)
            if ((type.indexOf(deck.charAt(2*i)) == -1) || (num.indexOf(deck.charAt(2*i+1)) == -1))
                return "";
        }
        if (deck.isEmpty()) // Judge deck string is empty
            return "";
        else {
            Random rand = new Random();
            int ran = rand.nextInt(len/2); // Random of numbers of deck
            return deck.substring(2*ran,2*ran+2);
        }

                                                  // FIXME TASK 5
    }

    /**
     * Determine whether this placement is valid for the current player. The "Turn String" determines who is making
     * this placement.
     *
     * A placement is valid if the following conditions are met:
     *
     * - The card is placed adjacent to a card that is already placed, or is placed in the position "C00C00" if this is
     * the first card placed.
     * - The card does not share a location with another card that has been placed by this player.
     * - The card being placed is currently in the hand of this player.
     * - The hand of this player has exactly 9 cards in it.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param placement the placement string of the card to be placed
     * @return false if the placement is valid, false if it is not valid.
     */
    public static boolean isPlacementValid(String[][] gameState, String placement) {
        int board_index;
        int hand_index;
        char[] board_array;
        if (gameState[0][0].equals("A")) {
            board_index = 1;
            hand_index = 1;
        } else {
            board_index = 3;
            hand_index = 2;
        }
        //Check player hand has exactly 9 cards
        if ((gameState[1][hand_index].length() - 1) != 18) {
            return false;
        }
        //Check card being played is in the hand of this player
        if (gameState[1][hand_index].indexOf(placement.substring(0,2)) == -1) {
            return false;
        }
        //Initiate board_array for reference
        board_array = gameState[0][board_index].toCharArray();

        //Check placement validity
        if (board_array.length == 1) {
            if (placement.substring(2).equals("C00C00")) {
                return true;
            } else {
                return false;
            }
        //Check if placement already exists on the board
        } else if (gameState[0][board_index].indexOf(placement.substring(2)) != -1) {
            return false;
        //Check if it is being placed next to an adjacent card
        } else {
            String first_direction_check = placement.substring(2,5);
            String second_direction_check = placement.substring(5);
            int adjacent_direction_check;
            String direction_check;
            //If placement contains C00, check the other direction component
            if (placement.indexOf("C00") != -1) {
                if (!first_direction_check.substring(0,1).equals("C")) {
                    adjacent_direction_check = Integer.valueOf(first_direction_check.substring(1)) - 1;
                    if (adjacent_direction_check == 0) {
                        return true;
                    } else if (adjacent_direction_check > 9) {
                        direction_check = first_direction_check.substring(0,1) + adjacent_direction_check + second_direction_check;
                    } else {
                        direction_check = first_direction_check.substring(0,1) + "0" + adjacent_direction_check + second_direction_check;
                    }
                } else {
                    adjacent_direction_check = Integer.valueOf(second_direction_check.substring(1)) - 1;
                    if (adjacent_direction_check == 0) {
                        return true;
                    } else if (adjacent_direction_check > 9) {
                        direction_check = first_direction_check + second_direction_check.substring(0,1) + adjacent_direction_check;
                    } else {
                        direction_check = first_direction_check + second_direction_check.substring(0,1) + "0" + adjacent_direction_check;
                    }
                }
                if (gameState[0][board_index].indexOf(direction_check) != -1) {
                    return true;
                }
            }
            //Check Up
            if (first_direction_check.substring(0, 1).equals("N")) {
                adjacent_direction_check = Integer.valueOf(first_direction_check.substring(1)) + 1;
            } else {
                adjacent_direction_check = Integer.valueOf(first_direction_check.substring(1)) - 1;
            }
            if (adjacent_direction_check == 0) {
                direction_check = "C00" + second_direction_check;
            } else if (adjacent_direction_check > 9) {
                direction_check = first_direction_check.substring(0,1) + adjacent_direction_check + second_direction_check;
            } else {
                direction_check = first_direction_check.substring(0,1) + "0" + adjacent_direction_check + second_direction_check;
            }

            if (gameState[0][board_index].indexOf(direction_check) != -1) {
                return true;
            }
            //Check Down
            if (first_direction_check.substring(0, 1).equals("N")) {
                adjacent_direction_check = Integer.valueOf(first_direction_check.substring(1)) - 1;
            } else {
                adjacent_direction_check = Integer.valueOf(first_direction_check.substring(1)) + 1;
            }
            if (adjacent_direction_check == 0) {
                direction_check = "C00" + second_direction_check;
            } else if (adjacent_direction_check > 9) {
                direction_check = first_direction_check.substring(0,1) + adjacent_direction_check + second_direction_check;
            } else {
                direction_check = first_direction_check.substring(0,1) + "0" + adjacent_direction_check + second_direction_check;
            }
            if (gameState[0][board_index].indexOf(direction_check) != -1) {
                return true;
            }
            //Check Left
            if (second_direction_check.substring(0, 1).equals("W")) {
                adjacent_direction_check = Integer.valueOf(second_direction_check.substring(1)) + 1;
            } else {
                adjacent_direction_check = Integer.valueOf(second_direction_check.substring(1)) - 1;
            }
            if (adjacent_direction_check == 0) {
                direction_check = first_direction_check + "C00";
            } else if (adjacent_direction_check > 9) {
                direction_check = first_direction_check + second_direction_check.substring(0,1) + adjacent_direction_check;
            } else {
                direction_check = first_direction_check + second_direction_check.substring(0,1) + "0" + adjacent_direction_check;
            }
            if (gameState[0][board_index].indexOf(direction_check) != -1) {
                return true;
            }

            //Check Right
            if (second_direction_check.substring(0, 1).equals("W")) {
                adjacent_direction_check = Integer.valueOf(second_direction_check.substring(1)) - 1;
            } else {
                adjacent_direction_check = Integer.valueOf(second_direction_check.substring(1)) + 1;
            }
            if (adjacent_direction_check == 0) {
                direction_check = first_direction_check + "C00";
            } else if (adjacent_direction_check > 9) {
                direction_check = first_direction_check + second_direction_check.substring(0,1) + adjacent_direction_check;
            } else {
                direction_check = first_direction_check + second_direction_check.substring(0,1) + "0" + adjacent_direction_check;
            }

            if (gameState[0][board_index].indexOf(direction_check) != -1) {
                return true;
            }
        }
        return false;
        //FIXME TASK 7
    }

    /**
     * Determine whether the given gameState is valid.
     * A state is valid if the following conditions are met:
     *
     * - There are exactly 48 cards in the game across the deck and each player's hand, arboretum and discard pile.
     * - There are no duplicates of any cards
     * - Every card in each player's arboretum is adjacent to at least one card played _before_ it.
     * - The number of card's in player B's arboretum is equal to, or one less than the number of cards in player
     * A's arboretum.
     * - Each player may have 0 cards in hand only if all cards are in the deck.
     * - Otherwise, a player has exactly 7 cards in their hand if it is not their turn.
     * - If it is a player's turn, they may have 7, 8, or 9 cards in hard.
     * - The number of cards in a player's discard pile is less than or equal to the number of cards in their arboretum.
     *
     * You may assume that the gameState array is well-formed.
     *
     * @param gameState the game state array
     * @return true if the gameState is valid, false if it is not valid.
     */
    public static boolean isStateValid(String[][] gameState) {
        int judge_number=0;


        int Deck_number=0;

        String repeat;

        char[] deck=gameState[1][0].toCharArray();
        for(int i=0;i<gameState[1][0].length();i++)
        {
            if(deck[i]>='a'&& deck[i]<='m'){
                Deck_number++;
                repeat= gameState[1][0].substring(i,i+2);

                if((gameState[0][1].contains(repeat))||gameState[0][3].contains(repeat)||gameState[0][2].contains(repeat)||gameState[0][4].contains(repeat)||gameState[1][1].contains(repeat)||gameState[1][2].contains(repeat)){

                    return false;
                }
            }
        }

        int B_hand_number=0;
        char[] B_hand=gameState[1][2].toCharArray();
        for(int i=0;i<gameState[1][2].length();i++)
        {
            if(B_hand[i]>='0'&& B_hand[i]<='8'){
                B_hand_number++;
                repeat=gameState[1][2].substring(i-1,i+1);





                if((gameState[0][1].contains(repeat))||gameState[0][3].contains(repeat)||gameState[0][2].contains(repeat)||gameState[0][4].contains(repeat)||gameState[1][1].contains(repeat)||gameState[1][0].contains(repeat))
                {

                    return false;
                }
            }
        }

        int A_hand_number=0;
        char[] A_hand=gameState[1][1].toCharArray();
        for(int i=0;i<gameState[1][1].length();i++)
        {
            if(A_hand[i]>='0'&& A_hand[i]<='8'){
                A_hand_number++;
                repeat= gameState[1][1].substring(i-1,i+1);
                if((gameState[0][1].contains(repeat))||gameState[0][3].contains(repeat)||gameState[0][2].contains(repeat)||gameState[0][4].contains(repeat)||gameState[1][0].contains(repeat)||gameState[1][2].contains(repeat)){

                    return false;
                }

            }
        }

        int A_Arboretum_number=0;
        char[] A_Arboretum=gameState[0][1].toCharArray();
        for(int i=1;i<gameState[0][1].length();i+=1){
            if(A_Arboretum[i]>='a'&&A_Arboretum[i]<='m')
            {
                A_Arboretum_number++;
                repeat= gameState[0][1].substring(i,i+2);

                if((gameState[1][1].contains(repeat))||gameState[0][3].contains(repeat)||gameState[0][2].contains(repeat)||gameState[0][4].contains(repeat)||gameState[1][0].contains(repeat)||gameState[1][2].contains(repeat)){

                    return false;
                }

            }
        }

        if(Objects.equals(gameState[0][0], "A"))
        {
            if(gameState[1][0].length()/2!=48) {
                if ((gameState[1][2].length() - 1) / 2 != 7) {
                    return false;
                }
            }
        }

        if(Objects.equals(gameState[0][0], "B"))
        {
            if(gameState[1][0].length()/2!=48) {
                if ((gameState[1][1].length() - 1) / 2 != 7) {
                    return false;
                }
            }
        }

        int B_Arboretum_number=0;
        char[] B_Arboretum=gameState[0][3].toCharArray();
        for(int i=1;i<gameState[0][3].length();i+=1){
            if(B_Arboretum[i]>='a'&&B_Arboretum[i]<='m')
            {
                B_Arboretum_number++;
                repeat= gameState[0][3].substring(i,i+2);

                if((gameState[1][1].contains(repeat))||gameState[0][1].contains(repeat)||gameState[0][2].contains(repeat)||gameState[0][4].contains(repeat)||gameState[1][0].contains(repeat)||gameState[1][2].contains(repeat)){

                    return false;
                }
            }
        }






        int A_discard_number=0;
        char[] A_discard =gameState[0][2].toCharArray();
        for(int i=1;i<gameState[0][2].length();i+=1){
            if(A_discard[i]>='a'&&A_discard[i]<='m')
            {
                A_discard_number++;
            }
        }

        int B_discard_number=0;
        char[] B_discard =gameState[0][4].toCharArray();
        for(int i=1;i<gameState[0][4].length();i+=1){
            if(B_discard[i]>='a'&&B_discard[i]<='m')
            {
                B_discard_number++;
            }
        }

        if((B_discard_number+A_discard_number)>(A_Arboretum_number+B_Arboretum_number)){
            return false;
        }




        judge_number=A_hand_number+B_hand_number+Deck_number+A_Arboretum_number+B_Arboretum_number+A_discard_number+B_discard_number;

        if(judge_number!=48) return false;





        if(A_Arboretum_number<B_Arboretum_number)
        {
            return false;
        }

        if(A_Arboretum_number<A_discard_number)
        {
            return false;

        }

        if(B_Arboretum_number<B_discard_number)
        {
            return false;

        }



        for(int i=1;i<A_Arboretum.length;i+=8)
        {


            if(gameState[0][1].length()>10) {



                String judge_string = gameState[0][1].substring(i+2, i + 8);




                char[] judge_char = judge_string.toCharArray();
                String judgeN = judge_string;
                String judgeS = judge_string;
                String judgeW = judge_string;
                String judgeE = judge_string;


                int judgeX = Integer.parseInt(judge_string.substring(1, 3));
                if (judge_char[0] == 'C') {
                    judgeN = (judge_string.toString().substring(0, 3).replace("C00", "N01") + judge_string.toString().substring(3, 6));
                    judgeS = (judge_string.toString().substring(0, 3).replace("C00", "S01") + judge_string.toString().substring(3, 6));


                }

                if(judge_char[3]=='C'){
                    judgeE = (judge_string.toString().substring(0, 3) + judge_string.toString().substring(3, 6).replace("C00", "E01"));
                    judgeW = (judge_string.toString().substring(0, 3) + judge_string.toString().substring(3, 6).replace("C00", "W01"));
                }

                if (judge_char[0] == 'N') {

                    judgeN = ("N" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeS = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }
                    if (judgeX != 1) {
                        judgeS = ("N" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }



                }

                if (judge_char[0] == 'S') {

                    judgeS = ("S" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeN = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }

                    if (judgeX != 1) {
                        judgeN = ("S" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }

                }


                int judgeY = Integer.parseInt(judge_string.substring(4, 6));
                if (judge_char[3] == 'C') {
                    judgeW = (judge_string.toString().substring(0, 3)+judge_string.toString().substring(3, 6).replace("C00", "W01") );
                    judgeE = (judge_string.toString().substring(0, 3)+judge_string.toString().substring(3, 6).replace("C00", "E01") );

                }
                if (judge_char[3] == 'E') {

                    judgeE = (judge_string.substring(0, 3) + "E" + String.format("%02d",judgeY+1));
                    if (judgeY == 1) {
                        judgeW = (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1) {
                        judgeW = (judge_string.substring(0, 3) + "E" + (String.format("%02d",judgeY-1)));
                    }

                }

                if (judge_char[3] == 'W') {

                    judgeW = (judge_string.substring(0, 3) + "W" + String.format("%02d",judgeY+1));

                    if (judgeY == 1)
                    {
                        judgeE =  (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1)
                    {
                        judgeE = (judge_string.substring(0, 3) + "W" + (String.format("%02d",judgeY-1)));
                    }

                }


                if (gameState[0][1].length() > 15) {



                    int resultN = (gameState[0][1].substring(1, i)+"N01C00").indexOf(judgeN);
                    int resultS = (gameState[0][1].substring(1, i)+"S01C00").indexOf(judgeS);
                    int resultW = (gameState[0][1].substring(1, i)+"C00W01").indexOf(judgeW);
                    int resultE = (gameState[0][1].substring(1, i)+"C00E01").indexOf(judgeE);






                    if(gameState[0][1].substring(0,i).contains(judge_string))
                    {
                        return false;
                    }

                    if ((resultN == -1) && (resultS == -1) && (resultW == -1) && (resultE == -1)) {
                        return false;
                    }


                }


            }




        }
        for(int i=1;i<B_Arboretum.length;i+=8)
        {


            if(gameState[0][3].length()>10) {



                String judge_string = gameState[0][3].substring(i+2, i + 8);




                char[] judge_char = judge_string.toCharArray();
                String judgeN = judge_string;
                String judgeS = judge_string;
                String judgeW = judge_string;
                String judgeE = judge_string;


                int judgeX = Integer.parseInt(judge_string.substring(1, 3));
                if (judge_char[0] == 'C') {
                    judgeN = (judge_string.toString().substring(0, 3).replace("C00", "N01") + judge_string.toString().substring(3, 6));
                    judgeS = (judge_string.toString().substring(0, 3).replace("C00", "S01") + judge_string.toString().substring(3, 6));


                }

                if(judge_char[3]=='C'){
                    judgeE = (judge_string.toString().substring(0, 3) + judge_string.toString().substring(3, 6).replace("C00", "E01"));
                    judgeW = (judge_string.toString().substring(0, 3) + judge_string.toString().substring(3, 6).replace("C00", "W01"));
                }

                if (judge_char[0] == 'N') {

                    judgeN = ("N" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeS = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }
                    if (judgeX != 1) {
                        judgeS = ("N" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }



                }

                if (judge_char[0] == 'S') {

                    judgeS = ("S" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeN = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }

                    if (judgeX != 1) {
                        judgeN = ("S" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }

                }


                int judgeY = Integer.parseInt(judge_string.substring(4, 6));
                if (judge_char[3] == 'C') {
                    judgeW = (judge_string.toString().substring(0, 3)+judge_string.toString().substring(3, 6).replace("C00", "W01") );
                    judgeE = (judge_string.toString().substring(0, 3)+judge_string.toString().substring(3, 6).replace("C00", "E01") );

                }
                if (judge_char[3] == 'E') {

                    judgeE = (judge_string.substring(0, 3) + "E" + String.format("%02d",judgeY+1));
                    if (judgeY == 1) {
                        judgeW = (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1) {
                        judgeW = (judge_string.substring(0, 3) + "E" + (String.format("%02d",judgeY-1)));
                    }

                }

                if (judge_char[3] == 'W') {

                    judgeW = (judge_string.substring(0, 3) + "W" + String.format("%02d",judgeY+1));

                    if (judgeY == 1)
                    {
                        judgeE =  (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1)
                    {
                        judgeE = (judge_string.substring(0, 3) + "W" + (String.format("%02d",judgeY-1)));
                    }

                }


                if (gameState[0][3].length() > 15) {



                    int resultN = (gameState[0][3].substring(1, i)+"N01C00").indexOf(judgeN);
                    int resultS = (gameState[0][3].substring(1, i)+"S01C00").indexOf(judgeS);
                    int resultW = (gameState[0][3].substring(1, i)+"C00W01").indexOf(judgeW);
                    int resultE = (gameState[0][3].substring(1, i)+"C00E01").indexOf(judgeE);





                    if ((resultN == -1) && (resultS == -1) && (resultW == -1) && (resultE == -1)) {
                        return false;
                    }
                }
            }
        }


        return true;
        // FIXME TASK 8
    }

    /**
     * Determine whether the given player has the right to score the given species. Note: This does not check whether
     * a viable path exists. You may gain the right to score a species that you do not have a viable scoring path for.
     * See "Gaining the Right to Score" in `README.md`.
     * You may assume that the given gameState array is valid.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array.
     * @param player the player attempting to score.
     * @param species the species that is being scored.
     * @return true if the given player has the right to score this species, false if they do not have the right.
     */
    public static boolean canScore(String[][] gameState, char player, char species) {

        String A_hand=gameState[1][1];
        String B_hand=gameState[1][2];
        char[] A_Hand=A_hand.toCharArray();
        char[] B_Hand=B_hand.toCharArray();
        int Score_species_A=0;
        int Score_species_B=0;
        int now=0;
        int now1=0;
        boolean A_special=false;
        boolean B_special=false;

        for(int i = 0; i< B_Hand.length;i++) {
            if (B_Hand[i] == species) {
                if (B_Hand[i + 1] == '1') {
                    B_special = true;
                }
            }
        }

        for(int i = 0; i< A_Hand.length;i++)
        {
            if(A_Hand[i]==species)
            {
                if(A_Hand[i+1]=='1')
                {
                    A_special=true;
                }
                if(B_special)
                {
                    if(A_Hand[i+1]=='8')
                    {
                        continue;
                    }
                }
                now=Integer.parseInt(String.valueOf(A_Hand[i+1]));
                Score_species_A+=now;


            }
        }
        for(int i = 0; i< B_Hand.length;i++)
        {
            if(B_Hand[i]==species)
            {
                if(B_Hand[i+1]=='1')
                {
                    B_special=true;
                }

                if(A_special) {
                    if(B_Hand[i+1]=='8')
                    {
                        continue;
                    }
                }

                    now1 = Integer.parseInt(String.valueOf(B_Hand[i + 1]));
                    Score_species_B += now1;



            }

        }




            if(Score_species_A>=Score_species_B)
            {
                if(player=='A')
                {

                    return true;
                }
            }

            if(Score_species_B>=Score_species_A)
            {
                if(player=='B')
                {
                    return true;
                }
            }
        return false;
        // FIXME TASK 9
    }

    /**
     * Find all the valid placements for the given card for the player whose turn it is.
     * A placement is valid if it satisfies the following conditions:
     * 1. The card is horizontally or vertically adjacent to at least one other placed card.
     * 2. The card does not overlap with an already-placed card.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param card the card to play
     * @return a set of valid card placement strings for the current player.
     */
    public static Set<String> getAllValidPlacements(String[][] gameState, String card) {

        Set<String> accessiable = new HashSet<String>();

        //Select arboretum based on which player's round
        String NowArb = new String();
        if(gameState[0][0].equals("A"))
            NowArb = gameState[0][1];
        else
            NowArb = gameState[0][3];

        accessiable.add(card+"C00C00");

        // Get Valid Position of Card
        for(int i=1;i<NowArb.length();i+=8)
        {

            String judge_string = NowArb.substring(i+2, i + 8);

                char[] judge_char = judge_string.toCharArray();
                String judgeN = judge_string;
                String judgeS = judge_string;
                String judgeW = judge_string;
                String judgeE = judge_string;


                int judgeX = Integer.parseInt(judge_string.substring(1, 3));
                if (judge_char[0] == 'C') {
                    judgeN = (judge_string.substring(0, 3).replace("C00", "N01") + judge_string.substring(3, 6));
                    judgeS = (judge_string.substring(0, 3).replace("C00", "S01") + judge_string.substring(3, 6));


                }

                if(judge_char[3]=='C'){
                    judgeE = (judge_string.substring(0, 3) + judge_string.substring(3, 6).replace("C00", "E01"));
                    judgeW = (judge_string.substring(0, 3) + judge_string.substring(3, 6).replace("C00", "W01"));
                }

                if (judge_char[0] == 'N') {

                    judgeN = ("N" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeS = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }
                    if (judgeX != 1) {
                        judgeS = ("N" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }

                }

                if (judge_char[0] == 'S') {

                    judgeS = ("S" + (String.format("%02d",judgeX+1)) + judge_string.substring(3, 6));
                    if (judgeX == 1) {
                        judgeN = (judge_string.substring(0, 3).replace(judge_string.substring(0, 3), "C00") + judge_string.substring(3, 6));
                    }

                    if (judgeX != 1) {
                        judgeN = ("S" + (String.format("%02d",judgeX-1)) + judge_string.substring(3, 6));
                    }

                }


                int judgeY = Integer.parseInt(judge_string.substring(4, 6));
                if (judge_char[3] == 'C') {
                    judgeW = (judge_string.substring(0, 3)+judge_string.substring(3, 6).replace("C00", "W01") );
                    judgeE = (judge_string.substring(0, 3)+judge_string.substring(3, 6).replace("C00", "E01") );

                }
                if (judge_char[3] == 'E') {

                    judgeE = (judge_string.substring(0, 3) + "E" + String.format("%02d",judgeY+1));
                    if (judgeY == 1) {
                        judgeW = (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1) {
                        judgeW = (judge_string.substring(0, 3) + "E" + (String.format("%02d",judgeY-1)));
                    }

                }

                if (judge_char[3] == 'W') {

                    judgeW = (judge_string.substring(0, 3) + "W" + String.format("%02d",judgeY+1));

                    if (judgeY == 1)
                    {
                        judgeE =  (judge_string.substring(0, 3)+judge_string.substring(3,6).replace(judge_string.substring(3, 6), "C00"));
                    }
                    if (judgeY != 1)
                    {
                        judgeE = (judge_string.substring(0, 3) + "W" + (String.format("%02d",judgeY-1)));
                    }

                }

                accessiable.add(card+judgeE);
                accessiable.add(card+judgeW);
                accessiable.add(card+judgeS);
                accessiable.add(card+judgeN);

        }
        if (NowArb.length()>=7)
        {
            accessiable.remove(card+"C00C00");
        }

        for(int i=1;i<NowArb.length();i+=8) {


            String judge_string = NowArb.substring(i + 2, i + 8);

            accessiable.remove(card+judge_string);
        }
        return accessiable;

        //FIXME TASK 10
    }

    /**
     * Find all viable scoring paths for the given player and the given species if this player has the right to
     * score this species.
     *
     * A "scoring path" is a non-zero number of card substrings in order from starting card to ending card.
     * For example: "a1a3b6c7a8" is a path of length 5 starting at "a1" and ending at "a8".
     *
     * A path is viable if the following conditions are met:
     * 1. The player has the right to score the given species.
     * 2. Each card along the path is orthogonally adjacent to the previous card.
     * 3. Each card has value greater than the previous card.
     * 4. The path begins with the specified species.
     * 5. The path ends with the specified species.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player the given player
     * @param species the species the path must start and end with.
     * @return a set of all viable scoring paths if this player has the right to score this species, or null if this
     * player does not have the right to score this species. If the player has no viable scoring paths (but has the
     * right to score this species), return the empty Set.
     */
    public static Set<String> getAllViablePaths(String[][] gameState, char player, char species) {
        //Initialise output set of viable paths
        Set<String> output_viable_paths = new HashSet<String>();
        //Initialise index for player arboretum and hands
        int arboretum_index;
        int hand_index;
        int other_player_hand_index;
        //Index calculated depending on player to score
        if (player == 'A') {
            arboretum_index = 1;
            hand_index = 1;
            other_player_hand_index = 2;
        } else {
            arboretum_index = 3;
            hand_index = 2;
            other_player_hand_index = 1;
        }
        /////////////////////////////////
        //Check player has right to score and initialise all required variables
        String player_hand_string;
        int hand_value;
        int player_hand_value = 0;
        int other_player_hand_value = 0;
        Boolean other_player_has_one = false;
        Boolean other_player_has_eight = false;

        //Calculate hand value for other player
        player_hand_string = gameState[1][other_player_hand_index];
        for (int i = 1; i < player_hand_string.length(); i+=2) {
            if (player_hand_string.charAt(i) == species) {
                hand_value = Character.getNumericValue(player_hand_string.charAt(i+1));
                //Check if the other player has a one, which will be used to neutralise the player's 8
                if (hand_value == 1) {
                    other_player_has_one = true;
                //Check if other player has 8, to be utilised for the calculation of other player
                } else if (hand_value == 8) {
                    other_player_has_eight = true;
                }
                other_player_hand_value += hand_value;
            }
        }
        //Calculate hand value for player
        player_hand_string = gameState[1][hand_index];
        for (int i = 1; i < player_hand_string.length(); i+=2) {
            if (player_hand_string.charAt(i) == species) {
                hand_value = Character.getNumericValue(player_hand_string.charAt(i+1));
                //Check if the player has an 8 (if other player has one then the 8 is no longer scored)
                if (hand_value == 8) {
                    if (other_player_has_one) {
                        continue;
                    }
                //Check if player has 1 and other player has 8 which will deduct 9 points from the other player
                } else if (hand_value == 1 && other_player_has_eight) {
                    other_player_hand_value = Math.max(0, other_player_hand_value - 8);
                }
                player_hand_value += hand_value;
            }
        }
        //Assess hand values
        if (player_hand_value < other_player_hand_value) {
            return null;
        }
        //////////////////////////////////////////////////////////////////////
        //Output all card positions in the arboretum (current_available_species) and starting species (possible_species)
        List<String> possible_species = new ArrayList<>();
        List<String> current_available_species = new ArrayList<>();
        for (int i = 1; i < gameState[0][arboretum_index].length(); i+=8) {
            if (gameState[0][arboretum_index].charAt(i) == species) {
                possible_species.add(gameState[0][arboretum_index].substring(i,i+8));
                }
            current_available_species.add(gameState[0][arboretum_index].substring(i,i+8));
            }
        //Output all viable branches of card positions (noting that the getAdjacentPosition function checks to make sure that the viable branches must have a greater value than the previous card)
        HashMap<String,List<String>> multiple_paths = new HashMap<String,List<String>>();
        getAllViableAdjacentCards(possible_species, current_available_species, multiple_paths);
        
        ////////////////////////////////////////////////
        //Output all possible paths that begins and ends with the specified species
        Set<String> output_paths = new HashSet<>();
        getAllViableBranches(possible_species, multiple_paths, output_paths);
        //Iterate through each output path and add only the viable paths into the output_viable_paths
        Iterator<String> output_iterator = output_paths.iterator();
        while(output_iterator.hasNext()) {
            String output_path = output_iterator.next();
            String output_viable_path = "";
            //Remove direction strings from output
            for (int i = 0; i < output_path.length(); i+=8) {
                output_viable_path += output_path.substring(i,i+2);
            }
            //Only add paths end in the base specie and is not just the base specie
            if ((output_viable_path.charAt(output_viable_path.length()-2) == species && (output_viable_path.length() > 2))) {
                output_viable_paths.add(output_viable_path);
            }
            //Consider viable sub-branches
            for (int i = 0; i < output_viable_path.length(); i+=2) {
                if ((i != 0) && output_viable_path.charAt(i) == species) {
                    output_viable_paths.add(output_viable_path.substring(0, i+2));
                }
            }
        }
        return output_viable_paths;
        // FIXME TASK 12
    }
    
    public static void getAllViableBranches(List<String> possible_branches, Map<String,List<String>> positions, Set<String> path_output) {
        //Stop condition when calculating all possible viable branches (when there are no more branches to continue appending)
        if (possible_branches.size() == 0) {
            return;
        } else {
            //Initiate a list for the next possible branches to build
            List<String> next_possible_branches= new ArrayList<>();
            //Iterate all current branches and add all possible adjacent cards to the branch
            for (String specie_position:possible_branches) {
                String specie_to_search = specie_position.substring(specie_position.length() - 8);
                if (positions.containsKey(specie_to_search)) {
                    for (String next_specie_position:positions.get(specie_to_search)) {
                        next_possible_branches.add(specie_position+next_specie_position);
                    }
                } else {
                    //If no more additional cards can be appended, that is the max branch possible (append to output)
                    path_output.add(specie_position);
                }
            }
            //Recursively find all possible max length branches
            getAllViableBranches(next_possible_branches, positions, path_output);
        }
    }

    public static void getAllViableAdjacentCards(List<String> possible_species, List<String> current_available_species, Map<String,List<String>> positions) {
        //Stop condition when no more possible species are found for the next path branch
        if (possible_species.size() == 0) {
            return;
        } else {
            //Initiate a list for the next path branches
            List<String> next_possible_species = new ArrayList<>();
            //Iterate through the current path branches and find adjacent cards for each
            for (String specie:possible_species) {
                List<String> species_found = getAdjacentPositions(specie, current_available_species);
                if (species_found.size() != 0) {
                    //Append these new adjacent cards for the next iterative phase
                    next_possible_species.addAll(species_found);
                    //Add to the final positions map
                    positions.put(specie, species_found);
                }
            }
            //Recursively find all other possible adjacent branches
            getAllViableAdjacentCards(next_possible_species, current_available_species, positions);
        }
    }


    public static List<String> getAdjacentPositions(String CardStringPosition, List<String> current_available_species) {
        List<String> output_adj_positions = new ArrayList<>();
        //Method created to get all adjacent position of a card position (eg. )
        String CardPosition = CardStringPosition.substring(2);
        Set<String>  adjacent_positions = new HashSet<>();
        if (CardPosition.charAt(0) == 'S') {
            if (CardPosition.substring(1,3).equals("01")) {
                adjacent_positions.add("C00" + CardPosition.substring(3));
            } else {
                if (Integer.parseInt(CardPosition.substring(1,3)) < 9) {
                    adjacent_positions.add("S0" + (Integer.parseInt(CardPosition.substring(1,3)) - 1) + CardPosition.substring(3));
                } else {
                    adjacent_positions.add("S" + (Integer.parseInt(CardPosition.substring(1,3)) - 1) + CardPosition.substring(3));
                }
            }
        } else {
            if (Integer.parseInt(CardPosition.substring(1,3)) < 9) {
                adjacent_positions.add("N0" + (Integer.parseInt(CardPosition.substring(1,3)) + 1) + CardPosition.substring(3));
            } else {
                adjacent_positions.add("N" + (Integer.parseInt(CardPosition.substring(1,3)) + 1) + CardPosition.substring(3));
            }
        }
        //Add position below
        if (CardPosition.charAt(0) == 'N') {
            if (CardPosition.substring(1,3).equals("01")) {
                adjacent_positions.add("C00" + CardPosition.substring(3));
            } else {
                if (Integer.parseInt(CardPosition.substring(1,3)) < 9) {
                    adjacent_positions.add("N0" + (Integer.parseInt(CardPosition.substring(1,3)) - 1) + CardPosition.substring(3));
                } else {
                    adjacent_positions.add("N" + (Integer.parseInt(CardPosition.substring(1,3)) - 1) + CardPosition.substring(3));
                }

            }
        } else {
            if (Integer.parseInt(CardPosition.substring(1,3)) < 9) {
                adjacent_positions.add("S0" + (Integer.parseInt(CardPosition.substring(1,3)) + 1) + CardPosition.substring(3));
            } else {
                adjacent_positions.add("S" + (Integer.parseInt(CardPosition.substring(1,3)) + 1) + CardPosition.substring(3));
            }
        }
        //Add position to the right
        if (CardPosition.charAt(3) == 'W') {
            if (CardPosition.substring(4,6).equals("01")) {
                adjacent_positions.add(CardPosition.substring(0,3) + "C00");
            } else {
                if (Integer.parseInt(CardPosition.substring(4,6)) < 9) {
                    adjacent_positions.add(CardPosition.substring(0,3) + "W0" + (Integer.parseInt(CardPosition.substring(4,6)) - 1));
                } else {
                    adjacent_positions.add(CardPosition.substring(0,3) + "W" + (Integer.parseInt(CardPosition.substring(4,6)) - 1));
                }
            }
        } else {
            if (Integer.parseInt(CardPosition.substring(4,6)) < 9) {
                adjacent_positions.add(CardPosition.substring(0,3) + "E0" + (Integer.parseInt(CardPosition.substring(4,6)) + 1));
            } else {
                adjacent_positions.add(CardPosition.substring(0,3) + "E" + (Integer.parseInt(CardPosition.substring(4,6)) + 1));
            }

        }
        //Add position to the left
        if (CardPosition.charAt(3) == 'E') {
            if (CardPosition.substring(4,6).equals("01")) {
                adjacent_positions.add(CardPosition.substring(0,3) + "C00");
            } else {
                if (Integer.parseInt(CardPosition.substring(4,6)) < 9) {
                    adjacent_positions.add(CardPosition.substring(0,3) + "E0" + (Integer.parseInt(CardPosition.substring(4,6)) - 1));
                } else {
                    adjacent_positions.add(CardPosition.substring(0,3) + "E" + (Integer.parseInt(CardPosition.substring(4,6)) - 1));
                }

            }
        } else {
            if (Integer.parseInt(CardPosition.substring(4,6)) < 9) {
                adjacent_positions.add(CardPosition.substring(0,3) + "W0" + (Integer.parseInt(CardPosition.substring(4,6)) + 1));
            } else {
                adjacent_positions.add(CardPosition.substring(0,3) + "W" + (Integer.parseInt(CardPosition.substring(4,6)) + 1));
            }
        }
        //Compute appropriate card positions that are adjacent and in ascending order of value
        for (String value:current_available_species) {
            //Check that the card position is adjacent and that the card value is greater than previous
            if (adjacent_positions.contains(value.substring(2)) && (Character.valueOf(value.charAt(1)) > Character.valueOf(CardStringPosition.charAt(1)))) {
                output_adj_positions.add(value);
            }
        }

        return output_adj_positions;
    }

    /**
     * Find the highest score of the viable paths for the given player and species.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player the given player
     * @param species the species to score
     * @return the score of the highest scoring viable path for this player and species.
     * If this player does not have the right to score this species, return -1.
     * If this player has the right to score this species but there is no viable scoring path, return 0.
     */
    public static int getHighestViablePathScore(String[][] gameState, char player, char species) {
        //Initiate output score value and the viable paths to score
        int output_value = 0;
        Set<String> viable_output_paths = getAllViablePaths(gameState, player, species);
        //If player does not have right to score (null), return min value (-1) and if no viable paths (size=0), return 0
        if (viable_output_paths == null) {
            return -1;
        } else if (viable_output_paths.size() == 0) {
            return 0;
        }
        //Iterate through each viable output path and calculate the total score
        for (String path : viable_output_paths) {
            //Initialise all species check and score value calculated for each path
            boolean all_given_species = true;
            int value_calculated = 0;
            for (int i = 0; i < path.length(); i+=2) {
                value_calculated ++;
                if (path.charAt(i) != species) {
                    all_given_species = false;
                }
            }
            //If path is at least 4 cards long and all cards are same species gain an additional point for each
            if (all_given_species && value_calculated >= 4) {
                value_calculated += value_calculated;
            }
            //If the path starts with a value of 1, gain an extra point
            if (path.charAt(1) == '1') {
                value_calculated ++;
            }
            //If the path ends with a value of 8, gain an extra two points
            if (path.charAt(path.length()-1) == '8') {
                value_calculated += 2;
            }
            //If the score is greater than the previous score, replace it as the output value
            if (value_calculated > output_value) {
                output_value = value_calculated;
            }
        }
        return output_value;
        // FIXME TASK 13
    }

    /**
     * AI Part 1:
     * Decide whether to draw a card from the deck or a discard pile.
     * Note: This method only returns the choice, it does not update the game state.
     * If you wish to draw a card from the deck, return "D".
     * If you wish to draw a card from a discard pile, return the cardstring of the (top) card you wish to draw.
     * You may count the number of cards in a players' hand to determine whether this is their first or final draw
     * for the turn.
     *
     * Note: You may only draw the top card of a players discard pile. ie: The last card that was added to that pile.
     * Note: There must be cards in the deck (or alternatively discard) to draw from the deck (or discard) respectively.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return "D" if you wish to draw from the deck, or the cardstring of the card you wish to draw from a discard
     * pile.
     */
    public static String chooseDrawLocation(String[][] gameState) {

        String A_pile_accessible = null;

        String B_pile_accessible=null;


        if(gameState[1][0]==null||gameState[1][0].length()==0) {


            if(Objects.equals(gameState[0][0],"A")){
                if (gameState[0][2] != null || gameState[0][2].length() >= 2) {
                    A_pile_accessible = gameState[0][2].substring(gameState[0][2].length() - 2, gameState[0][2].length());


                }
                return A_pile_accessible;

            }


            if(Objects.equals(gameState[0][0],"B")) {
                if (gameState[0][4] != null || gameState[0][4].length() >= 2) {
                    B_pile_accessible = gameState[0][4].substring(gameState[0][4].length() - 2, gameState[0][4].length());


                }
                return B_pile_accessible;


            }
        }




        return "D";



        // FIXME TASK 14
    }


    /**
     * AI Part 2:
     * Generate a moveString array for the player whose turn it is.
     *
     * A moveString array consists of two parts;
     * moveString[0] is the valid card _placement_ string for the card you wish to place.
     * moveString[1] is the cardstring of the card you wish to discard.
     *
     * For example: If I want to play card "a1" in location "N01E02" and discard card "b4" then my moveString[] would
     * be as follows:
     * moveString[0] = "a1N01E02"
     * moveString[1] = "b4"
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return a valid move for this player.
     */
    public static String[] generateMove(String[][] gameState) {
        String[] moving = new String[2];
        String[] Placement = new String[4];
        // valid card placement in arboretum
        // card that wish to discard
        Random rand = new Random();
        // Check whose turn
        int turn;
        if (gameState[0][0].equals("A"))
            turn = 1;
        else turn = 2;
        System.out.println(gameState[0][0]);
        int randomplace = rand.nextInt(9);
        int randomdiscard = randomplace;
        //Make sure not the same card
        while (randomplace == randomdiscard)
            randomdiscard = rand.nextInt(9);
        String placecard = gameState[1][turn].substring(2 * randomplace + 1 ,2 * randomplace + 3);
        Set<String> validplace = getAllValidPlacements(gameState,placecard);
        Iterator interator = validplace.iterator();
        moving[0] = (String) interator.next();
        moving[1] = gameState[1][turn].substring(2 * randomdiscard + 1, 2 * randomdiscard + 3);

        return moving;
        // FIXME TASK 15
    }
/**
     * Find the final winner of the game.
     *
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return the string to show which player is the winner and his score.
     * by Xiaodan Du
     */
    public static String getFinalWinner(String[][] gameState) {

        String species = "abcdjm";
        int totalA = 0;
        int totalB = 0;
        // Loop the species to get player's final score for all species
        for(int i = 0; i < 8; i++){
            if (getHighestViablePathScore(gameState, 'A', species.charAt(i)) != -1){
                totalA = totalA + getHighestViablePathScore(gameState,'A',species.charAt(i));
            }
            if (getHighestViablePathScore(gameState, 'B', species.charAt(i)) != -1){
                totalB = totalB + getHighestViablePathScore(gameState,'B',species.charAt(i));
            }
        }
        if (totalA > totalB)
            return "A is winner and get " + totalA + " score";

        else if (totalA == totalB)
               return "A and B are both winner and get " + totalA + " score";
             else
                 return "B is winner and get " + totalB + " score";
    }


}