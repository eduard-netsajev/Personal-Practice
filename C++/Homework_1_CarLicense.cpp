#include <stdio.h>
#include <stdlib.h>
#include <string>

using namespace std;

int main()
{
    char name[10];
    bool sobib(char[]), tavaline(char[]), erinumber;
    int numbriPikkus(char[]), pikkus;
    printf("\nKodune nr.1 - Eduard Netsajev (IAPB23)\n");

    algus:
    for(int i = 0; i<10; i++)name[i]=NULL;
    erinumber = true;




    printf("\nPalun sisestage autonumbri (ilma tyhikuteta) >>> ");
    scanf("%1000s", &name);

    if(sobib(name))
    {
        pikkus = numbriPikkus(name);
        switch(pikkus){
            case 5:
            if(tavaline(name) && !isdigit(name[2]))erinumber = false;
            break;
            case 6:
            if(tavaline(name) && isdigit(name[2]) && !isdigit(name[5]))erinumber = false;
        }

        if(erinumber){
            printf("Tegemist on eritellimusel tehtud numbrim2rgiga");
        }
        else{
            printf("Tegemist on tavalise numbrim2rgiga");
        }
    }
    goto algus;
    return 0;
}

//Kontrollime sisestatud number sobib yldse v6i mitte
bool sobib(char autonimi[10]){
    //Kontrollime kas number ei ole liiga pikk
    if(autonimi[9]!=NULL){printf("Number ei vasta reeglitele, liiga pikk");return 0;}
    //Kontrollime kas numbris on v2hemalt yks number
    for(int i = 0; i<10; i++){
        if(isdigit(autonimi[i]))break;
        if(i==9){printf("Number ei vasta reeglitele, peab olema v2hemalt yks number");return 0;}
    }
    //Kontrollime kas k6ik numbrim2rgid on arvud ja tavat2hed
    for(int i = 0; i<10; i++){
        if(!isalnum(autonimi[i]) && autonimi[i]!=NULL){printf("Number ei vasta reeglitele, kasutatud mittelubatud m2rgid");return 0;}
    }
    return 1;
}

//Saame numbri pikkus
int numbriPikkus(char autonimi[10]){
    for(int i = 0; i<10; i++){
        if(autonimi[i] == NULL)return i;
    }
}

bool tavaline(char autonimi[10]){
if(isdigit(autonimi[0]) && isdigit(autonimi[1]) && !isdigit(autonimi[3]) && !isdigit(autonimi[4]))return 1;
return 0;
}
