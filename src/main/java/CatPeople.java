
import com.hedera.hashgraph.sdk.HederaStatusException;
import com.organization.catpeople.CatPeopleState;
import com.txmq.aviator.core.Aviator;
import com.txmq.aviator.core.AviatorTestConsensus;
import com.txmq.aviator.core.hcs.AviatorHCSConsensus;

public class CatPeople {

	/**
	 * All we need to do here is configure our copy 
	 * of the shared state and the core framework.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//Use this line to run with test consensus
			//AviatorTestConsensus consensus = new AviatorTestConsensus();
			
			//Use this line to run with HCS consensus
			AviatorHCSConsensus consensus = new AviatorHCSConsensus();
			
			consensus.initState(CatPeopleState.class);
			Aviator.init(consensus);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
