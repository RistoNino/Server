package org.uid.ristonino.server.model.services;

import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Table;

import java.util.ArrayList;

public class TableService {
    private ArrayList<Table> table=new ArrayList<>();

    private static TableService instance;

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
        DatabaseHandler db= DatabaseHandler.getInstance();
        db.loadTable();
    }

    public void update(){
        table=new ArrayList<>();
        this.load();
    }

    public int getNumberOfTables(){
        update();
        return table.getLast().getId();
    }

    @Override
    public String toString() {
        return "TableService{" +
                "table=" + table +
                '}';
    }

    public int getCoperti(int id){
        return table.get(id-1).getOccupied();
    }

    public int getTotalCoperti(){
        int tot=0;

        for(int i=1; i<=this.getNumberOfTables(); i++){

            tot+=getCoperti(i);
        }

        return tot;
    }


    public int getTotalMaxCoperti(){
        int tot=0;
        System.out.println("totqua"+tot);
        for(int i=0; i<getNumberOfTables(); i++){
            System.out.println("tot"+i+" table.get(i).getMaxOccupied()"+table.get(i).getMaxOccupied());
            tot+=table.get(i).getMaxOccupied();
        }
        System.out.println("tot"+tot);
        return tot;
    }
}
