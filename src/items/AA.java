//package items;
//
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.value.ObservableValue;
//
//import java.io.Serializable;
//
///**
// * Created by ali on 2/4/2016.
// */
//public class AA implements Serializable ,ObservableValue<Number>{
//    SimpleDoubleProperty simpleDoubleProperty=new SimpleDoubleProperty(1);
//    public void bind(ObservableValue<? extends Number> a){
//        simpleDoubleProperty.bind(a);
//    }
//    public void set(double v){
//        simpleDoubleProperty.set(v);
//    }
//    public void setValu(Number v){
//        simpleDoubleProperty.setValue(v);
//    }
//    public double get(){
//        return simpleDoubleProperty.get();
//    }
//}
