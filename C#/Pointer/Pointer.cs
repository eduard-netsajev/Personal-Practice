using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pointer
{
    class Pointer
    {
        static void Main(string[] args)
        {
            Random r = new Random();
            int error = 0;
        start:   //Initializating programm, clearing the console, stating ingerers
            Console.Clear();
            int size = 0;
            bool ThisLine = false;
            int x = -1; int y = -1; //Coordinats of the pointer must be held here
            bool SpotOn = false;  //Containts whether the pointer is on the field or outside
            //This if() checks for errors history in case it is needed to write about them
            if (error != 0)
            {
                if (error == 1) Console.WriteLine("Not a valid number!");
                if (error == 2) Console.WriteLine("Not a valid input!");
            }
            //Asking user for a size of the field
            Console.Write("Which will be the size of the field? (an integer):");
            if (int.TryParse(Console.ReadLine(), out size) && size <= 25 && size > 0) // Try to parse the string as an integer
            {
                //Loading the project, some dark magic going on down there
                Console.CursorVisible = false;
                for (int load = 1; load < 101; load++)
                {
                    Console.WriteLine("Loading application - {0}%", load);
                    int randInt = r.Next(0, 6000);
                    if (load == 96)
                    {
                        System.Threading.Thread.Sleep(7000);
                    }
                    else if (randInt < 5800 && randInt > 4800)
                    {
                        System.Threading.Thread.Sleep(300);
                    }
                    else if (randInt <= 4800)
                    {
                        System.Threading.Thread.Sleep(100);
                    }
                    else
                    {
                        System.Threading.Thread.Sleep(randInt);
                    }
                    Console.Clear();
                }
                Console.WriteLine("Loading completed");    //Message about completed loading, 3s before proceeding
                System.Threading.Thread.Sleep(3000);
            reRender:   //Starting to reRendering the field after input
                //Rendering the field using spaces and X for the pointer
                Console.Clear();
                SpotOn = false;
                for (int heigh = 1; heigh <= size; heigh++)     //This cycle goes through every line
                {

                    if (heigh == y)
                    {                  //This condition checks whether this lane is he Pointed lane (y)
                        ThisLine = true;
                    }
                    for (int width = 1; width <= size; width++) //This cycle happens in every line, it renders the spaces
                    {
                        //Here, programm determines which background color should cell have
                        if (ThisLine == true && width == x) { Console.BackgroundColor = ConsoleColor.Red; SpotOn = true; }
                        else if (ThisLine == true || width == x) { Console.BackgroundColor = ConsoleColor.Yellow; }
                        else
                        {
                            Console.BackgroundColor = ConsoleColor.Blue;
                        }
                        Console.Write("  ");
                        if (x == -1)
                        {
                            System.Threading.Thread.Sleep(2);
                        }
                    }
                    ThisLine = false;
                    Console.WriteLine();
                }
                Console.BackgroundColor = ConsoleColor.Black;
                if (SpotOn == false)
                {
                    Console.WriteLine("Pointer is currently out of reach");
                }
                Console.WriteLine("Place a mark on the field (example: C3; F7; etc.) :");


                //This can be made much easier, more professional and better looking
                //Although the document is quite well documented and easy to read

                string coordin = Console.ReadLine();
                if (coordin.Length > 3) { error = 2; goto start; }
                else if (coordin.Length == 3 && size < 10) { error = 2; goto start; }
                if (coordin[0] == 'A' || coordin[0] == 'a') { x = 1; }
                else if (coordin[0] == 'B' || coordin[0] == 'b') { x = 2; }
                else if (coordin[0] == 'C' || coordin[0] == 'c') { x = 3; }
                else if (coordin[0] == 'D' || coordin[0] == 'd') { x = 4; }
                else if (coordin[0] == 'E' || coordin[0] == 'e') { x = 5; }
                else if (coordin[0] == 'F' || coordin[0] == 'f') { x = 6; }
                else if (coordin[0] == 'G' || coordin[0] == 'g') { x = 7; }
                else if (coordin[0] == 'H' || coordin[0] == 'h') { x = 8; }
                else if (coordin[0] == 'J' || coordin[0] == 'j') { x = 9; }
                else if (coordin[0] == 'K' || coordin[0] == 'k') { x = 10; }
                else if (coordin[0] == 'L' || coordin[0] == 'l') { x = 11; }
                else if (coordin[0] == 'M' || coordin[0] == 'm') { x = 12; }
                else if (coordin[0] == 'N' || coordin[0] == 'n') { x = 13; }
                else if (coordin[0] == 'O' || coordin[0] == 'o') { x = 14; }
                else if (coordin[0] == 'P' || coordin[0] == 'p') { x = 15; }
                else if (coordin[0] == 'Q' || coordin[0] == 'q') { x = 16; }
                else if (coordin[0] == 'R' || coordin[0] == 'r') { x = 17; }
                else if (coordin[0] == 'S' || coordin[0] == 's') { x = 18; }
                else if (coordin[0] == 'T' || coordin[0] == 't') { x = 19; }
                else if (coordin[0] == 'U' || coordin[0] == 'u') { x = 20; }
                else if (coordin[0] == 'V' || coordin[0] == 'v') { x = 21; }
                else if (coordin[0] == 'W' || coordin[0] == 'w') { x = 22; }
                else if (coordin[0] == 'X' || coordin[0] == 'x') { x = 23; }
                else if (coordin[0] == 'Y' || coordin[0] == 'y') { x = 24; }
                else if (coordin[0] == 'Z' || coordin[0] == 'z') { x = 25; }
                else
                {
                    error = 2;
                    goto start;
                }
                if (coordin.Length == 3)
                {
                    if (int.TryParse(new string(coordin[1],coordin[2]), out y)) goto reRender;
                    else
                    {
                        error = 2;
                        goto start;
                    }
                }
                else if (coordin.Length == 2)
                {
                    char[] smallX = {coordin[1]};
                    if (int.TryParse(new string(smallX), out y)) goto reRender;
                    else
                    {
                        error = 2;
                        goto reRender;
                    }

                }
                else
                {
                    goto reRender;
                }
            }
            else
            {
                //What to do if parsing input as integrer <=20 has failed
                Console.Write("Not a valid number!");
                Console.Clear(); //Clears whole console
                error = 1;
                goto start; // Starts a programm from a start with error = 1
            }
        }
    }
}
