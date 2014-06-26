#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string.h>
using namespace std;

using namespace std;


float protsent(int a, int b){
float x = float(a);
float y = float(b);
float result = x/y*100;
return result;
}

int main()
{

    float protsent(int, int);

    printf("Protsent(133,200) v22rtus on %.1f \n", protsent(133,200));

    char *failinimi="Decathlon.txt";
    char rida[200], *p1, *p2, *p3, *p4, *p5, *p6;
    char riigid[100][3];
    for(int j=0; j<100; j++){
     for(int c=0; c<3; c++){
        riigid[j][c]=NULL;
    }}

    FILE *fp=fopen(failinimi, "r");
if(!fp){printf("Faili %s ei leia!\n", failinimi); return 1;}

fgets(rida, sizeof(rida), fp);
fgets(rida, sizeof(rida), fp);

float parimf = 0;
int parim =0;
int i=0;
int erinevadRiigid=0;

float pointsf = 0;
int points =0;

int olijuba=0;

while(!feof(fp)){

fgets(rida, sizeof(rida), fp);

p1=strtok(rida, "\t");
p2=strtok(NULL, "\t");
p3=strtok(NULL, "\t");
p4=strtok(NULL, "\n");

char* koht = p1;
char* tulemus = p2;

if(!i){
    parimf = atof(tulemus);
    parim = (int) parimf;
    printf("Parim tulemus on %d\n\n", parim);

}
else{
    pointsf = atof(tulemus);
    points = (int) pointsf;
    if(protsent(points, parim)<90.0){
            continue;
    }
}

char* nimi = p3;
char* riik = p4;

for(int k=0; strlen(riigid[k])>1; k++){
    if(riigid[k][0]==riik[0]){
         if(riigid[k][1]==riik[1]){
            if(riigid[k][2]==riik[2]){
        olijuba = 1;
    }}}
}
if(!olijuba){
        riigid[erinevadRiigid][0]=riik[0];
        riigid[erinevadRiigid][1]=riik[1];
        riigid[erinevadRiigid][2]=riik[2];

printf("%s (%s) on saanud %s. koha tulemusega %s.\n", nimi, riik, koht, tulemus);
erinevadRiigid++;

}

fgets(rida, sizeof(rida), fp);
p5=strtok(NULL, "\t");
p6=strtok(NULL, "\n");
i++;
olijuba=0;
}

fclose(fp);


    return 0;
}
