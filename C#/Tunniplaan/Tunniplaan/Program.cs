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
                default:
                    Console.WriteLine("Seems like some class lacks it's evenness!");
                    Console.ReadKey();
                    break;
            }
            Tund = Tund.Replace(Tund[0] + "?", "");
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
                    GroupsIDs.Add(Tunniplaan.getGroupID(temp));
                    Tund = Tund.Replace(temp + ",", "");
                    temp = "";
                    charNum = -1;
                }
                else
                {
                    temp += Tund[charNum];
                }
            }
            Groups.Add(temp);
            GroupsIDs.Add(Tunniplaan.getGroupID(temp));
        }

        public int Day { get; set; }
        public int PaariNumber { get; set; }

        public string ClassName { get; set; }
        public int ClassID { get; set; }

        public int PaarisPaaritu { get; set; }

        public int Room { get; set; }

        public List<string> Groups = new List<string>();
        public List<int> GroupsIDs = new List<int>();

    }

    public class Tunniplaan
    {
        public static int Amount = 0;
        public static List<Class> Tunnid { get; set; }

        public static List<string> ClassNames = new List<string>();

        public static List<string> GroupsNames = new List<string>();

        public static List<Class> GetClasses(int GroupNeeded, int DayNeeded, int EvennessNeeded)
        {
            List<Class> temporaryTunnid = new List<Class>();

            foreach (Class Tund in Tunnid)
            {
                if (Tund.Day == DayNeeded && (Tund.PaarisPaaritu == 6 || Tund.PaarisPaaritu == EvennessNeeded) && Tund.GroupsIDs.Contains(GroupNeeded) )
                {
                    temporaryTunnid.Add(Tund);
                }
            }


            return temporaryTunnid;
        }

        public static int groupExist(string gruppiNimi)
        {
            int id = 1;
            foreach (string gruppinimetus in GroupsNames)
            {
                if (gruppiNimi == gruppinimetus)
                {
                    return id;
                }
                else id++;
            }

            return 0;
        }

        public static int getGroupID(string gruppiNimi){
            int id = 1;
            foreach (string gruppinimetus in GroupsNames)
            {
                if (gruppiNimi == gruppinimetus)
                {
                    return id;
                }
                else id++;
            }
            GroupsNames.Add(gruppiNimi);
            return id;
        }

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
        public static void PrintoutClasses(List<Class> Tunnid, string MyGroup){
            Console.WriteLine("   Today you have next classes:");
            Console.WriteLine();
            Console.WriteLine();


            foreach (Class Tund in Tunnid)
            {
                int GroupsAmount = 0;
                Console.Write(Tund.ClassName + " in room " + Tund.Room);
                foreach (string Group in Tund.Groups)
                {
                    if (Group != MyGroup.ToUpper())
                    {
                        GroupsAmount++;
                    }

                }

                switch (GroupsAmount)
                {
                    case 0:
                        Console.Write(" with only your group.");
                        break;
                    case 1:
                        if (Tund.Groups[0] == MyGroup.ToUpper())
                        {
                            Console.Write(" with group {0}.", Tund.Groups[1]);
                        }
                        else
                        {
                            Console.Write(" with group {0}.", Tund.Groups[0]);
                        }
                        break;
                    default:
                        Console.Write(" with groups ");
                        foreach (string Group in Tund.Groups)
                        {
                            if (Group != MyGroup.ToUpper())
                            {
                                Console.Write(Group + ", ");
                            }

                        }
                        Console.Write("\b\b.");
                        break;
                }
                Console.WriteLine();
            }



        }

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
            string tunniplaan = wc.DownloadString("http://money.vnet.ee/tunniplaan5.txt");
            string [,] TimedData = SplitTime(tunniplaan);
            Tunniplaan.Tunnid = ParseClasses(TimedData);
            tunniplaan = "";
            TimedData = new string[0,0];

            Console.WriteLine("Programm is ready!");

            string MyGroup;

            do
            {
                Console.WriteLine("In which group do you study?");
                MyGroup = Console.ReadLine();
                Console.Clear();

            } while (Tunniplaan.groupExist(MyGroup.ToUpper()) == 0);

            int KasutajaGrupp = Tunniplaan.getGroupID(MyGroup.ToUpper());
            Console.WriteLine("Gruppi ID = {0}", KasutajaGrupp);

            int Day;

            do
            {
                Console.WriteLine("For what day you want to get classes?");
                int.TryParse(Console.ReadLine(), out Day);
                Console.Clear();

            } while (Day != 1 && Day != 2 && Day != 3 && Day != 4 && Day != 5);

            Console.WriteLine("Gruppi ID = {0}", KasutajaGrupp);
            Console.WriteLine("Day = {0}", Day);

            int PaarisPaaritu;

            do
            {
                Console.WriteLine("Is it an even or uneven week? (2/3)");
                int.TryParse(Console.ReadLine(), out PaarisPaaritu);
                Console.Clear();

            } while (PaarisPaaritu != 2 && PaarisPaaritu != 3);

            Console.WriteLine("Gruppi ID = {0}", KasutajaGrupp);
            Console.WriteLine("Day = {0}", Day);

            if(PaarisPaaritu==2){
                Console.WriteLine("Week is even");
            }
            else if(PaarisPaaritu==3){
                Console.WriteLine("Week is uneven");
            }

            PrintoutClasses(Tunniplaan.GetClasses(KasutajaGrupp, Day, PaarisPaaritu), MyGroup);

            Console.WriteLine();
            Console.Write("Press any key to exit..");
            System.Console.ReadKey();


        }
    }
}
