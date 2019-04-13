package practice;

public class ElasticSearchResult {
	
	private int responseCode;
	private String responseAsString;
	private boolean elasticQuerySuccess;
	private int numberOfRecordsUpdated;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseAsString() {
		return responseAsString;
	}
	public void setResponseAsString(String responseAsString) {
		this.responseAsString = responseAsString;
	}
	public boolean isElasticQuerySuccess() {
		return elasticQuerySuccess;
	}
	public void setElasticQuerySuccess(boolean elasticQuerySuccess) {
		this.elasticQuerySuccess = elasticQuerySuccess;
	}
	public int getNumberOfRecordsUpdated() {
		return numberOfRecordsUpdated;
	}
	public void setNumberOfRecordsUpdated(int numberOfRecordsUpdated) {
		this.numberOfRecordsUpdated = numberOfRecordsUpdated;
	}
	

}
