using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.RegularExpressions;

namespace Tunniplaan
{
    class Class
    {
        public static int Day { get; set; }
        public static int PaariNumber { get; set; }
        public static int ClassID { get; set; }
        public static string ClassName { get; set; }
        public static int Room { get; set; }
        public static string[] Groups { get; set; }

    }
    //class Tunniplaan
    //{
    //     public static string[,] TempData = new string[5, 5];//Array [Day, Class]
    //    //public static Array Monday = new Array[5,0];
    //    //public static Array Tuesday = new Array[5,0];
    //    //public static Array Wednesday = new Array[5,0];
    //    //public static Array Thursday = new Array[5,0];
    //    //public static Array Friday = new Array[5,0];
    //}
    
    class Program
    {
        static string[,] SplitTime(string TunniplaanTXT){

            string[,] TempData = new string[5, 5];//Array [Day, Class]
            string pattern = "Day";            // Split on "Day"

            string[] substrings = Regex.Split(TunniplaanTXT, pattern);

            int d = 0; //Day
            int c = 0; //Class


            for (int i = 0; i < 25; i++)
            {
                TempData[d, c] = substrings[i];
            //    System.Console.WriteLine ("Day {0} Class {1} : {2}", d+1, c+1, TempData[d, c]);
                if (c == 4)
                {
                    d++;
                    c = -1;
                }
                c++;
            }

            return TempData;
        }




        static void Main(string[] args)
        {

            System.Net.WebClient wc = new System.Net.WebClient();
            string tunniplaan = wc.DownloadString("http://money.vnet.ee/tunniplaan31.txt");




            string [,] TimedData = SplitTime(tunniplaan);
            Class[] tunnid = new Class[0]; //Array of Class (Class = Class)
            System.Console.ReadKey();


        }
    }
}
