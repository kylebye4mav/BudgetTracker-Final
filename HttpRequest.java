import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for stores a url,
 * reads the data from it, and stores the content
 * found in the url.
 * 
 * @author  Kyle Bye
 */
public class HttpRequest {

    ///
    ///  Properties, Getters, Setters
    ///

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlIn) {
        url = urlIn;
    }

    private ArrayList<String> content;

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> contentIn) {
        content = contentIn;
    }

    ///
    /// Functions
    ///

    /**
     * Takes the url within this instance and
     * reads the content and stores it within
     * the content field of this instance.
     * 
     * @return  true if successful. false if failed.
     */
    public boolean readUrl() {
        boolean isSuccessful;
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    connection.getInputStream()
                )
            );
            parseInputStream(reader);
            isSuccessful = true;
        }
        catch (Exception e) {
            System.err.format(
                "Something went wrong in readUrl()\nException: %s\n", 
                e.getMessage()
            );
            isSuccessful = false;
        }
        return isSuccessful;
    }

    /**
     * Stores the parameter to the url.
     * <br><br>
     * Takes the url within this instance and
     * reads the content and stores it within
     * the content field of this instance.
     * 
     * @param   urlIn
     * @return  true if successful. false if failed.
     * @see HttpRequest#readUrl(String)
     */
    public boolean readUrl(String urlIn) {
        setUrl(urlIn);
        return readUrl();
    }

    /**
     * Takes an inputstreamreader type and reads all the content
     * and stores it in content field of this instance.
     * 
     * @param readerIn  InputStreamReader type
     */
    private void parseInputStream(BufferedReader readerIn) {
        String line;
        while (true) {
            try {
                line = readerIn.readLine();
                if (line == null) break; 
                content.add(line);
            }
            catch (IOException ioe) {
                System.err.format(
                    "Something went wrong in parseInputStream()\nException: %s\n", 
                    ioe.getMessage()
                );
                break;
            }
        }
    }

    ///
    /// Constructors
    ///

    public HttpRequest() {
        this("");
    }

    public HttpRequest(String urlIn) {
        setUrl(urlIn);
        setContent(new ArrayList<String>());
    }



}