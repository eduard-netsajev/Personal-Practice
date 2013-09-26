using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication2
{
    class BuzzFizz
    {
        static void Main(string[] args)
        {

            //The first my lines using C# without even knowing the language, props to Artur for helping me with the syntacs
            int x = 1337;
            Console.WriteLine("{0} - Hello World!", x);
            Console.ReadKey();

            Random r = new Random(); //Here starts the BuzzFizz solution

            for (int y = 0; y < 1000; y++) // Where y = number of random numbers to be generated
            {
                int randInt = r.Next(0, 1000);
                System.Threading.Thread.Sleep(5);
                Console.Write("\n");
                //Tabulating the numbers
                if (randInt < 100)
                {
                    Console.Write(" ");
                }
                if (randInt < 10)
                {
                    Console.Write(" ");
                }
                if (randInt % 15 == 0)
                {
                    Console.Write("{0} - BuzzFizz", randInt);
                }
                else if (randInt == 777) // Just some random addition by me, is not assotiated with the original BuzzFizz at all
                {
                    Console.Write("BINGO! YOU GOT THE JACKPOT FOR SCORING {0} POINTS! \n Press any key to aquire the prize!", randInt);
                    Console.ReadKey();
                }
                else if (randInt % 3 == 0)
                {
                    Console.Write("{0} - Buzz", randInt);
                }
                else if (randInt % 5 == 0)
                {
                    Console.Write("{0} - Fizz", randInt);
                }
                else // if not a single condition meets
                {
                    Console.Write("{0}", randInt);
                }
            }
            Console.Write("Run was successful, Programm is working as intended.");
            Console.ReadKey();
        }
    }
}
