package net.ejrbuss.data2;

public class Result<A, B extends Throwable> extends Alt<A, B> {

    public static <A> Result<A, ?> ok(A a) {
        return null;
    }

    public static <A extends Throwable> Result<?, A> error(A a) {
        return null;
    }

}
