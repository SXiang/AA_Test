package ax.keyword.restapi;

import java.io.IOException;

import javax.print.DocFlavor.URL;

import com.gargoylesoftware.htmlunit.WebConnection;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;

public class ModifyHostConnection extends WebConnectionWrapper {
    public ModifyHostConnection(WebConnection webConnection) {
        super(webConnection);
    }
    public WebResponse getResponse(WebRequestSettings settings) throws
IOException {
        java.net.URL url = settings.getUrl();
        // TODO: change host of url base on some lookup table
        settings.setUrl(url);
        settings.setCharset("UTF-8");
        return super.getResponse(settings);
    }


} 