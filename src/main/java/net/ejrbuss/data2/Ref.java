package net.ejrbuss.data2;

import net.ejrbuss.function.Fn;
import net.ejrbuss.function.Thunk;

public class Ref<A> {

    public static <A> Ref<A> of(A value) {
        return new Ref(value);
    }

    private A value;

    private Ref(A value) {
        this.value = value;
    }

    public A get() {
        return value;
    }

    public A set(A newValue) {
        return value = newValue;
    }

    public synchronized A swap(A newValue) {
        A oldValue = value;
        value = newValue;
        return oldValue;
    }

    public synchronized boolean compareAndSet(A expected, A newValue) {
        if (value != expected) {
            return false;
        }
        set(newValue);
        return true;
    }

    public synchronized boolean compareAndSet(A expected, Thunk<A> thunk) {
        if (value != expected) {
            return false;
        }
        set(thunk.apply());
        return true;
    }

    public <B> B map(Fn<A, B> transform) {
        return transform.apply(value);
    }

    public Ref<A> mapAndSet(Fn<A, A> transform) {
        set(transform.apply(value));
        return this;
    }

    public Ref<A> flatMapAndSet(Fn<A, Ref<A>> transform) {
        set(transform.apply(value).value);
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + get() + ")";
    }

}
