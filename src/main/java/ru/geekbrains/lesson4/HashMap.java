package ru.geekbrains.lesson4;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Структура хэш-таблицы
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMap <K, V> implements Iterable<HashMap<K,V>.Bucket<K,V>.Node> {

    //region Публичные методы

    public V put(K key, V value){
        if (buckets.length * LOAD_FACTOR <= size)
            recalculate();

        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = (V)bucket.add(entity);
        if (buf == null){
            size++;
        }
        return buf;
    }

    @Override
    public String toString() {
        return "HashMap{" +
                "buckets=" + Arrays.toString(buckets) +
                ", size=" + size +
                '}';
    }

    //endregion

    //region Методы

    private void recalculate(){
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++){
            Bucket<K, V> bucket = old[i];
            if (bucket != null){
                Bucket<K, V>.Node node = bucket.head;
                while (node != null){
                    put((K)node.value.key, (V)node.value.value);
                    node = node.next;
                }
            }
        }
    }

    private int calculateBucketIndex(K key){
       return Math.abs(key.hashCode()) % buckets.length;
    }

    //endregion

    //region Конструкторы

    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }

    @Override
    public HashMapIterator iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements java.util.Iterator<Bucket<K,V>.Node> {
        private int bucketIndex;
        Bucket<K,V>.Node currNode;
        Bucket<K,V> buc;
        public HashMapIterator(){
            this.bucketIndex = 0;
            this.buc = buckets[bucketIndex];
            this.currNode = null;
        }

        @Override
        public boolean hasNext() {
            if (this.buc == null) {
                bucketIndex++;
            }
            this.buc =

            if (currNode.next != null){
                    return true;
                }
                else{
                    bucketIndex++;
                    return false;
                }
            }
        }

        @Override
        public Bucket<K,V>.Node next() {
            Bucket<K,V>.Node value = currNode;
            currNode = currNode.next;
            return value;
        }

    }


    //endregion

    //region Вспомогательные структуры
    /**
     * Элемент хэш-таблицы
     */
    class Entity{

        /**
         * Ключ
         */
        K key;

        /**
         * Значение
         */
        V value;

        @Override
        public String toString() {
            return "Entity{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

    }
    /**
     * Элемент массива (связный список) из которого состоит хэш-таблица
     */
    class Bucket<K,V>{


        /**
         * Указатель на первый элемент связного списка
         */
        private Node head;


        /**
         * Узел связного списка
         */

        class Node{


            /**
             * Ссылка на следующий узел (если имеется)
             */
            Node next;

            /**
             * Значение узла
             */
            Entity value;
            @Override
            public String toString() {
                return "key=" + head.value.key +
                        ", value=" +  head.value.value;
            }



        }
        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else {
                    currentNode.next = node;
                    return null;
                }
            }
        }


        @Override
        public String toString() {
            return "{" +
                     head +
                    '}';
        }

    }
    //endregion


    //region Поля

    /**
     * Массив бакетов (связных списков)
     */
    private Bucket<K,V>[] buckets;
    private int size;

    //endregion

    //region Константы

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;

    //endregion
}

public void main() {
}

