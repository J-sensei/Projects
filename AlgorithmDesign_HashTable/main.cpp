#include <iostream>
#include <string>
#include <fstream>
#include <chrono>
#include <cstdlib>

#include "HashTableChain.cpp"
#include "HashTableLinear.cpp"

using namespace std;

void selectTableMenu(const string &filename, ifstream &file);
void enterFileMenu();
void chaining(const string &filename, ifstream &file);
void searchByFile_Chain(HashTableChain<string> *ht);
void searchByValue_Chain(HashTableChain<string> *ht);

void linear(const string &filename, ifstream &file);
void searchByFile_Linear(HashTableLinear<string> *ht);
void searchByValue_Linear(HashTableLinear<string> *ht);

int countFileLine(string filename) {
    int count = 0;
    ifstream file;
    file.open(filename);

    string line;
    if(file.is_open()) {
        while(getline(file, line)) count++;
    }else {
        cout << "File cannot open" << endl;
        return -1;
    }

    file.close();
    return count;
}

// Menu stuff

int checkInput(int choice, int min, int max) {
    int c = choice;
    while(!(c >= min && c <= max)) {
        cout << "Invalid Input. Enter again: ";
        cin >> c;
    }
    return c;
}

/////// Linear Probing Hash Table Menu ///////////////////
void linear(const string &filename, ifstream &file) {
    int fileLines = countFileLine(filename);
    HashTableLinear<string> *hashTable = new HashTableLinear<string>(fileLines * 1.5);

    chrono::duration<double>insertTime;

    string line;
    if(file.is_open()) {
        auto start = chrono::system_clock::now();
        while(getline(file, line)) {
            hashTable->insert(line);
        }
        auto end = chrono::system_clock::now();

        insertTime = end - start;
    }else {
        cout << "File cannot open" << endl;
    }
    file.close();

    cout << "File Lines: " << fileLines << endl;
    cout << "Hash Table (Linear Probing) Size: " << hashTable->size() << endl;
    cout << "Hash Table Usage: " << hashTable->usage() << "%" << endl;
    cout << "Time Taken to Insert: " << insertTime.count() << "s" << endl;

    int choice;
    do {
        cout << "0. Exit" << endl;
        cout << "1. Return to Enter Filename" << endl;
        cout << "2. Return to Select Hash Table" << endl;
        cout << "3. Search the Data using File" << endl;
        cout << "4. Search the Data by insert the value" << endl;
        cout << "5. Print Hash Table (Linea Probing)" << endl;

        cout << "Enter: ";
        cin >> choice;
        choice = checkInput(choice, 0, 5);

        switch(choice) {
            case 0:
                delete hashTable;
                exit(0);
            case 1:
                delete hashTable;
                enterFileMenu();
            case 2:
                delete hashTable;
                file.open(filename);
                selectTableMenu(filename, file);
            case 3: searchByFile_Linear(hashTable); break;
            case 4: searchByValue_Linear(hashTable); break;
            case 5:
                cout << "----- Hash Table -----" << endl;
                cout << *hashTable << endl;
                break;
        }
    }while(choice != 0);
}

void searchByValue_Linear(HashTableLinear<string> *ht) {
    string value;
    cout << "Enter a Value: ";
    cin >> value;

    cout << value;

    auto start = chrono::system_clock::now();
    if(ht->retrieve(value)) cout << " is Found" << endl;
    else cout << " is NOT Found" << endl;
    auto end = chrono::system_clock::now();
    chrono::duration<double>duration = end - start;

    cout << "Time Taken to Search: " << duration.count() << "s" << endl;
}

void searchByFile_Linear(HashTableLinear<string> *ht) {
    ifstream file;
    string filename;
    cout << "Enter Filename: ";
    cin >> filename;

    file.open(filename += ".txt");
    while(!file.is_open()) {
        cout << "Invalid Filename. Enter again: ";
        cin >> filename;
        file.open(filename += ".txt");
    }

    string line;
    chrono::duration<double>duration;

    if(countFileLine(filename) <= 100) {
        auto start = chrono::system_clock::now();
        while(getline(file, line)) {
            cout << line;
            if(ht->retrieve(line)) cout << " is Found" << endl;
            else cout << " is NOT Found" << endl;
        }
        auto end = chrono::system_clock::now();
        duration = end - start;
    }else {
        int found = 0;
        int notfound = 0;
        auto start = chrono::system_clock::now();
        while(getline(file, line)) {
            if(ht->retrieve(line)) found++ ;
            else notfound++;
        }
        auto end = chrono::system_clock::now();
        duration = end - start;

        cout << "Found: " << found << endl;
        cout << "Not Found: " << notfound << endl;
        cout << "Total Searches: " << found + notfound << endl;
    }
    cout << "Time Taken to Searchs: " << duration.count() << "s" << endl;
}
////////////////////////////////////////////////////

/////// Chaining Hash Table Menu ///////////////////
void chaining(const string &filename, ifstream &file) {
    int fileLines = countFileLine(filename);
    HashTableChain<string> *hashTable = new HashTableChain<string>(fileLines * 0.9);

    chrono::duration<double>insertTime;

    string line;
    if(file.is_open()) {
        auto start = chrono::system_clock::now();
        while(getline(file, line)) {
            hashTable->insert(line);
        }
        auto end = chrono::system_clock::now();

        insertTime = end - start;
    }else {
        cout << "File cannot open" << endl;
    }
    file.close();

    cout << "File Lines: " << fileLines << endl;
    cout << "Hash Table (Chaining) Size: " << hashTable->size() << endl;
    cout << "Hash Table Usage: " << hashTable->usage() << "%" << endl;
    cout << "Time Taken to Insert: " << insertTime.count() << "s" << endl;

    int choice;
    do {
        cout << "0. Exit" << endl;
        cout << "1. Return to Enter Filename" << endl;
        cout << "2. Return to Select Hash Table" << endl;
        cout << "3. Search the Data using File" << endl;
        cout << "4. Search the Data by insert the value" << endl;
        cout << "5. Print Hash Table (Chaining)" << endl;

        cout << "Enter: ";
        cin >> choice;
        choice = checkInput(choice, 0, 6);

        switch(choice) {
            case 0:
                delete hashTable;
                exit(0);
            case 1:
                delete hashTable;
                enterFileMenu();
            case 2:
                delete hashTable;
                file.open(filename);
                selectTableMenu(filename, file);
            case 3: searchByFile_Chain(hashTable); break;
            case 4: searchByValue_Chain(hashTable); break;
            case 5:
                cout << "----- Hash Table -----" << endl;
                cout << *hashTable << endl;
                break;
            case 6:
                cout << "Collision" << endl;
                hashTable->collision();
        }
    }while(choice != 0);
}

void searchByValue_Chain(HashTableChain<string> *ht) {
    string value;
    cout << "Enter a Value: ";
    cin >> value;

    cout << value;

    auto start = chrono::system_clock::now();
    if(ht->retrieve(value)) cout << " is Found" << endl;
    else cout << " is NOT Found" << endl;
    auto end = chrono::system_clock::now();
    chrono::duration<double>duration = end - start;

    cout << "Time Taken to Search: " << duration.count() << "s" << endl;
}

void searchByFile_Chain(HashTableChain<string> *ht) {
    ifstream file;
    string filename;
    cout << "Enter Filename: ";
    cin >> filename;

    file.open(filename += ".txt");
    while(!file.is_open()) {
        cout << "Invalid Filename. Enter again: ";
        cin >> filename;
        file.open(filename += ".txt");
    }

    string line;
    chrono::duration<double>duration;

    if(countFileLine(filename) <= 100) {
        while(getline(file, line)) {
            cout << line;
            auto start = chrono::system_clock::now();
            if(ht->retrieve(line)) cout << " is Found" << endl;
            else cout << " is NOT Found" << endl;
            auto end = chrono::system_clock::now();
            duration = end - start;
            cout << "Time Taken: " << duration.count() << "s" << endl;
        }
    }else {
        int found = 0;
        int notfound = 0;
        auto start = chrono::system_clock::now();
        while(getline(file, line)) {
            if(ht->retrieve(line)) found++;
            else notfound++;
        }
        auto end = chrono::system_clock::now();
        duration = end - start;

        cout << "Found: " << found << endl;
        cout << "Not Found: " << notfound << endl;
        cout << "Total Searches: " << found + notfound << endl;
    }
    //cout << "Time Taken to Searchs: " << duration.count() << "s" << endl;
}

///////////////////////////////////////////////////////////////////////////

void enterFileMenu() {
    ifstream file;
    string filename;
    cout << "Enter Filename: ";
    cin >> filename;

    file.open(filename += ".txt");
    while(!file.is_open()) {
        cout << "Invalid Filename. Enter again: ";
        cin >> filename;
        file.open(filename += ".txt");
    }
    selectTableMenu(filename, file);
}

void selectTableMenu(const string &filename, ifstream &file) {
    int choice;
    do {
        cout << "0. Exit" << endl;
        cout << "1. Hash Table (Chaining)" << endl;
        cout << "2. Hash Table (Linear Probing)" << endl;
        cout << "Enter: ";
        cin >> choice;
        choice = checkInput(choice, 0, 2);

        switch(choice) {
            case 0: exit(0);
            case 1: chaining(filename, file); break;
            case 2: linear(filename, file); break;
        }
    }while(choice != 0);
}

int main() {

    enterFileMenu();

    return 0;
}
