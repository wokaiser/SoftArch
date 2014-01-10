package highscore;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import interfaces.IHighscore;
import interfaces.IHighscoreEntry;

public class HtwgHighscore implements IHighscore {
    private static final String GAME_NAME = "Battleship_Group02";
    private static final String SERVER_ADDRESS = "http://localhost:9000";
    
    public List<IHighscoreEntry> getAll() {
            List<IHighscoreEntry> scores = new LinkedList<IHighscoreEntry>();
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(SERVER_ADDRESS);
            
            try {
                HttpResponse response = client.execute(request);
                JSONTokener tokener = new JSONTokener(response.getEntity().getContent());        
                JSONObject scoreObject = new JSONObject(tokener);
                return extractScores(scoreObject.getJSONArray("result"));
            } catch (ClientProtocolException e) {
                /* exception will be ignored and an empty list will be returned */
            } catch (IOException e) {
                /* exception will be ignored and an empty list will be returned */
            }
            return scores;
    }
    
    private List<IHighscoreEntry> extractScores(JSONArray scoreArray) {
        List<IHighscoreEntry> scores = new LinkedList<IHighscoreEntry>();
        
        for (int i=0; i<scoreArray.length(); i++) {
            JSONObject item = scoreArray.getJSONObject(i);
            if (item.getString("game").equals(GAME_NAME)) {
                scores.add(new HighscoreEntry(item.getString("player"), item.getLong("score")));
            }
        }
        return scores;
    }
    
    public void add(IHighscoreEntry entry) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(SERVER_ADDRESS+"/addHighscore?game="+GAME_NAME+"&player=" + entry.getPlayer() + "&score=" + entry.getScore());
        try {
            client.execute(post);
        } catch (IOException e) {
            /* exception will be ignored */
        }
    }
    
	public String toString(){
		StringBuilder builder = new StringBuilder("");
		
		for (IHighscoreEntry e : getAll()) {
			builder.append(e.getPlayer());
			builder.append(" : ");
			builder.append(e.getScore());
			builder.append("\n");
		}		
		return builder.toString();
	}
}
