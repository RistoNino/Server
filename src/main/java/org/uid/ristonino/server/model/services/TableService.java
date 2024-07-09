package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Table;

import java.util.ArrayList;


//TODO:Usare thread negli init

public class TableService {
    private ArrayList<Table> table=new ArrayList<>();
    private DatabaseHandler db = DatabaseHandler.getInstance();

    public ArrayList<Table> getTable(){
        return table;
    }

    private static TableService instance=null;

    public static TableService getInstance(){
        if(instance==null){
            instance= new TableService();
        }
        return instance;
    }

    public void addTable(Table tab){
        table.add(tab);
    }

    public void load(){
        db.loadTable();
    }

    public void update(){
        table=new ArrayList<>();
        this.load();
    }

    public int getNumberOfTables(){
        update();
        return table.size();
    }

    public boolean addNewTable(Table tab) {
        table.add(tab);
        return db.addTable(tab);
    }

    @Override
    public String toString() {
        return "TableService{" +
                "table=" + table +
                '}';
    }

    public int getCoperti(int id){
        int oc=0;
        for(int i=0;i<table.size();i++){
            if(table.get(i).getId()==id){
                oc=table.get(i).getOccupied();
            }
        }
        return oc;
    }

    public int getTotalCoperti(){
        int tot=0;
        for(int i=0; i<table.size(); i++){
            //System.out.println("getCoperti(i)"+table.get(i).getOccupied());
            tot+=table.get(i).getOccupied();
        }
        //System.out.println("******Table:"+table);
        return tot;
    }


    public int getTotalMaxCoperti(){
        int tot=0;
        //System.out.println("totqua"+tot);
        for(int i=0; i<getNumberOfTables(); i++){
            //System.out.println("tot"+i+" table.get(i).getMaxOccupied()"+table.get(i).getMaxOccupied());
            tot+=table.get(i).getMaxOccupied();
        }
        //System.out.println("tot"+tot);
        return tot;
    }
}
