package Classes;

import java.util.List;

public class DocumentWithRFScore extends Document {
		
	//list of count of  term  accor times in each documents
	protected List<Integer> WordFreqList;
	//list of term appear probability of each document
	protected List<Double> WordProb;
	
	/**
	 * @return the wordProb
	 */
	public List<Double> getWordProb() {
		return WordProb;
	}

	/**
	 * @param wordProb the wordProb to set
	 */
	public void setWordProb(List<Double> wordProb) {
		WordProb = wordProb;
	}
	protected double DocLength;

	public DocumentWithRFScore(String docid, String docno, double score, List<Integer> WordFreqList, double DocLength, List<Double> WordProb) {
		super(docid, docno, score);
		this.docid = docid;
		this.docno = docno;
		this.score = score;
		this.WordFreqList = WordFreqList;
		this.DocLength = DocLength;
		this.WordProb = WordProb;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the wordFreqList
	 */
	public List getWordFreqList() {
		return WordFreqList;
	}
	/**
	 * @param wordFreqList the wordFreqList to set
	 */
	public void setWordFreqList(List wordFreqList) {
		WordFreqList = wordFreqList;
	}
	/**
	 * @return the docLength
	 */
	public double getDocLength() {
		return DocLength;
	}
	/**
	 * @param docLength the docLength to set
	 */
	public void setDocLength(double docLength) {
		DocLength = docLength;
	}
	

}