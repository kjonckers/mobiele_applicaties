package com.example.kjonckers.hypt.db;

/**
 * Created by kjonckers on 16/08/15.
 */
public interface Database<E> {
    E get(String id);
}
