// Soon Jie Kang (1201301760)

import java.util.Comparator;

public class typeComparator implements Comparator<Property>{
        
    @Override
    public int compare(Property o1, Property o2){
        if (null != o1 && null != o2) {
            if (null != o1.getType() && null != o2.getType()) {
                    return o1.getType().compareTo(o2.getType());
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "According to Type";
    }
}
