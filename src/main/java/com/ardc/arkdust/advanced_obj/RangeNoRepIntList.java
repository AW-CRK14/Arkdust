package com.ardc.arkdust.advanced_obj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class RangeNoRepIntList extends AbstractList<Integer> {
    private final Logger log = LogManager.getLogger();
    private final int min;
    private final int max;
    private final int maxLength;

    public RangeNoRepIntList(int min ,int max){
        if(max <= min){
            log.warn("[ArdObj-RangeNoRepIntList]You shouldn't let the min value {} greater than or equal the max value {}.the max value will be change to {}.",min,max,min+1);
            max = min+1;
        }
        this.min = min;
        this.max = max;
        this.maxLength = max-min+1;
    }
    private List<Integer> l = new ArrayList<>();

    @Override
    public Integer get(int index) {
        return l.get(index);
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        l.forEach(action);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return l.spliterator();
    }

    @Override
    public Stream<Integer> stream() {
        return l.stream();
    }

    @Override
    public Stream<Integer> parallelStream() {
        return l.parallelStream();
    }

    @Override
    public int size() {
        return l.size();
    }

    @Override
    public boolean removeIf(Predicate<? super Integer> filter) {
        return l.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<Integer> operator) {
        l.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Integer> c) {
        l.sort(c);
    }

    @Override
    public boolean contains(Object o) {
        return l.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(l).containsAll(c);
    }

    @Override
    public boolean add(Integer integer) {
        if(l.size() >= maxLength || l.contains(integer) || integer < min || integer > max )
            return false;
        l.add(integer);
        return true;
    }

    public RangeNoRepIntList fill(){
        l.clear();
        for(int i = min; i <= max; i++){
            l.add(i);
        }
        return this;
    }

    @Override
    public boolean remove(Object o) {
        return l.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        if (l.size() >= maxLength) return false;
        boolean flag = false;
        int length = l.size();
        for (Integer i : c) {
            if(!l.contains(i) && i<= max && i>= min){
                l.add(i);
                length++;
                flag = true;
            }
            if(length >= maxLength) break;
        }
        return flag;
    }

    public RangeNoRepIntList addAllAndReturn(Collection<? extends Integer> c) {
        addAll(c);
        return this;
    }

    public RangeNoRepIntList addAndReturn(Integer integer) {
        add(integer);
        return this;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return l.removeAll(c);
    }
}
