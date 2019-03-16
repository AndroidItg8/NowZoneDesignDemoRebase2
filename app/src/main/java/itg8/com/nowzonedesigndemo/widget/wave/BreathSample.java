package itg8.com.nowzonedesigndemo.widget.wave;

public class BreathSample {
    private long timestamp;
    private double value;

    public BreathSample(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public BreathSample interpolate(BreathSample breathSample, long timestamp) {
        long j2 = this.timestamp;
        long j3 = breathSample.timestamp;
        double d = this.value;
        return new BreathSample(timestamp, (((breathSample.value - d) * ((double) (timestamp - j2))) / ((double) (j3 - j2))) + d);
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getValue() {
        return this.value;
    }
}
