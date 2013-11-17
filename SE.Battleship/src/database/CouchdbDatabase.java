package database;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.playground.PlaygroundCell;
import modules.AiModule;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.GameContent;
import database.couchdb.CouchdbGameContent;
import database.couchdb.CouchdbPlaygroundItem;

public class CouchdbDatabase extends AbstractDatabase {
    private CouchDbConnector db = null;
    
    public CouchdbDatabase() {
        super();
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://127.0.0.1:5984").build();
    
        } catch (MalformedURLException e) {
            status.addError(e.getMessage());
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("battleship_db", true);
    }
    
    @Override
    public GameContent load(String name) {
        CouchdbGameContent hContent = db.find(CouchdbGameContent.class, name);
        GameContent content = map(hContent);
        if(null == content) {
            status.addError(SAVEGAME_NOT_EXIST);
            return null;
        } else {
            status.addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
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
    public void save(String name, GameContent content) {
        CouchdbGameContent hContent = map(content);
        
        if (getAll().contains(name)) {
            db.update(hContent);
        } else {
            db.create(hContent.getId(), hContent);
        }
    }

    @Override
    public void delete(String name) {
        CouchdbGameContent hContent = db.find(CouchdbGameContent.class, name);
        if (null != hContent) {
            db.delete(hContent);   
        }
    }

    private CouchdbGameContent map(GameContent content) {
        /* copy content information to Couchdb required format */
        CouchdbGameContent hContent = new CouchdbGameContent();
        hContent.setId(content.getName());
        hContent.setGameType(content.getGameType());
        hContent.setRows(content.getRows());    
        hContent.setColumns(content.getColumns());    
        hContent.setPlayer1(content.getPlayer1());
        hContent.setPlayer2(content.getPlayer2());
        
        /* copy playground information to Hibernate required format */
        List<CouchdbPlaygroundItem> playground1 = new LinkedList<CouchdbPlaygroundItem>();
        List<CouchdbPlaygroundItem> playground2 = new LinkedList<CouchdbPlaygroundItem>();
        
        PlaygroundCell[][] playground1Raw = content.getOwnPlayground(content.getPlayer1()).ownView();
        PlaygroundCell[][] playground2Raw = content.getOwnPlayground(content.getPlayer2()).ownView();
        for (int row = 0; row < content.getRows(); row++) {
            for (int column = 0; column < content.getColumns(); column++){
                playground1.add(new CouchdbPlaygroundItem(1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId()));
                playground2.add(new CouchdbPlaygroundItem(2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId()));
            }
        }
        hContent.setPlayground1(playground1);
        hContent.setPlayground2(playground2);
        
        return hContent;
    }
    
    private GameContent map(CouchdbGameContent hcontent) {
        if (hcontent == null) {
            return null;
        }
        PlaygroundCell[][] matrixPlayground1 = loadPlayground(hcontent.getPlayground1(), hcontent.getRows(), hcontent.getColumns());
        PlaygroundCell[][] matrixPlayground2 = loadPlayground(hcontent.getPlayground2(), hcontent.getRows(), hcontent.getColumns());
        Injector inject = Guice.createInjector(new AiModule().setSettings(hcontent.getPlayer1()), new AiModule().setSettings(hcontent.getPlayer2()));
        GameContent content = inject.getInstance(GameContent.class);
        content.initContent(hcontent.getRows(), hcontent.getColumns(), hcontent.getPlayer1(), hcontent.getPlayer2(), hcontent.getGameType(), matrixPlayground1, matrixPlayground2);
        content.startGame();
        return content;
    }

    private PlaygroundCell[][] loadPlayground(List<CouchdbPlaygroundItem> playground, int rows, int columns) {
        PlaygroundCell[][] matrix = new PlaygroundCell[rows][columns]; 

        for (int index = 0; index < playground.size(); index++) {
            matrix[playground.get(index).getRowcell()][playground.get(index).getColumncell()] = new PlaygroundCell(playground.get(index).getStatus(), playground.get(index).getShipId());
        }
        return matrix;
    }
    
}
