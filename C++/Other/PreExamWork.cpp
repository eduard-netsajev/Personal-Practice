#include <iostream>
#include <string.h>
#include <stdio.h>

using namespace std;


void printBackwards(char *str)
{
   if(*str)
   {
       printBackwards(str+1);
       printf("%c", *str);
   }
}

unsigned long faktorial(unsigned long N){
if(N==0){return 1;}
return N*faktorial(N-1);
}


void foo1(int a, int b)
{ a = 10; b = 10; }

void foo2(int* a, int *b)
{ *a = 10; *b = 10; }

int main()
{
    cout << "Hello world!\n" << endl;

    char* word="Hello world!";
printBackwards(word);

int n1 = faktorial(5), *p1 = &n1;

cout << "\n" << faktorial(8) << endl;

cout << "Adress " << p1 << " hint at N = " << *p1 << endl;

int a[]={7,19,42,666};
int* p2=a;
p2=p2+2;
*p2+=1;
cout << *--p2 << "  " << a[2] << endl;


int c=2;
int b=3;

foo1(c,b);
cout << c << "   " << b << endl;


foo2(&c, &b);
cout << c << "   " << b << endl;


    return 0;
}


void set_bits(unsigned short x){
size_t suurus = sizeof(x);



}
