using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.RegularExpressions;

namespace Tunniplaan
{
    public class Class
    {
        public Class(string Tund)
        {

            //Here we give Tunnid[a] it's parameters

            string TunniNimetus = "";
            foreach (char charN in Tund)
            {
                if (charN == 40)
                {
                    break;
                }
                else
                {
                    TunniNimetus += charN;
                }
            }
            Console.WriteLine("Tunni nimetus: {0}", TunniNimetus);

        }

        public static int Day { get; set; }
        public static int PaariNumber { get; set; }
        public static int ClassID { get; set; }
        public static string ClassName { get; set; }
        public static int Room { get; set; }
        public static string[] Groups { get; set; }
    }

    public static class Tunniplaan
    {
        public static int Amount = 0;// { get; set; }
        public static List<Class> Tunnid{ get; set; }
    }

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
                if (c == 4)
                {
                    d++;
                    c = -1;
                }
                c++;
            }

            return TempData;
        }

        static List<Class> ParseClasses(string[,] ChunkedData){
            List<Class> tempTunnid = new List<Class>();

            string pattern = "/";
            for (int d = 0; d < 5; d++)
            {
                for (int c = 0; c < 5; c++)
                {
                    string[] tunnidtekst = Regex.Split(ChunkedData[d,c], pattern);
                    for (int i = 0; i < (tunnidtekst.Count()); i++)
                    {
                        tunnidtekst[i] = tunnidtekst[i].Replace("\n", "");
                        if (tunnidtekst[i].Length > 0)
                        {
                            Console.WriteLine("Day {0} Paar {1}: {2}", d + 1, c + 1, tunnidtekst[i]);
                            tempTunnid.Add(new Class(tunnidtekst[i]));
                            Tunniplaan.Amount++;
                        }
                    }

                }


               
            }




            return tempTunnid;
        }


        static void Main(string[] args)
        {

            System.Net.WebClient wc = new System.Net.WebClient();
            string tunniplaan = wc.DownloadString("http://money.vnet.ee/tunniplaan31.txt");




            string [,] TimedData = SplitTime(tunniplaan);
            Tunniplaan.Tunnid = ParseClasses(TimedData);
            System.Console.ReadKey();


        }
    }
}
