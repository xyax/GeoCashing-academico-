import java.util.Comparator;
import java.io.Serializable;
import java.util.GregorianCalendar;

public class CompareData implements Comparator<GregorianCalendar>, Serializable
{
    public int compare(GregorianCalendar d1, GregorianCalendar d2){
        if(d1.before(d2))
            return -1;
        if(d2.before(d1))
            return 1;
        return 0;
    }
}
