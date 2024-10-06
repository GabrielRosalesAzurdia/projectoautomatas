package core;

public interface Matcher {
    public abstract boolean isFinalState(String state);
    public abstract boolean matchString(String string) throws Exception;
    public abstract String toString();
}
