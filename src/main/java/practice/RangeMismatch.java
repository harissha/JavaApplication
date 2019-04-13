package practice;

public class RangeMismatch {

    private long min;
    private long max;
    private long oracleCount;
    private long esCount;

    public RangeMismatch(long min, long max, long oracleCount, long esCount) {
        this.min = min;
        this.max = max;
        this.oracleCount = oracleCount;
        this.esCount = esCount;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getOracleCount() {
        return oracleCount;
    }

    public void setOracleCount(long oracleCount) {
        this.oracleCount = oracleCount;
    }

    public long getEsCount() {
        return esCount;
    }

    public void setEsCount(long esCount) {
        this.esCount = esCount;
    }

    @Override
    public String toString() {
        return "RangeMismatch[" +
                "min=" + min +
                ", max=" + max +
                ", oracleCount=" + oracleCount +
                ", esCount=" + esCount +
                ']';
    }


}
