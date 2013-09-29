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
        
        public Class(string Tund, int DayNumber, int NumberOfPair)
        {
        
            //Here we give Class it's parameters upon creation

            Day = DayNumber+1;
            PaariNumber = NumberOfPair+1;
          

            string temp = "";

            foreach (char charN in Tund)
            {
                if (charN == 40)
                {
                    break;
                }
                else
                {
                    ClassName += charN;
                }
            }


            ClassID = Tunniplaan.getClassID(ClassName);

            Tund = Tund.Replace(ClassName + "(", "");
            switch (Tund[0])
            {
                case '2':
                    PaarisPaaritu = 2;
                    break;
                case '3':
                    PaarisPaaritu = 3;
                    break;
                case '6':
                    PaarisPaaritu = 6;
                    break;
            }
            Tund = Tund.Replace(Tund[0] + "?", ""); //Check if it uses this only ONCE, without replacing anything other
            foreach (char charN in Tund)
            {
                if (charN == 58)
                {
                    break;
                }
                else
                {
                    temp += charN;
                }
            }

            Tund = Tund.Replace(temp + ":", "");
            Room = int.Parse(temp);
            temp = "";

            for (int charNum = 0; Tund[charNum] != 41; charNum++)
            {
                if (Tund[charNum] == 44)
                {
                    Groups.Add(temp);
                    Tund = Tund.Replace(temp + ",", "");
                    temp = "";
                    charNum = 0;
                }
                else
                {
                    temp += Tund[charNum];
                }
            }
            Groups.Add(temp);

            Console.WriteLine("Nadal {4} Day {0} Paar {1}: Aine {2} ID {5} Ruumis {3} Gruppidega ", Day + 1, PaariNumber + 1, ClassName, Room, PaarisPaaritu, ClassID);
            Groups.ForEach(delegate(String ruhm)
            {
                Console.Write(ruhm + " ");

            });

           

        }

        public int PaarisPaaritu { get; set; }
        public int Day { get; set; }
        public int PaariNumber { get; set; }
        public int ClassID { get; set; }
        public string ClassName { get; set; }
        public int Room { get; set; }
        public List<string> Groups = new List<string>(); //{ get; set; }
    }

    public class Tunniplaan
    {
        public static int Amount = 0;
        public static List<Class> Tunnid { get; set; }

        public static List<string> ClassNames = new List<string>();

        public static int getClassID(string TundiNimi)
        {
            int id = 1;
            foreach (string nimetus in ClassNames)
            {
                if (TundiNimi == nimetus)
                {
                    return id;
                }
                else id++;
            }
            ClassNames.Add(TundiNimi);
            return id;
        }
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
                           // Console.WriteLine("Day {0} Paar {1}: {2}", d + 1, c + 1, tunnidtekst[i]);
                            //Class classA = new Class(tunnidtekst[i], d, c, Tunniplaan.Amount);
                            //tempTunnid.Add(classA);
                            tempTunnid.Add(new Class(tunnidtekst[i], d, c));
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
            string tunniplaan = wc.DownloadString("http://money.vnet.ee/tunniplaan41.txt");
            string [,] TimedData = SplitTime(tunniplaan);
            Tunniplaan.Tunnid = ParseClasses(TimedData);
            System.Console.ReadKey();


        }
    }
}
