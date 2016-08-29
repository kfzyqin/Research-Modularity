import java.util.List;
import java.util.Map;

/**
 * Created by david on 29/08/16.
 */
public interface Statistics<T extends Chromosome> {
    void record(final Map<String, Object> data);
    void request(final List<String> keys, final Map<String, Object> data);
    void save(final String filename);
    void load(final String filename);
}
