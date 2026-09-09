#include <stdio.h>
int main(){
    char star='*';
    for(int i=1; i<7; i++){
        for (int j=1; j<=i; j++){
            printf("%c", star);
        }
        printf("\n");
    }
}
