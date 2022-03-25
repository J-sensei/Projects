// Soon Jie Kang (1201301760)

import java.util.Comparator;

public class rentalComparator implements Comparator<Property>{ 
        
    @Override
    public int compare(Property o1, Property o2){
        if (null != o1 && null != o2) {
            return Float.compare(o1.getPrice(), o2.getPrice());
        }
        
        return 0;
    }

    @Override
    public String toString() {
        return "According to Project";
    }
}
