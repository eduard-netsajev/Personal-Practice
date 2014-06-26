#include <iostream>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


using namespace std;

typedef enum { WHITE = 0, BLACK , GREEN, RED } Color, *pColor;

typedef struct { unsigned x,y; unsigned char label[30];} Point, *pPoint;

typedef struct { Point x, y; } Rect, *pRect;

pPoint most_distant(pPoint array, size_t size, Point pt){
    if(size == 0){return NULL;}
    pPoint kaugem;
    double max_distance = 0.0;
    double distance = 0.0;

    for(int i =0; i < size/sizeof(Point); i++){
    distance = sqrt( pow(abs((*array).x-pt.x), 2)+pow(abs((*array).y-pt.y), 2));
    if(distance > max_distance){
        max_distance = distance;
        kaugem = array;
    }
    array+=1;
    }
return kaugem;
}


int * recursive_min( int* array, size_t size){
     if(size == 0 || array == NULL){return NULL;}
    if(size/sizeof(int)== 1){return array;}
    int *jargmine = recursive_min(array+1, size-sizeof(int));
    if(*array < *jargmine ){
        return array;
    }
    else{
        return jargmine;
    }
}


void unset_bits(unsigned char x){
unsigned char mask = 3;
x = x & ~mask;
}

void unset_bitsMUUTUB(unsigned char* x){
unsigned char mask = 3;
*x = *x & ~mask;
}

int compar (const void* a, const
void* b){

    Point a1=*(Point*)a;
    Point b1=*(Point*)b;

    char* p1 = (char*) a1.label;
    char* p2 = (char*) b1.label;

    return strcmp(p1,p2);
}


int main()
{

   Point p1 = {1,3, "REDesimene"};
   Point p2 = {70,20, "GREENteine"};
   Point p3 = {70,10, "REDkolmas" };
   Point p4 = {12,4, "BLACKneljas" };
   Point p5 = {100, 100, "REDkaugem"};
   Point punktid[] = {p1, p2, p3, p4, p5};

   pPoint lahim = most_distant(punktid, sizeof(punktid), p1);

   cout << "Kaugem pukt on nimega " << (*lahim).label << endl;

   int massiv[]= {65,74,34,93,-123,13,-54};
   cout << "minimaalne arv massiivis on: " << *recursive_min(massiv, sizeof(massiv)) << endl;

    unsigned char c = 71;
    cout << "Oli taht " << c << endl;
    unset_bitsMUUTUB(&c);
    cout << "Nyyd on taht " << c << endl;

    return 0;
}
