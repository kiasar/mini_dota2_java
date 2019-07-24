package eventQueue;

import map.Cell;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by ali on 12/24/2015.
 */
public abstract class EventQueue implements Comparable{
    int remainedTime;
    public Cell cell;

    public EventQueue(Cell cell, int remainedTime) {
        this.cell = cell;
        this.remainedTime = remainedTime;
    }

    private void passTime(int milis){
        remainedTime-=milis;
    }

    public void pass50milis(){
        passTime(50);
    }

    public void pop(ArrayList<EventQueue> eventQueue){
        if (itsTime()){
        push(eventQueue,command(eventQueue));
        eventQueue.remove(this);
        }
    }

   public static void push(ArrayList<EventQueue> eventQueue,EventQueue q){
        if (q!=null){
        eventQueue.add(q);
        eventQueue.sort(new Comparator<EventQueue>() {

            @Override
            public int compare(EventQueue o1, EventQueue o2) {
                return ((Integer) o1.remainedTime).compareTo(o2.remainedTime);
            }
        });}
    }

    @Override
    public int compareTo(Object o) {
        return ((Integer)remainedTime).compareTo(((EventQueue)o).remainedTime);
    }

    public boolean itsTime(){
        if(remainedTime<=0)return true;
        return false;
    }

    public abstract EventQueue command(ArrayList<EventQueue> eventQueue);

}