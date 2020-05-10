package sample.service;

import sample.models.Bmr;

public class BmrServiceImpl implements BmrService {


    @Override
    public Bmr addBmr(Integer height, Integer weight, Integer age) {
        Bmr bmr = new Bmr();
        bmr.setHeight(height);
        bmr.setWeight(weight);
        bmr.setAge(age);
        return bmr;
    }
}
