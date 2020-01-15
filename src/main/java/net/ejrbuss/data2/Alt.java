package net.ejrbuss.data2;

public abstract class Alt<A, B> {

    public static <A, B> Alt<A, B> left(A left) {
        return new LeftAlt<>(left);
    }

    public static <A, B> Alt<A, B> right(B right) {
        return new RightAlt<>(right);
    }

    private static class LeftAlt<A, B> extends Alt<A, B> {

        private final A left;

        public LeftAlt(A left) {
            this.left = left;
        }

    }

    private static class RightAlt<A, B> extends Alt<A, B> {

        private final B right;

        public RightAlt(B right) {
            this.right = right;
        }

    }

}
