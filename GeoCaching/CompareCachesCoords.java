import java.util.Comparator;
import java.io.Serializable;

public class CompareCachesCoords implements Comparator<GPS>, Serializable
{
    public int compare(GPS g1, GPS g2){
        if(g1.getLat() > g2.getLat())
            return 1;
        if(g1.getLat() < g2.getLat())
            return -1;
        if(g1.getLongi() > g2.getLongi())
            return 1;
        if(g1.getLongi() < g2.getLongi())
            return -1;
        return 0;
    }
}
