#include <fstream>
#include <string.h>
#include <stdio.h>


int main()
{
    bool detect(char);//Funktsioon eraldajate otsimiseks
    char *failinimi="Eriala.txt";
    FILE *f=fopen(failinimi, "r");

     if(!f){printf("Faili %s ei leia!", failinimi); return 1;}

   //Faili suuruse leidmine
   fseek(f, 0, SEEK_END);
   size_t size = ftell(f);

    //Terve tekst
   char* tekst = new char[size];
   rewind(f);
   fread(tekst, sizeof(char), size, f);

int i=0, sonadarv=0, uniks=0, found;

for(i = 0; tekst[i]!='\0'; i++){
if(isalpha ( tekst[i] )){
    while(detect(tekst[i])){
          i++;
          }
    sonadarv++;
}
}
printf("\nM2rgid: %d \nSonad: %d \n", i, sonadarv);

char* sonad[sonadarv]; //Massiiv koikide unikaalsete sonade jaoks
int unikarv[sonadarv]; //Massiiv sonade kordamise loendi jaoks

for(i = 0; i<sonadarv; i++){
    unikarv[i]=1;
}
//Jagame teksti sonadeks
char * pch;
pch = strtok (tekst," ,.!?:;\n\t[](){}\"1234567890");
while (pch != NULL )
{
pch = strtok (NULL, " ,.!?:;\n\t[](){}\"1234567890");
if(pch == NULL)break;
found = 0;
for(i=0; i<uniks; i++){
    if(strcmp(pch, sonad[i])==NULL){
unikarv[i]++;
        found++;
    }
}
if(found == 0){
    sonad[uniks]=pch;
    uniks++;
}
}
//Otsime koige populaarsemat sona
int top=0;

for(int h=0; h<uniks; h++){
    if(unikarv[h]>top)top=unikarv[h];
}
//Prindime valja sonad kahanevas jarjekorras
for(int h = top; h>0; h--){
    for(int d = 0; d<uniks; d++){
        if(unikarv[d]==h){
            printf("\n%s - %d korda",sonad[d], h);
        }
    }
}
  delete[] tekst;
  return 0;
}

bool detect(char c){
switch(c){
case ' ':
case ',':
case '.':
case '!':
case '\n':
case '\t':
case ':':
case ';':
case '"':
case '?':
case '{':
case '}':
case '(':
case ')':
case '[':
case ']':
    return 0;
default:
    return 1;
}
}
