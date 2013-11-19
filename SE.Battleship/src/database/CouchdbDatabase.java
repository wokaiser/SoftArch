package database;

import interfaces.IGameContent;
import interfaces.IPlaygroundCell;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import model.playground.PlaygroundCell;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class CouchdbDatabase extends AbstractDatabase {
    private CouchDbConnector db = null;
    
    public CouchdbDatabase() {
        super();
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://127.0.0.1:5984").build();
    
        } catch (MalformedURLException e) {
            getStatus().addError(e.getMessage());
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("battleship_db", true);
    }
    
    @Override
    public IGameContent load(String name) {
        PersistentGameContent hContent = db.find(PersistentGameContent.class, name);
        IGameContent content = map(hContent);
        if(null == content) {
            getStatus().addError(SAVEGAME_NOT_EXIST);
            return null;
        } else {
            getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
            return content;
        }
    }

    @Override
    public List<String> getAll() {
        List<String> lst = new ArrayList<String>();
        ViewQuery query = new ViewQuery().allDocs();
        ViewResult vr = db.queryView(query);

        for (Row r : vr.getRows()) {
            lst.add(r.getId());
        }
        return lst;
    }

    @Override
    public void save(String name, IGameContent content) {
        PersistentGameContent hContent = map(content);
        if (getAll().contains(name)) {
            getStatus().addError(SAVEGAME_NAME_EXIST);
        } else {
            db.create(hContent.getId(), hContent);
        }
    }

    @Override
    public void delete(String name) {
        PersistentGameContent hContent = db.find(PersistentGameContent.class, name);
        if (null != hContent) {
            db.delete(hContent);   
        }
    }

    private IPlaygroundCell[][] loadPlayground(List<PersistentPlaygroundItem> playground, int rows, int columns) {
        IPlaygroundCell[][] matrix = new PlaygroundCell[rows][columns]; 

        for (int index = 0; index < playground.size(); index++) {
            matrix[playground.get(index).getRowcell()][playground.get(index).getColumncell()] = new PlaygroundCell(playground.get(index).getStatus(), playground.get(index).getShipId());
        }
        return matrix;
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground1(PersistentGameContent hcontent) {
        return loadPlayground(hcontent.getPlayground1(), hcontent.getRows(), hcontent.getColumns());
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground2(PersistentGameContent hcontent) {
        return loadPlayground(hcontent.getPlayground2(), hcontent.getRows(), hcontent.getColumns());
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem1(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground1Raw) {
        return new PersistentPlaygroundItem(null, 1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId());
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem2(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground2Raw) {
        return new PersistentPlaygroundItem(null, 2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId());
    }
    
}
