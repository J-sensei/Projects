#include <vector>
#include <cmath>
#include "LinkedList.cpp"

template <typename T>
class HashTableChain {
private:
    vector< LinkedList<T> > table; // Vector contains the data
    int prime;

    //Hash the string
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
    HashTableChain (int size) {
        table.resize (size); // resize vector to support size elements.
        prime = nearestPrime(size);
    }

    ~HashTableChain() {
        for (int i = 0; i < table.size(); i++)
            table[i].makeEmpty();
    }

    int size() {
        return table.size();
    }

    void insert (T newItem) {
        int location = hashfunction(newItem);
        table[location].insertFront(newItem);
    }

    bool retrieve (const T& target) {
        int location = hashfunction(target);
        return table[location].find(target);
    }

    double usage() {
        int used = 0;
        for(int i = 0; i < table.size(); i++) {
            if(!table[i].isEmpty()) used++;
        }

        return (used / (double)table.size()) * 100;
    }

    void collision() {
        int result = 0;

        for(int i = 0; i < table.size(); i++) {
            if(table[i].isEmpty()) cout << i << ": " << "Empty" << endl;
            else {
                cout << i << ": " << table[i].countCollision() << endl;
                result += table[i].countCollision();
            }
        }
        cout << "Total Collision: " << result << endl;
    }

    friend ostream& operator<< (ostream& os, HashTableChain<T>& ht) {
        for (int i = 0; i < ht.size(); i++)
            os << i << " = " << ht.table[i] << endl;
        return os;
    }
};

