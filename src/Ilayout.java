
import java.util.*;

interface Ilayout {
	List<Ilayout> children();
	/**
	@return true if the receiver equals the argument l; return false otherwise.
	 */
	boolean isGoal(Ilayout l);
	/**
	@return the cost for moving from the input config to the receiver.
	 */
	double getG();

	double getH(Ilayout l);
}
