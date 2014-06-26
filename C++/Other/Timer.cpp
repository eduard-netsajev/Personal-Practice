#include <stdio.h>
#include <time.h>
#include <conio.h>

using namespace std;

int main()
{
    time_t sek, algus;
    clock_t tapne;
    tapne=clock();
    sek=time(NULL);
    algus = sek;
    printf("Sekundeid %d\n", sek);
    getch();
    printf("Aega kulus %d sekundit\n", time(NULL)-algus);
    printf("t2psemalt %6.3f\n", (float)clock()/CLK_TCK);


    return 0;
}
