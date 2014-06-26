#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <algorithm>
#include <time.h>
using namespace std;

int kkpp(char* kuupaev){
    int paev;
    char ckuu[15];
    sscanf(kuupaev, "%d. %s", &paev, ckuu );
string kuu=ckuu;
if(kuu=="jaanuar"){
    return 100+paev;
}
if(kuu=="veebruar"){
    return 200+paev;
}
if(kuu=="märts"){
    return 300+paev;
}
if(kuu=="aprill"){
    return 400+paev;
}
if(kuu=="mai"){
    return 500+paev;
}
if(kuu=="juuni"){
    return 600+paev;
}
if(kuu=="juuli"){
    return 700+paev;
}
if(kuu=="august"){
    return 800+paev;
}
if(kuu=="september"){
    return 900+paev;
}
if(kuu=="oktoober"){
    return 1000+paev;
}
if(kuu=="november"){
    return 1100+paev;
}
if(kuu=="detsember"){
    return 1200+paev;
}

return 9900+paev;
}


class Star{
public:
    string name;
    int algus;
    int lopp;
};

class StarTable {
public:
    Star tahtkujud[12];
    int starcount = 0;
    void newStar (char*,int,int);
    string getStar(int);
    int getStarNR(int);
};

void StarTable::newStar (char* iname, int ialgus, int ilopp) {
    tahtkujud[starcount].name = iname;
    tahtkujud[starcount].algus = ialgus;
    tahtkujud[starcount].lopp = ilopp;
    starcount++;
}
string StarTable::getStar(int kuupaev){
    for(int i = 0; i<12; i++){
     if(kuupaev >=tahtkujud[i].algus && kuupaev <=tahtkujud[i].lopp){
         return tahtkujud[i].name;
     }
    }
    return tahtkujud[9].name;
}
int StarTable::getStarNR(int kuupaev){
    for(int i = 0; i<12; i++){
     if(kuupaev >=tahtkujud[i].algus && kuupaev <=tahtkujud[i].lopp){
         return i;
     }
    }
    return 9;
}

int main(){

    int kkpp(char*);
    StarTable taabel;

    char *failinimi="Tahtkuju.txt";
char rida[200], *p1, *p2, *p3;
FILE *fp=fopen(failinimi, "r");
if(!fp){printf("Faili %s ei leia!\n", failinimi); return 1;}

while(!feof(fp)){
fgets(rida, sizeof(rida), fp);
p1=strtok(rida, "(");
p2=strtok(NULL, "-");
p3=strtok(NULL, ")\n");

char* nimetus = p1;
int from = kkpp(p2);
int to = kkpp(p3);

taabel.newStar(nimetus, from, to);

printf("%s\t%04d - %04d\n", p1, from, to);

}
fclose(fp);

char isikukood[12]; for(int j=0; j<12; j++){isikukood[j]=NULL;}
printf("\n\tSisestage oma isikukoodi: ");
int number;
fgets (isikukood , 12 , stdin);
if(isikukood[11]==NULL && isikukood[10] != NULL){
string code = isikukood;
code=code.substr(3,4);
const char* kood=code.c_str();
number = atoi(kood);

cout << "\n" << taabel.getStar(number);
}
else{
    time_t sek=time(NULL);
    tm *aeg;
    aeg=localtime(&sek);
    number = (aeg->tm_mon+1)*100 + aeg->tm_mday;
    cout << "\nTana - " << taabel.getStar(number) << "\n";

}
    char *failinimi2="Isikud.txt";

FILE *ft=fopen(failinimi2, "r");
if(!ft){printf("Faili %s ei leia!\n", failinimi2); return 1;}

int top[12]={0,0,0,0,0,0,0,0,0,0,0,0};

while(!feof(ft)){
fgets(rida, sizeof(rida), ft);

string code = rida;
code=code.substr(3,4);
const char* kood=code.c_str();
number = atoi(kood);
top[taabel.getStarNR(number)]++;
}

int pop, poparv=0;

for(int i = 0; i<12; i++){
       // cout << "\n" << top[i];
        if(top[i]>poparv){pop=i;
        poparv = top[i];
        }
}

cout << "\nKoige populaarsem on " << taabel.tahtkujud[pop].name;
fclose(ft);
}









