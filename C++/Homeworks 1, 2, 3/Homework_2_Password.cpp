#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>


using namespace std;

int main()
{
    char *failinimi="semecka.txt";
    char password[20], password2[20];


    wans:


    for(int i = 0; i<20; i++){password[i]=NULL;password2[i]=NULL;}

    bool sobib(char[]), vordlus(char[], char[]);


    FILE *fp=fopen(failinimi, "r");

    if(!fp){

            //ESIMENE KORD

              printf("\n Looge parooli:");
              scanf("%20s", &password);
              printf("\n Sisestatud parool: %s", password);

if(sobib(password)){

printf("\n Palun sisestage parooli teist korda:");
scanf("%20s", &password2);
printf("\n Sisestatud parool: %s", password2);

if(!vordlus(password, password2)){
     printf("\n Sisestatud paroolid on erinevad. Veakood 1 ");
    return 1;
}

fclose(fp);

FILE *fp=fopen(failinimi, "w");

    fprintf(fp, password);
fclose(fp);

printf("\n Uus parool on loodud");

return 0;

}
else{
    printf("\n Vale sisestatud parool");
goto wans;


}
    }

     printf("\n Palun sisestage parooli:");
              scanf("%20s", &password);
              printf("\n Sisestatud parool: %s", password);
fgets(password2, 20, fp);
fclose(fp);
 printf("\n Parool failist: %s", password2);

if(vordlus(password, password2)){
    printf("\nParool on oige!\n Tere tulemast!");
}
else{
    printf("\nParool on vale! Veakood 2");
    return 2;

}
    return 0;
}

bool sobib(char parol[20]){

    if(parol[5]==NULL){printf("\n Parool ei vasta reeglitele, liiga v2ike");return 0;}

    for(int i = 0; i<20; i++){
        if(isdigit(parol[i]))break;
        if(i==19){printf("\n Parool ei vasta reeglitele, peab olema v2hemalt yks number");return 0;}
    }
    for(int i = 0; i<20; i++){
        if(islower(parol[i]))break;
        if(i==19){printf("\n Parool ei vasta reeglitele, peab olema v2hemalt yks v2ike t2ht");return 0;}
    }
    for(int i = 0; i<20; i++){
        if(isupper(parol[i]))break;
        if(i==19){printf("\n Parool ei vasta reeglitele, peab olema v2hemalt yks suur t2ht");return 0;}
    }


    for(int i = 0; i<20; i++){
        if((parol[i] == '+' || parol[i] == '-' || parol[i] == '!' || parol[i] == '?' || parol[i] == '*'))break;
        if(i==19){printf("\n Parool ei vasta reeglitele, peab olema v2hemalt yks erim2rk (+ - ! ? * ) ");return 0;}
    }

     for(int i = 0; i<20; i++){
        if((parol[i] != '+' && parol[i] != '-' && parol[i] != '!' && parol[i] != '?' && parol[i] != '*' && !isalnum(parol[i]) && parol[i]!=NULL)){
            printf("\n Tohib kasutada ainult j2rgmised m2rgid: + , - , ! , ? , * ");
                return 0;
        }
        }
    return 1;
}

bool vordlus(char parol[20], char parol2[20]){


for(int i = 0; i<20; i++){
    if(parol[i] != parol2[i])return 0;
    }


return 1;
}


