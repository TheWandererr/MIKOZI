#include <iostream>
#include <fstream>

using namespace std;


#define INT_BIT_SIZE 32
#define COMMON_BIT_SIZE 10000000
#define D 3

class LFSR {
public:
	unsigned long long state;
	unsigned long long polynomial;
	unsigned long long border;
	LFSR() {
		state = 0;
		polynomial = 0;
		border = 0;
	}
	int lfsr() {
		if (state & 1) {
			state = (state ^ polynomial);
			state = (state >> 1) | border;
			return 1;
		}
		else {
			state >>= 1;
			return 0;
		}
	}
};


int calcGeffe(LFSR* data) {
	int first = data[0].lfsr();
	int second = data[1].lfsr();
	int third = data[2].lfsr();

	return ((first & second) ^ ((first ^ 1) & third));
}

void run(LFSR* data) {
	ofstream fout("out.bin", ios::binary);
	int border = COMMON_BIT_SIZE / INT_BIT_SIZE;
	int bits;
	int index;
	for (int i = 0; i < border; i++) {
		bits = 0;
		index = 1;
		for (int j = 0; j < INT_BIT_SIZE; j++) {
			bits += index * calcGeffe(data);
			index *= 2;
		}
		fout.write((char*)&bits, sizeof(bits));
	}
	fout.close();
}


int main()
{
	LFSR inputData[D] = {};
	
	inputData[0].state = 0x4AFE85;
	inputData[0].polynomial = 0x400026;
	inputData[0].border = 0x400000;

	inputData[1].state = 0x1EF46F79;
	inputData[1].polynomial = 0x1000007C;
	inputData[1].border = 0x10000000;

	inputData[2].state = 0x4E2BF982;
	inputData[2].polynomial = 0x40000023;
	inputData[2].border = 0x40000000;

	/*inputData[0].state = 0x52AD63;
	inputData[0].polynomial = 0x400045;
	inputData[0].border = 0x400000;

	inputData[1].state = 0x1AA66BDA;
	inputData[1].polynomial = 0x100000B9;
	inputData[1].border = 0x10000000;

	inputData[2].state = 0x7EFADFF3;
	inputData[2].polynomial = 0x40000054;
	inputData[2].border = 0x40000000;*/

	run(inputData);
	return 0;
}

