package com.ran.dissertation.algebraic.common;

import java.util.Objects;

public class Pair<T1, T2> {

    private final T1 left;
    private final T2 right;

    public Pair(T1 left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public T1 getLeft() {
        return left;
    }

    public T2 getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.left);
        hash = 47 * hash + Objects.hashCode(this.right);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(this.left, other.left) && Objects.equals(this.right, other.right);
    }

    @Override
    public String toString() {
        return "Pair{" + "left=" + left + ", right=" + right + '}';
    }
    
}