package net.ejrbuss.data;

import net.ejrbuss.func.Func;
import net.ejrbuss.func.Thunk;

public class Ref<A> {

    public static <A> Ref<A> empty() {
        return new Ref<A>(null);
    }

    public static <A> Ref<A> of(A a) {
        return new Ref<A>(a);
    }

    public static <A> Ref<A> from(Maybe<A> maybe) {
        return Ref.of(maybe.get(null));
    }

    public static <A> Ref<A> from(Seq<A> seq) {
        if (seq.isEmpty()) {
            return empty();
        } else {
            return Ref.of(seq.first());
        }
    }

    private A element;

    private Ref(A element) {
        this.element = element;
    }

    boolean isEmpty() {
        return element == null;
    }

    public A get() {
        return element;
    }

    public A set(A element) {
        this.element = element;
        return element;
    }

    public A getOrSet(A element) {
        if (isEmpty()) {
            return set(element);
        } else {
            return get();
        }
    }

    public A getOrSet(Thunk<A> thunk) {
        if (isEmpty()) {
            return set(thunk.apply());
        } else {
            return get();
        }
    }

    public A swap(A newElement) {
        A oldElement = get();
        set(newElement);
        return oldElement;
    }

    public Ref<A> map(Func<A, A> transformer) {
        this.element = transformer.apply(element);
        return this;
    }

    public Ref<A> flatMap(Func<A, Ref<A>> transformer) {
        this.element = transformer.apply(element).get();
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Ref) {
            Ref<?> otherRef = (Ref<?>) other;
            if (isEmpty() && otherRef.isEmpty()) {
                return true;
            }
            return isEmpty() == otherRef.isEmpty() && otherRef.element.equals(element);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isEmpty()) {
            return 0;
        }
        return element.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "(" + element + ")";
    }

}
