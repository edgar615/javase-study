package lambda;

/**
 * Created by Edgar on 2016/1/19.
 *
 * @author Edgar  Date 2016/1/19
 */
public interface PersonFactory {
  Person create(String firstName, String lastName);
}
