#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

template <typename T>
class HashTableLinear {
private:
    int n;
    int prime;
    T *table; // Data
    int hashfunction (const string &str) { // hash function
        int code = 0;

        for(int i = 0; i < str.length(); i++) {
            code += static_cast<int>(str[i]) * pow(2, i); //convert a char to its ascii value then add it
        }

        return code % prime;
    }

    // Determine the prime number
        bool isPrime(int n) {
        for(int i = 2; i <= n / 2; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }
    // find the nearest prime number
    int nearestPrime(int n) {
        for(int i = n - 1; i > 2; i--) {
            if(isPrime(i)) return i;
        }
        return -1;
    }
public:
    HashTableLinear (int size) {
        n = size;
        table = new T[size];
        prime = nearestPrime(n);
    }
    ~HashTableLinear() {
        delete []table;
    }

    int size() {
        return n;
    }

    void insert (T newItem) {
        int location = hashfunction(newItem);

        // find empty slot
        while(!table[location].empty()) { // while the content is not empty
            location = (location + 1) % n; // increase the location by 1
        }
        table[location] = newItem;
    }

    bool retrieve(T &target) {
        int location = hashfunction(target);

        while(!table[location].empty()) {
            if(table[location] == target) return true;
            location = (location + 1) % n;
        }
        return false;
    }

    double usage() {
        int used = 0;
        for(int i = 0; i < n; i++) {
            if(!table[i].empty()) used++;
        }

        return (used / (double)n) * 100;
    }

    friend ostream& operator<< (ostream& os, HashTableLinear<T>& ht) {
        for (int i = 0; i < ht.size(); i++) {
            os << i << " = ";
            if(!ht.table[i].empty()) {
                os << ht.table[i] << "(" << ht.hashfunction(ht.table[i]) << ")";
            }else {
                os << "Empty";
            }
            os << endl;
        }
        return os;
    }
};
