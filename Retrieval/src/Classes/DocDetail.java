package Classes;

import java.util.HashMap;

public class DocDetail {

	protected HashMap<Integer, Integer> tdFreq;
	protected int docSize;
	public HashMap<Integer, Integer> getTdFreq() {
		return tdFreq;
	}
	public void setTdFreq(HashMap<Integer, Integer> tdFreq) {
		this.tdFreq = tdFreq;
	}
	public int getDocSize() {
		return docSize;
	}
	public void setDocSize(int docSize) {
		this.docSize = docSize;
	}
	
}
