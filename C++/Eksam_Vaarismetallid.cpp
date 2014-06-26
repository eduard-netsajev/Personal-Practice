#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string.h>
using namespace std;


int aegSulamiseniJaKeemiseni(char* temp){
    int grad = atoi(temp);
    int aeg = grad*12/17;
    if(grad - aeg*85/60 == 0 )return aeg;
    else return aeg+1;
}

int kasutamiseAeg(char* daatum){
    int aasta = atoi(daatum);
    if(strlen(daatum)>5){ return aasta+2013; }
    else { return 2013-aasta; }}

int main()
{
        int aegSulamiseniJaKeemiseni(char*);
        int kasutamiseAeg(char*);

        char *failinimi="Vaarismetallid.txt";
        char rida[200], *p1, *p2, *p3, *p4;

for(int c = 65; c < 91; c++){

FILE *fp=fopen(failinimi, "r");
if(!fp){printf("Faili %s ei leia!\n", failinimi); return 1;}

while(!feof(fp)){

fgets(rida, sizeof(rida), fp);
p1=strtok(rida, "\t");
p2=strtok(NULL, "\t");
p3=strtok(NULL, "\t");
p4=strtok(NULL, ")\n");

char* nimetus = p1;
char* sulT = p2;
char* keemT = p3;
char* avaaasta = p4;

char jarjestus = nimetus[0];
if(jarjestus != c)continue;

int sulaeg = aegSulamiseniJaKeemiseni(p2);
int kemaeg = aegSulamiseniJaKeemiseni(p3);

int sulmin=sulaeg/60;
int sulsec=sulaeg%60;

int kemmin=kemaeg/60;
int kemsec=kemaeg%60;

printf("\n%s (%s)\n\tSulamistemperatuur: %s (%d minutit, %d sekundid)\n\tKeemistemperatuur: %s (%d minutit, %d sekundid)\n\tAvastati %d aastat tagasi\n",nimetus, avaaasta, sulT, sulmin, sulsec, keemT, kemmin, kemsec, kasutamiseAeg(p4));
}
fclose(fp);
}
    return 0;
}
