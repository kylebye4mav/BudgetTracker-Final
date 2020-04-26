import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

/**
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

    public boolean readUrl(String urlIn) {
        setUrl(urlIn);
        return readUrl();
    }

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