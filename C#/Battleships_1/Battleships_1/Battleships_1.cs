using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////Legend for the game////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
//Field status:
//0 - untouched free field Blue/DarkBlue
//1 - used condom free field 
//2 - untouched field with a ship
//3 - damaged ship field
//4 - sunked shid field
//5 - untouched free field near sunked ship (designed nicely)
////////////////////////////////////
//Ship variants:
//ShipSmall - 1 cell
//ShipMedium - 2 cell
//ShipBig - 3 cell
//ShipHuge - 4 cell
//Need to work here
////////////////////////////////////
//Type of coordinats user inputs through ReadCell() where the number after the end is the int.Purpose
//ShipStartX/Y -1
//ShipEndX/Y -2
//ShootEnemyX/Y -3
////////////////////////////////////
//Phases of the game:
//1 - placing ships
//2 - enemy randoming ships
//3 - fighting
//4 - end of the game

namespace Battleships_1
{

    //This container class "coord" contains all temporary data according ships
    public static class coord {
        public static int ShipStartY {get; set;}
                                                    //These two contain the coordinates where to start to draw a ship
        public static int ShipStartX {get; set;}

        public static int ShipEndY { get; set; }
                                                    //These two contain the coordinates where to finish to draw a ship
        public static int ShipEndX { get; set; }

        public static int ShootEnemyX {get; set;}
                                                    //These two contain the coordinates where to shoot on enemy field
        public static int ShootEnemyY {get; set;}

        public static int EnemyShootX {get; set;}
                                                    //These two contain the coordinates where enemy shoots
        public static int EnemyShootY {get; set;}

        public static int ShipSize { get; set; } //Contains the size of the ship 1-4
        }
    //a container with information about the amount of ship placed by a player or enemy
    public static class shipsPlaced
    {
        public static int Small { get; set; }

        public static int Medium { get; set; }

        public static int Big { get; set; }

        public static int Huge{ get; set; }
    }

    public static class game //Container with the most important current game information
    {
        public static int Phase { get; set; } //Phase of the game
        public static int[,] MyField = new int[12, 12];//2-rank array with player's field data
        public static int[,] EnemyField = new int[12, 12];//2-rank array with opponent's field data
        public static int PlayerRandoms{ get; set; } //Holds information whether player randoms his ships or not
        public static int DamageDone { get; set; } //Hold information whether enemy has hit your ship (atleast 1 cell is red - damaged) or not
        public static int x = -1;
        public static int TurnBack = 0;
        public static int level = 0;
    }

    class Battleships_1
    {
        public static void DrawPlayerField() {
            //Writing a line of letters above of the field nr.1
            Console.SetCursorPosition(0, 5);
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("     A B C D E F G H I J");
            for (int i = 0; i < 10; i++)  //A cycle for drawing the first field
            {
                Console.SetCursorPosition(2, i + 6); //Preparing for drawning the first field
                for (int j = -1; j < 11; j++)
                {
                    if (j == 10)
                    {
                        Console.ForegroundColor = ConsoleColor.DarkMagenta;
                        Console.Write("{0,-2}", i + 1);
                        Console.ResetColor();
                    }
                    else if (j == -1)
                    {
                        Console.ForegroundColor = ConsoleColor.DarkMagenta;
                        Console.Write("{0,2}", i + 1);
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 0)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }
                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 1)
                    {
                        Console.BackgroundColor = ConsoleColor.Black;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 2)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Yellow;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkYellow;
                        }

                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Yellow;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkYellow;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 3)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Red;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkRed;
                        }

                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Red;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkRed;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 4)
                    {
                        Console.BackgroundColor = ConsoleColor.Magenta;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.MyField[j + 1, i + 1] == 5)
                    {
                        Console.BackgroundColor = ConsoleColor.DarkGray;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                }
                Console.WriteLine();
            }
            //Writing a line of letters below the field nr.1
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("     A B C D E F G H I J");
            Console.WriteLine();
            Console.ResetColor();
        }

        public static void DrawEnemyField() {
            //Writing a line of letters above the field nr.2
            Console.SetCursorPosition(30, 5);
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("   A B C D E F G H I J");

            for (int i = 0; i < 10; i++)  //A cycle for drawing the second field
            {
                Console.SetCursorPosition(30, i + 6); //Preparing for drawning the first field
                for (int j = -1; j < 11; j++)
                {
                    if (j == 10)
                    {
                        Console.ForegroundColor = ConsoleColor.DarkMagenta;
                        Console.Write("{0,-2}", i + 1);
                        Console.ResetColor();
                    }
                    else if (j == -1)
                    {
                        Console.ForegroundColor = ConsoleColor.DarkMagenta;
                        Console.Write("{0,2}", i + 1);
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 0)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }

                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 1)
                    {
                        Console.BackgroundColor = ConsoleColor.Black;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 2)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }

                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Blue;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkBlue;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 3)
                    {
                        if (i % 2 == 0)
                        {
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.Red;
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.DarkRed;
                        }

                        if (i % 2 == 1)
                        {
                            if (j % 2 == 1)
                                Console.BackgroundColor = ConsoleColor.Red;
                            if (j % 2 == 0)
                                Console.BackgroundColor = ConsoleColor.DarkRed;
                        }
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 4)
                    {
                        Console.BackgroundColor = ConsoleColor.Magenta;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                    else if (game.EnemyField[j + 1, i + 1] == 5)
                    {
                        Console.BackgroundColor = ConsoleColor.DarkGray;
                        Console.Write("  ");
                        Console.ResetColor();
                    }
                }
                Console.WriteLine();
            }
            //Writing a line of letters below the field nr.2
            Console.ResetColor();
            Console.SetCursorPosition(30, 16);
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("  A B C D E F G H I J");
            Console.ResetColor();
        
       }


        //A function for eliminating a ship (fields marked as 3 turn to mark 4) and marking near cells as 5th grade
        public static void ShipElimination(int who) {
            //Pretty similar system to checking for near ship while placing them.
            //Programm picks 1 point to the top and 1 point to the left from the first point of the ship as starting point for checking
            //And 1 pont to the left and 1 point to the bottom from the last point of the ship as end-point for checking
            int CheckPointY = 0;
            int CheckPointX = 0;
            int EndCheckPointY = 0;
            int EndCheckPointX = 0;


            CheckPointY = coord.ShipStartY - 1;
            CheckPointX = coord.ShipStartX - 1;
            EndCheckPointY = coord.ShipEndY + 1;
            EndCheckPointX = coord.ShipEndX + 1;



            for (int k = 0; CheckPointY + k <= EndCheckPointY; k++)
            {
                for (int i = 0; CheckPointX + i <= EndCheckPointX; i++)
                {

                    if (who == 1)
                    {
                        if (game.MyField[CheckPointY + k, CheckPointX + i] == 3)
                        {
                            game.MyField[CheckPointY + k, CheckPointX + i] = 4;
                        }
                        else
                        {
                            game.MyField[CheckPointY + k, CheckPointX + i] = 5;
                        }
                    }

                    if (who == 2)
                    {

                        if (game.EnemyField[CheckPointY + k, CheckPointX + i] == 3)
                        {
                            game.EnemyField[CheckPointY + k, CheckPointX + i] = 4;
                        }
                        else
                        {
                            game.EnemyField[CheckPointY + k, CheckPointX + i] = 5;
                        }
                    }
                }
            }
        }

        //Function for getting random direction
        public static int RandomDirection(){
            Random rnd = new Random();
            return rnd.Next(1, 5);            //1 - Upwards, 2 - Right, 3 - DownWards, 4 - Left
        }

        //Function for getting a random cell from the field
        public static void RandomCell(int Purpose)
        {
            Random rnd = new Random();
            switch (Purpose) //Purpose: picking cells goes for either 1 -placing ships or 2 - AI enemy shooting at player
            {
                case 1:
                    coord.ShipStartX = rnd.Next(1, 11);
                    coord.ShipStartY = rnd.Next(1, 11);
                    break;
                case 3:
                    coord.EnemyShootX = rnd.Next(1, 11);
                    coord.EnemyShootY = rnd.Next(1, 11);
                    break;
            }
        }


        public static void AddShipToField(int who)//Places ships on the field using coord.ShipStartX/Y and coord.ShipEndX/Y
        {
            int k = 0;
            if (coord.ShipStartY == coord.ShipEndY) {
                while (coord.ShipStartX+k <= coord.ShipEndX) {

                    switch(who){

                        case 1:
                    game.MyField[coord.ShipStartY, coord.ShipStartX+k] = 2;
                    break;
                        case 2:
                    game.EnemyField[coord.ShipStartY, coord.ShipStartX + k] = 2;
                    break;
                }
                    k++;
                }
            }
            else if(coord.ShipStartX == coord.ShipEndX) {
                while (coord.ShipStartY+k <= coord.ShipEndY)
                {

                    switch (who)
                    {

                        case 1:
                            game.MyField[coord.ShipStartY + k, coord.ShipStartX] = 2;
                            break;
                        case 2:
                            game.EnemyField[coord.ShipStartY + k, coord.ShipStartX] = 2;
                            break;
                    }
                    k++;
                }
            }
            switch (coord.ShipSize) { 
            
                case 1:
                    shipsPlaced.Small++;
                    break;
                case 2:
                    shipsPlaced.Medium++;
                    break;
                case 3:
                    shipsPlaced.Big++;
                    break;
                case 4:
                    shipsPlaced.Huge++;
                    break;
            }
        }
        //Checks player's input coordinates of ship to prove that it is 1) linear and 2) fits the desired size
        public static int CheckShipCoordinates(){

            if (coord.ShipSize == 1) {
                coord.ShipEndX = coord.ShipStartX;
                coord.ShipEndY = coord.ShipStartY;
                return 1; }

            if (coord.ShipStartY == coord.ShipEndY && Math.Abs(coord.ShipStartX-coord.ShipEndX)+1 == coord.ShipSize)
            {
                if (coord.ShipStartX > coord.ShipEndX)
                {
                    int temp = coord.ShipStartX;
                    coord.ShipStartX = coord.ShipEndX;
                    coord.ShipEndX = temp;
                }
                return 1;
            }

            if (coord.ShipStartX == coord.ShipEndX && Math.Abs(coord.ShipStartY - coord.ShipEndY)+1 == coord.ShipSize)
            {
                if (coord.ShipStartY > coord.ShipEndY)
                {
                    int temp = coord.ShipStartY;
                    coord.ShipStartY = coord.ShipEndY;
                    coord.ShipEndY = temp;
                }
                return 1;
            }
            return 0;
        }
        //A function for checking whether the field programm is going to place suits the field (not too close to another ship)
        public static int CheckShipSurrounding(int who){

            int CheckPointY = 0;
            int CheckPointX = 0;
            int EndCheckPointY = 0;
            int EndCheckPointX = 0;

            
                CheckPointY = coord.ShipStartY - 1;
                CheckPointX = coord.ShipStartX - 1;
                EndCheckPointY = coord.ShipEndY + 1;
                EndCheckPointX = coord.ShipEndX + 1;

            for (int k = 0; CheckPointY + k <= EndCheckPointY; k++ )
            {
                for (int i = 0; CheckPointX + i <= EndCheckPointX; i++)
                {
                    if (who == 1)
                    {
                        if (game.MyField[CheckPointY + k, CheckPointX + i] == 2) //&& game.Phase < 3
                        {
                            return 0;
                        }
                        if (game.MyField[CheckPointY + k, CheckPointX + i] == 3)
                        {
                            if (CheckPointY + k < coord.ShipStartY)
                            {
                                coord.ShipStartY = CheckPointY + k;
                                return 3;
                            }
                            else if (CheckPointY + k > coord.ShipEndY)
                            {
                                coord.ShipEndY = CheckPointY + k;
                                return 3;
                            }
                            else if (CheckPointX + i < coord.ShipStartX)
                            {
                                coord.ShipStartX = CheckPointX + i;
                                return 3;
                            }
                            else if (CheckPointX + i > coord.ShipEndX)
                            {
                                coord.ShipEndX = CheckPointX + i;
                                return 3;
                            }
                        }
                    }
                    if (who == 2)
                    {
                        if (game.EnemyField[CheckPointY + k, CheckPointX + i] == 2)
                        {
                            return 0;
                        }
                        if (game.EnemyField[CheckPointY + k, CheckPointX + i] == 3)
                        {
                            if (CheckPointY + k < coord.ShipStartY)
                            {
                                coord.ShipStartY = CheckPointY + k;
                                return 3;
                            }
                            else if(CheckPointY + k > coord.ShipEndY)
                            {
                                coord.ShipEndY = CheckPointY + k;
                                return 3;
                            }
                            else if (CheckPointX + i < coord.ShipStartX)
                            {
                                coord.ShipStartX = CheckPointX + i;
                                return 3;
                            }
                            else if (CheckPointX + i > coord.ShipEndX)
                            {
                                coord.ShipEndX = CheckPointX + i;
                                return 3;
                            }
                        }
                    }
                }
            }
            return 1;
        }

        

        public static int DrawNearField(int X=55, int Y=7)
        {
            int oldX = Console.CursorLeft;
            int oldY = Console.CursorTop;

            if (game.Phase == 1)
            {
                if (shipsPlaced.Small == 4 && shipsPlaced.Medium == 3 && shipsPlaced.Big == 2 && shipsPlaced.Huge == 1)
                {
                    game.Phase++;
                    shipsPlaced.Small = 0;
                    shipsPlaced.Medium = 0;
                    shipsPlaced.Big = 0;
                    shipsPlaced.Huge = 0;
                    return 1;
                }


                Console.SetCursorPosition(X, Y - 1);
                Console.Write("Ships remained to place:");
                Console.SetCursorPosition(X + 1, Y + 1);
                Console.Write("Small: {0}", 4 - shipsPlaced.Small);
                Console.SetCursorPosition(X, Y + 2);
                Console.Write("Medium: {0}", 3 - shipsPlaced.Medium);
                Console.SetCursorPosition(X + 3, Y + 3);
                Console.Write("Big: {0}", 2 - shipsPlaced.Big);
                Console.SetCursorPosition(X + 2, Y + 4);
                Console.Write("Huge: {0}", 1 - shipsPlaced.Huge);

                Console.SetCursorPosition(oldX, oldY);
                return 0;
            }
                return 2;
        }

        public static string ReadShipSize() //Function for reading Ship's size - from 1 to 4
        {
            string result = "";
            while (true)
            {
                ConsoleKeyInfo key = Console.ReadKey(true);
                switch (key.Key)
                {
                    case ConsoleKey.Backspace:
                        if (result.Length > 0)
                        {
                            result = "";
                            Console.Write("\b \b");
                        }
                        break;
                    case ConsoleKey.Enter:
                        if (result.Length > 0)
                        {
                            Console.WriteLine();
                            return result;
                        }
                        break;
                    default:
                        if(result.Length > 0){
                            Console.Write("\b");
                        }
                        if (char.IsDigit(key.KeyChar) && key.KeyChar > 48 && key.KeyChar < 53)
                        {
                            coord.ShipSize = key.KeyChar - 48;

                            if (coord.ShipSize == 1 && shipsPlaced.Small == 4)
                            {
                                break;
                            }
                            if (coord.ShipSize == 2 && shipsPlaced.Medium == 3)
                            {
                                break;
                            }
                            if (coord.ShipSize == 3 && shipsPlaced.Big == 2)
                            {
                                break;
                            }
                            if (coord.ShipSize == 4 && shipsPlaced.Huge == 1)
                            {
                                break;
                            }


                            result = "" + key.KeyChar;
                            Console.Write(key.KeyChar);
                        }
                        break;
                }
            }
        }

        public static void ReadCell(int Purpose) //Function for reading cells, only first 10 letters allowd and numbers from 1 to 10
        {
            string result = "";
            bool TwoSized = false;
            while (true)
            {
                ConsoleKeyInfo key = Console.ReadKey(true);
                switch (key.Key)
                {
                    case ConsoleKey.Backspace:
                        if (result.Length > 0)
                        {
                            ClearCurrentConsoleLine();
                            result = "";
                            TwoSized = false;
                        }
                        break;
                    case ConsoleKey.Enter:
                        if ((result.Length == 2) || (result.Length == 3 && TwoSized == true))
                        {
                            return;
                        }
                        break;
                    default:
                        if (result.Length > 2 && key.KeyChar != 48)
                        {
                            Console.Write("\b");
                        }
                        if((false == char.IsDigit(key.KeyChar) && result.Length == 0) && ((key.KeyChar > 64 && key.KeyChar < 75) || (key.KeyChar > 96 && key.KeyChar < 107)))
                        {
                            Console.Write(key.KeyChar);
                            result += key.KeyChar;

                            if (Purpose == 1){
                                if (key.KeyChar > 64 && key.KeyChar < 75)
                                {
                                    coord.ShipStartY = key.KeyChar - 64;
                                }
                                if (key.KeyChar > 96 && key.KeyChar < 107)
                                {
                                    coord.ShipStartY = key.KeyChar - 96;
                                }
                            }
                            else if(Purpose == 2){
                                if (key.KeyChar > 64 && key.KeyChar < 75)
                                {
                                    coord.ShipEndY = key.KeyChar - 64;
                                }
                                if (key.KeyChar > 96 && key.KeyChar < 107)
                                {
                                    coord.ShipEndY = key.KeyChar - 96;
                                }
                            }
                            else if(Purpose == 3){
                                if (key.KeyChar > 64 && key.KeyChar < 75)
                                {
                                    coord.ShootEnemyY = key.KeyChar - 64;
                                }
                                if (key.KeyChar > 96 && key.KeyChar < 107)
                                {
                                    coord.ShootEnemyY = key.KeyChar - 96;
                                }
                            }



                        }
                        else if (char.IsDigit(key.KeyChar) && result.Length == 1 && key.KeyChar != 48)
                        {
                            Console.Write(key.KeyChar);
                            result += key.KeyChar;
                            if (key.KeyChar == 49)
                            {
                                TwoSized = true;
                            }

                            if (Purpose == 1)
                            {
                                coord.ShipStartX = key.KeyChar - 48;
                            }
                            else if (Purpose == 2)
                            {
                                coord.ShipEndX = key.KeyChar - 48;
                            }
                            else if (Purpose == 3)
                            {
                                coord.ShootEnemyX = key.KeyChar - 48;
                            }
                        }
                      
                        else if (char.IsDigit(key.KeyChar) && key.KeyChar == 48 && TwoSized)
                        {
                            Console.Write(key.KeyChar);
                            result += key.KeyChar;

                            if (Purpose == 1)
                            {
                                coord.ShipStartX = 10;
                            }
                            else if (Purpose == 2)
                            {
                                coord.ShipEndX = 10;
                            }
                            else if (Purpose == 3)
                            {
                                coord.ShootEnemyX = 10;
                            }

                        }
                        break;
                }
            }
        }

        public static void RorM()
        {
            while (true)
            {
                ConsoleKeyInfo key = Console.ReadKey(true);
                  if (key.KeyChar == 77  || key.KeyChar == 109)
                        {
                            game.PlayerRandoms = 0;
                            break;
                            }
                  else if(key.KeyChar == 82 || key.KeyChar == 114)
                  {
                      game.PlayerRandoms = 1;
                      break;
                  }
           }
        }

        public static void ClearCurrentConsoleLine() //This function clears current lane
        {
            int currentLineCursor = Console.CursorTop;
            Console.SetCursorPosition(0, Console.CursorTop);
            for (int i = 0; i < Console.WindowWidth; i++)
                Console.Write(" ");
            Console.SetCursorPosition(0, currentLineCursor);
        }

        public static void Main()
        {
            int EnemyIsAlive = 1;
            int PlayerIsAlive = 1;
            game.PlayerRandoms = 1;


        start: // Starting point
            Console.Clear();
            if (EnemyIsAlive == 0 || PlayerIsAlive == 0)
            {
                game.Phase = 4;
                
            }
            //Creating two fields, 1 for the player, 2nd for the opponent
            Console.Title = "Battleships!"; // When project is finished, add " by NS-Projects"
            // Welcoming message
            Console.SetCursorPosition(6, 2);
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("Welcome to the Battleships!\n\n");

            DrawPlayerField();
            DrawEnemyField();

            //The part where rendering is completed
            
            //Starting to ask the user for ship placement settings
            Console.SetCursorPosition(1, 17); 
            ClearCurrentConsoleLine();

            if (game.Phase > 1)
            {
                goto EnemyShips;
            }
            if (game.level == 0)
            {
                Console.Write("\n Choose a level on a scale from Normal to Impossible (1-4):");
                ReadShipSize();
                game.level = coord.ShipSize;
                coord.ShipSize = 0;
            }
            if (game.PlayerRandoms == 1)
            {
                Console.Write("\n Do you want to randomize (press R) your ships or place them manually (press M):");

                RorM();
            }
            if (game.PlayerRandoms == 1)
            {

                game.Phase = 2;
                goto playerRand;
            }
            game.Phase = 1;
            //Ask for the size of the ship
            
             if(DrawNearField()==1)goto start;
            Console.Write("\n Insert the size (1-4) of the ship you want to place on the field:\n");
            ClearCurrentConsoleLine();
            ReadShipSize();
            ClearCurrentConsoleLine();
            Console.SetCursorPosition(1, 17);
            Console.WriteLine(" Current Ship parameters: Size = {0}", coord.ShipSize);
            ClearCurrentConsoleLine();
            Console.SetCursorPosition(1, 20);
            ClearCurrentConsoleLine();
            Console.SetCursorPosition(1, 21);
            ClearCurrentConsoleLine();
            Console.SetCursorPosition(1, 18);

            //Now ask for the starting point of the ship
            Console.WriteLine("Insert ship's starting point (example: C3; F7; etc.) :");
            //Here are the two integrers we use as X and Y coordinates
            ReadCell(1);

            if (coord.ShipSize == 1)
            { }
            else
            {

                ClearCurrentConsoleLine();
                Console.SetCursorPosition(1, 17);
                Console.WriteLine(" Current Ship parameters: Size = {0}", coord.ShipSize);
                ClearCurrentConsoleLine();
                Console.WriteLine();
                ClearCurrentConsoleLine();
                Console.SetCursorPosition(1, 18);

                Console.WriteLine(" The ship's starting point is {1}{0}\n", coord.ShipStartX, (char)(coord.ShipStartY+64));
                ClearCurrentConsoleLine();


                //Now ask for the ending point of the ship
                Console.WriteLine("Insert ship's ending point (example: D3; F4; etc.) :");

                ReadCell(2);
                ClearCurrentConsoleLine();
                Console.SetCursorPosition(1, 19);
                Console.WriteLine(" The ship's ending point is {1}{0}\n", coord.ShipEndX, (char)(coord.ShipEndY+64));
                ClearCurrentConsoleLine();
            }

            if (CheckShipCoordinates() == 1)
            {
                if (CheckShipSurrounding(1) == 1)
                {
                    AddShipToField(1);
                }
                else
                {
                    Console.WriteLine("Ship you are trying to place is too close to already exsisting ship!");
                    System.Threading.Thread.Sleep(3000);


                }

            }
            else { Console.WriteLine("Coordinates are NOT correct!");

            System.Threading.Thread.Sleep(3000);
            }
            Console.Clear();
            goto start;

            //Randomize ships
            playerRand:
            if(game.PlayerRandoms == 1){
            while (shipsPlaced.Huge < 1)
            {
                int k = 3;
                RandomCell(1);
                switch (RandomDirection())
                {
                    case 1:
                        coord.ShipEndX = coord.ShipStartX - k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 2:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY + k;
                        break;
                    case 3:
                        coord.ShipEndX = coord.ShipStartX + k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 4:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY - k;
                        break;
                }
                if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                {
                    coord.ShipSize = k + 1;
                    if (CheckShipCoordinates() == 1)
                    {
                        if (CheckShipSurrounding(1) == 1)
                        {
                            AddShipToField(1);
                        }
                    }
                }


            }
            while (shipsPlaced.Big < 2)
            {
                int k = 2;
                RandomCell(1);
                switch (RandomDirection())
                {
                    case 1:
                        coord.ShipEndX = coord.ShipStartX - k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 2:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY + k;
                        break;
                    case 3:
                        coord.ShipEndX = coord.ShipStartX + k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 4:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY - k;
                        break;
                }
                if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                {
                    coord.ShipSize = k + 1;
                    if (CheckShipCoordinates() == 1)
                    {
                        if (CheckShipSurrounding(1) == 1)
                        {
                            AddShipToField(1);
                        }
                    }
                }


            }
            while (shipsPlaced.Medium < 3)
            {
                int k = 1;
                RandomCell(1);
                switch (RandomDirection())
                {
                    case 1:
                        coord.ShipEndX = coord.ShipStartX - k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 2:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY + k;
                        break;
                    case 3:
                        coord.ShipEndX = coord.ShipStartX + k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 4:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY - k;
                        break;
                }
                if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                {
                    coord.ShipSize = k + 1;
                    if (CheckShipCoordinates() == 1)
                    {
                        if (CheckShipSurrounding(1) == 1)
                        {
                            AddShipToField(1);
                        }
                    }
                }


            }
            while (shipsPlaced.Small < 4)
            {
                int k = 0;
                RandomCell(1);
                switch (RandomDirection())
                {
                    case 1:
                        coord.ShipEndX = coord.ShipStartX - k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 2:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY + k;
                        break;
                    case 3:
                        coord.ShipEndX = coord.ShipStartX + k;
                        coord.ShipEndY = coord.ShipStartY;
                        break;
                    case 4:
                        coord.ShipEndX = coord.ShipStartX;
                        coord.ShipEndY = coord.ShipStartY - k;
                        break;
                }
                if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                {
                    coord.ShipSize = k + 1;
                    if (CheckShipCoordinates() == 1)
                    {
                        if (CheckShipSurrounding(1) == 1)
                        {
                            AddShipToField(1);
                        }
                    }
                }


            }
        }

            goto start;
            //Rerandomize ships?

            EnemyShips:
            if (game.Phase > 2)
            {
                goto fight;
            }
            Console.WriteLine("Enemy is placing his ships!");
            shipsPlaced.Small = 0;
            shipsPlaced.Medium = 0;
            shipsPlaced.Big = 0;
            shipsPlaced.Huge = 0;
                while (shipsPlaced.Huge < 1)
                {
                    int k = 3;
                    RandomCell(1);
                    switch (RandomDirection())
                    {
                        case 1:
                            coord.ShipEndX = coord.ShipStartX - k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 2:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY + k;
                            break;
                        case 3:
                            coord.ShipEndX = coord.ShipStartX + k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 4:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY - k;
                            break;
                    }
                    if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                    {
                        coord.ShipSize = k+1;
                        if (CheckShipCoordinates() == 1)
                        {
                            if (CheckShipSurrounding(2) == 1)
                            {
                                AddShipToField(2);
                            }
                        }
                    }
                }
                while (shipsPlaced.Big < 2)
                {
                    int k = 2;
                    RandomCell(1);
                    switch (RandomDirection())
                    {
                        case 1:
                            coord.ShipEndX = coord.ShipStartX - k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 2:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY + k;
                            break;
                        case 3:
                            coord.ShipEndX = coord.ShipStartX + k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 4:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY - k;
                            break;
                    }
                    if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                    {
                        coord.ShipSize = k + 1;
                        if (CheckShipCoordinates() == 1)
                        {
                            if (CheckShipSurrounding(2) == 1)
                            {
                                AddShipToField(2);
                            }
                        }
                    }
                }
                while (shipsPlaced.Medium < 3)
                {
                    int k = 1;
                    RandomCell(1);
                    switch (RandomDirection())
                    {
                        case 1:
                            coord.ShipEndX = coord.ShipStartX - k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 2:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY + k;
                            break;
                        case 3:
                            coord.ShipEndX = coord.ShipStartX + k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 4:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY - k;
                            break;
                    }
                    if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                    {
                        coord.ShipSize = k + 1;
                        if (CheckShipCoordinates() == 1)
                        {
                            if (CheckShipSurrounding(2) == 1)
                            {
                                AddShipToField(2);
                            }
                        }
                    }
                }
                while (shipsPlaced.Small < 4)
                {
                    int k = 0;
                    RandomCell(1);
                    switch (RandomDirection())
                    {
                        case 1:
                            coord.ShipEndX = coord.ShipStartX - k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 2:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY + k;
                            break;
                        case 3:
                            coord.ShipEndX = coord.ShipStartX + k;
                            coord.ShipEndY = coord.ShipStartY;
                            break;
                        case 4:
                            coord.ShipEndX = coord.ShipStartX;
                            coord.ShipEndY = coord.ShipStartY - k;
                            break;
                    }
                    if (coord.ShipEndY > 0 && coord.ShipEndY < 11 && coord.ShipEndX > 0 && coord.ShipEndX < 11)
                    {
                        coord.ShipSize = k + 1;
                        if (CheckShipCoordinates() == 1)
                        {
                            if (CheckShipSurrounding(2) == 1)
                            {
                                AddShipToField(2);
                            }
                        }
                    }
                }

                game.Phase = 3;
            goto start;

            fight:

            if (EnemyIsAlive == 0 || PlayerIsAlive == 0) { 
                game.Phase = 4;
            }
            if (game.Phase > 3)
            {
                goto finish;
            }

            Console.WriteLine("Where you want to attack?");
            ReadCell(3);

            ClearCurrentConsoleLine();
            Console.SetCursorPosition(0, Console.CursorTop - 1);
            ClearCurrentConsoleLine();

            if (game.EnemyField[coord.ShootEnemyY, coord.ShootEnemyX] == 2)
            {
                game.EnemyField[coord.ShootEnemyY, coord.ShootEnemyX] = 3;

                coord.ShipStartY = coord.ShootEnemyY;
                coord.ShipStartX = coord.ShootEnemyX;
                coord.ShipEndY = coord.ShootEnemyY;
                coord.ShipEndX = coord.ShootEnemyX;

                int status = 0;
                while (status == 0)
                {
                    int z = CheckShipSurrounding(2);

                    if (z == 3) { }
                    else
                    {
                        status++;

                        if (z == 0)
                        {
                            //Ship is damaged but not dead
                        }
                        else
                        {
                            //Ship is dead
                            ShipElimination(2);
                            //Checking whether enemy is alive
                            EnemyIsAlive = 0;
                            for (int y = 0; y < 12; y++) {

                                for (int h = 0; h < 12; h++)
                                {
                                    if (game.EnemyField[y, h] == 2)
                                    {
                                        EnemyIsAlive = 1;
                                    }
                                }
                                
                            }
                            if (EnemyIsAlive == 0) { goto finish; }
                        }
                    }
                }

                DrawEnemyField();
                goto fight;
            }
            else
            {
                //Missed shot
                if (game.EnemyField[coord.ShootEnemyY, coord.ShootEnemyX] == 0)
                {
                    game.EnemyField[coord.ShootEnemyY, coord.ShootEnemyX] = 1;
                }
                DrawEnemyField();
                System.Threading.Thread.Sleep(800);
            }

            EnemyTurn:

            if (EnemyIsAlive == 1)
            {

                //AI Shootings are starting here
                if(game.DamageDone == 0)
                {
                  RandomCell(3);
                }
                else {

                    if (game.x == -1)
                    {
                        game.x = RandomDirection();
                    }
                    else
                    {
                       //
                           
                    }

                    for (int aproppriate = 0; aproppriate == 0; )
                    {
                        switch (game.x)
                        {
                            case 1:
                                if (coord.EnemyShootX - 1 > 0)
                                {

                                    if (game.MyField[coord.EnemyShootY, coord.EnemyShootX - 1] == 2 || game.MyField[coord.EnemyShootY, coord.EnemyShootX - 1] == 0 || game.MyField[coord.EnemyShootY, coord.EnemyShootX - 1] == 3)
                                    {
                                        if (game.TurnBack != 2)
                                        {
                                            coord.EnemyShootX = coord.EnemyShootX - 1;
                                            coord.EnemyShootY = coord.EnemyShootY;
                                            aproppriate++;
                                        }
                                        else
                                        {
                                            if (game.MyField[coord.EnemyShootY, coord.EnemyShootX-1] == 0)
                                            {
                                                game.x = RandomDirection();
                                            }
                                            else
                                            {
                                                coord.EnemyShootX = coord.EnemyShootX-1;
                                                coord.EnemyShootY = coord.EnemyShootY;
                                                aproppriate++;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        game.x = RandomDirection();
                                    }
                                }
                                else
                                {
                                    game.x = RandomDirection();
                                }
                                break;
                            case 2:

                                if (coord.EnemyShootY + 1 < 11)
                                {

                                    if (game.MyField[coord.EnemyShootY + 1, coord.EnemyShootX] == 2 || game.MyField[coord.EnemyShootY + 1, coord.EnemyShootX] == 0 || game.MyField[coord.EnemyShootY+1, coord.EnemyShootX] == 3)
                                    {
                                        if (game.TurnBack != 2)
                                        {
                                        coord.EnemyShootX = coord.EnemyShootX;
                                        coord.EnemyShootY = coord.EnemyShootY + 1;
                                        aproppriate++;
                                        }
                                        else
                                        {
                                            if (game.MyField[coord.EnemyShootY + 1, coord.EnemyShootX] == 0)
                                            {
                                                game.x = RandomDirection();
                                            }
                                            else
                                            {
                                                coord.EnemyShootX = coord.EnemyShootX;
                                                coord.EnemyShootY = coord.EnemyShootY + 1;
                                                aproppriate++;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        game.x = RandomDirection();
                                    }
                                }
                                else
                                {
                                    game.x = RandomDirection();
                                }
                                break;
                            case 3:

                                if (coord.EnemyShootX + 1 < 11)
                                {

                                    if (game.MyField[coord.EnemyShootY, coord.EnemyShootX + 1] == 2 || game.MyField[coord.EnemyShootY, coord.EnemyShootX + 1] == 0 || game.MyField[coord.EnemyShootY, coord.EnemyShootX + 1] == 3)
                                    {
                                        if (game.TurnBack != 2)
                                        {
                                        coord.EnemyShootX = coord.EnemyShootX + 1;
                                        coord.EnemyShootY = coord.EnemyShootY;
                                        aproppriate++;
                                        }
                                        else
                                        {
                                            if (game.MyField[coord.EnemyShootY, coord.EnemyShootX+1] == 0)
                                            {
                                                game.x = RandomDirection();
                                            }
                                            else
                                            {
                                                coord.EnemyShootX = coord.EnemyShootX + 1;
                                                coord.EnemyShootY = coord.EnemyShootY - 1;
                                                aproppriate++;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        game.x = RandomDirection();
                                    }
                                }
                                else
                                {
                                    game.x = RandomDirection();
                                }
                                break;
                            case 4:

                                if (coord.EnemyShootY - 1 > 0)
                                {

                                    if (game.MyField[coord.EnemyShootY - 1, coord.EnemyShootX] == 2 || game.MyField[coord.EnemyShootY - 1, coord.EnemyShootX] == 0 || game.MyField[coord.EnemyShootY - 1, coord.EnemyShootX] == 3)
                                    {
                                        if (game.TurnBack != 2)
                                        {
                                        coord.EnemyShootX = coord.EnemyShootX;
                                        coord.EnemyShootY = coord.EnemyShootY - 1;
                                        aproppriate++;
                                        }
                                        else
                                        {
                                            if (game.MyField[coord.EnemyShootY - 1, coord.EnemyShootX] == 0)
                                            {
                                                game.x = RandomDirection();
                                            }
                                            else
                                            {
                                                coord.EnemyShootX = coord.EnemyShootX;
                                                coord.EnemyShootY = coord.EnemyShootY - 1;
                                                aproppriate++;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        game.x = RandomDirection();
                                    }
                                }
                                else
                                {
                                    game.x = RandomDirection();
                                }
                                break;
                        }
                    }
                }

                //if the cell is not Clear / WithShip / Damaged Ship
                while (game.MyField[coord.EnemyShootY, coord.EnemyShootX] != 0 && game.MyField[coord.EnemyShootY, coord.EnemyShootX] != 2 && game.MyField[coord.EnemyShootY, coord.EnemyShootX] !=3)
                {   
                    RandomCell(3);
                }

                if (game.MyField[coord.EnemyShootY, coord.EnemyShootX] == 3)
                {
                    goto EnemyTurn; //Give enemy one more turn if he hit already damaged cell - part of AI system
                }

                if (game.MyField[coord.EnemyShootY, coord.EnemyShootX] == 2)
                {
                    if (game.DamageDone == 1){
                        game.TurnBack = 1;
                    }
                    game.DamageDone = 1;
                    game.MyField[coord.EnemyShootY, coord.EnemyShootX] = 3;

                    DrawPlayerField();
                    System.Threading.Thread.Sleep(500);


                    coord.ShipStartY = coord.EnemyShootY;
                    coord.ShipStartX = coord.EnemyShootX;
                    coord.ShipEndY = coord.EnemyShootY;
                    coord.ShipEndX = coord.EnemyShootX;

                    int status = 0;
                    while (status == 0)
                    {
                        int z = CheckShipSurrounding(1);

                        if (z == 3) { }
                        else
                        {
                            status++;

                            if (z == 0)
                            {
                                //Ship is damaged but not dead
                            }
                            else
                            {
                                //Ship is dead
                                ShipElimination(1);
                                game.DamageDone = 0;
                                game.TurnBack = 0;
                                game.x = -1;
                                //Checking whether player is alive
                                PlayerIsAlive = 0;
                                for (int y = 0; y < 12; y++)
                                {

                                    for (int g = 0; g < 12; g++)
                                    {
                                        if (game.MyField[y, g] == 2)
                                        {
                                            PlayerIsAlive = 1;
                                        }
                                    }
                                }
                                if (PlayerIsAlive == 0) { goto finish; }
                            }
                        }
                    }
                    DrawPlayerField();
                    System.Threading.Thread.Sleep(1200);
                    goto EnemyTurn; //Give enemy one more turn if he hit the player

                }
                else
                {
                    if (game.TurnBack == 1)
                    {
                        game.TurnBack = 2;
                    }
                    //Missed shot
                    if (game.MyField[coord.EnemyShootY, coord.EnemyShootX] == 0)
                    {
                        Random rnd = new Random(); //Difficulty level is right here
                        if ((game.level - 1) * 10 < rnd.Next(1, 101)) //Every 1 level gives AI a 10% chance on missing to reroll
                        {
                            game.MyField[coord.EnemyShootY, coord.EnemyShootX] = 1;  //Without actually shooting the field
                        }
                        else
                        {
                            goto EnemyTurn;
                        }
                    }

                    if (game.DamageDone == 1)
                    {
                        switch (game.x)
                        {
                            case 1:
                                coord.EnemyShootY = coord.EnemyShootY;
                                coord.EnemyShootX=coord.EnemyShootX+1;
                                break;
                            case 2:
                                coord.EnemyShootY = coord.EnemyShootY-1;
                                coord.EnemyShootX = coord.EnemyShootX;
                                break;
                            case 3:
                                coord.EnemyShootY = coord.EnemyShootY;
                                coord.EnemyShootX = coord.EnemyShootX-1;
                                break;
                            case 4:
                                coord.EnemyShootY = coord.EnemyShootY+1;
                                coord.EnemyShootX = coord.EnemyShootX;
                                break;
                        }
                    }
                }

                DrawPlayerField();
                System.Threading.Thread.Sleep(800);
                goto fight;
            }

            finish: //4th phase of the game, when someone won

            //resetting some containers
            shipsPlaced.Small = 0;
            shipsPlaced.Medium = 0;
            shipsPlaced.Big = 0;
            shipsPlaced.Huge = 0;
            game.Phase = 1;

            for (int y = 0; y < 12; y++)
            {

                for (int z = 0; z < 12; z++)
                {
                    game.EnemyField[y, z]=0;
                    game.MyField[y, z] = 0;
                }
            }
            if (PlayerIsAlive == 1)
            {
                Console.WriteLine("Congratulations, you won!");
            }
            else
            {
                Console.WriteLine("Damn, you lost!");
            }
            EnemyIsAlive = 1;
            PlayerIsAlive = 1;
            System.Threading.Thread.Sleep(5000);
            Console.ReadKey(); // Here goes matrix-style finish
            goto start;
        }
    }
}