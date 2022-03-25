// Soon Jie Kang (1201301760)

import java.util.Comparator;

public class activeComparator implements Comparator<Property>{
        
    @Override
    public int compare(Property o1, Property o2){
        if (null != o1 && null != o2) {
            return o1.getStatus().compareTo(o2.getStatus());
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Active Property";
    }
}
