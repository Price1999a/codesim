#include <stdio.h>

int f(int a, int b) {
	if (b == 0) return a;
	else {
		return f(b, a % b);
	}
}

int f1(int aa){
    if(aa==0) return 0;
    return aa+f1(aa-1);
}
