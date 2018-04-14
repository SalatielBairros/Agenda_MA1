package com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Models;

import com.fadergs.salatiel.agendatelefonicamarcoavaliativo1.Interfaces.IEntityModel;

public abstract class BaseModel implements IEntityModel {
    protected int _id;
    protected BaseModel(int id){
        this._id = id;
    }
}
