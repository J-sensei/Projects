#ifndef LINKEDLIST_CPP


    int countCollision() {
        Node<T> *ptr = start;
        int count = 0;

        while(ptr != NULL) {
            count++;
            ptr = ptr->next;
        }
        return --count;
    }
