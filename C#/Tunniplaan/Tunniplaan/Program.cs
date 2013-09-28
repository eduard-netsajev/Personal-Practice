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
        public Class(string Tund, int DayNumber, int NumberOfPair, int IDofClass)
        {
        //Day = 0;
        //PaariNumber = 0;
        //ClassID = 0;
        //ClassName = "";
        //Room = 0;
        //Groups = new List<string>(); //{ get; set; }

            //Here we give Tunnid[a] it's parameters

            Day = DayNumber+1;
            PaariNumber = NumberOfPair+1;
            ClassID = IDofClass+1;
            //if (ClassID == 2)
            //{
            //    int x = 5;
            //}

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

            Tund = Tund.Replace(ClassName + "(", "");
            
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

            Console.WriteLine("Day {0} Paar {1}: Aine {2} Ruumis {3} Gruppidega ", Day + 1, PaariNumber + 1, ClassName, Room);
            Groups.ForEach(delegate(String ruhm)
            {
                Console.Write(ruhm + " ");

            });

           

        }

        public int Day { get; set; }
        public int PaariNumber { get; set; }
        public int ClassID { get; set; }
        public string ClassName { get; set; }
        public int Room { get; set; }
        public List<string> Groups = new List<string>(); //{ get; set; }
    }

    public class Tunniplaan
    {
        public static int Amount = 0;// { get; set; }
        public static List<Class> Tunnid { get; set; } // = new List<Class>()
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
                            tempTunnid.Add(new Class(tunnidtekst[i], d, c, Tunniplaan.Amount));
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

            //Tunniplaan.Tunnid = ParseClasses(TimedData);
            List<Class> x = new List<Class>();
            //x = ParseClasses(TimedData);
            //Tunniplaan.Tunnid = x;
            Tunniplaan.Tunnid = ParseClasses(TimedData);
            System.Console.ReadKey();


        }
    }
}
