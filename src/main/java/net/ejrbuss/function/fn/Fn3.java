package net.ejrbuss.function.fn;

@FunctionalInterface
public interface Fn3<A, B, C, D> extends Fn<A, Fn<B, Fn<C, D>>> {

    D $(A a, B b, C c);

    @Override
    default Fn<B, Fn<C, D>> $(A a) {
        return b -> c -> $(a, b, c);
    }
}
