// Soon Jie Kang (1201301760)

import java.util.Comparator;

public class addressComparator implements Comparator<Property>{ 
        
    @Override
    public int compare(Property o1, Property o2) {
        String[] address1 = o1.getAddress().split("\\s*,\\s*");
        String[] address2 = o2.getAddress().split("\\s*,\\s*");
        if (address1[2].compareTo(address2[2]) == 0) {
            if (address1[1].compareTo(address2[1]) == 0) {
                if (address1[0].compareTo(address2[0]) > 0) {
                    return 1;
                } else {
                    return -1;
                } // end house number
            } else {
                return address1[1].compareTo(address2[1]);
            } //end street
        }else 
            return address1[2].compareTo(address2[2]);
            
        //end taman
    }

    @Override
    public String toString() {
        return "According to Project";
    }
}