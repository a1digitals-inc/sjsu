package camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import twitter4j.DirectMessage;
import twitter4j.Status;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lin Cheng on 2017/9/25.
 *
 This service will pull Twitter contents and put them to local file system.

 Supported twitter APIs and their destination folders:

 timeline -> /lghr/camel_d/timeline
 search -> /lghr/camel_d/search
 directmessage -> /lghr/camel_d/directmessage

 APIs exposed:

 TwitterService.start -> start service and keep pulling default contents
 TwitterService.stop -> stop service
 TwitterService.setKeyword -> change search keyword
 TwitterService.setTimeline -> change to different people's timeline
 */
public class TwitterService extends CamelService {

    private String keyword = "camel";
    private String timeline = "BarackObama";

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    static private String searchRouteId = "searchRoute";
    static private String msgRouteId = "msgRoute";
    static private String timelineRouteId = "timelineRoute";

    private String rootDir = "/lghr/camel_d/";
    private String searchDir = "search";
    private String msgDir = "directmessage";
    private String timelineDir = "timeline";

    public TwitterService() {
    }

    public TwitterService(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) throws Exception {
        this.keyword = keyword;

        //enforce
        removeRoute(searchRouteId);
        addSearchRoute();
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) throws Exception {
        this.timeline = timeline;

        //enforce
        removeRoute(timelineRouteId);
        addTimelineRoute();
    }

    @Override
    public void addRoutes() throws Exception {
        addTimelineRoute();
        addSearchRoute();
        addMsgRoute();
    }

    private void addTimelineRoute() throws Exception{
        addTwitterRoute("timeline/user?type=direct&user=" + timeline, timelineDir, timelineRouteId);
    }

    private void addSearchRoute() throws Exception{
        addTwitterRoute("search?type=polling&keywords=" + keyword, searchDir, searchRouteId);
    }

    private void addMsgRoute() throws Exception{
        addTwitterRoute("directmessage?type=polling&delay=10000", msgDir, msgRouteId);
    }

    private void addTwitterRoute(String uri, String path, String id) throws Exception{
        addRoute(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("twitter://" + uri +
                        "&consumerKey=" + consumerKey +
                        "&consumerSecret=" + consumerSecret +
                        "&accessToken=" + accessToken +
                        "&accessTokenSecret=" + accessTokenSecret)
                        .routeId(id)
                        .process(new TwitterProcessor())
                        .process(new PrintProcessor())
                        .to("file:" + rootDir + path);
            }
        });
    }

    static class PrintProcessor implements Processor{
        @Override
        public void process(Exchange exchange) throws Exception {
            InputStream inputStream = exchange.getIn().getBody(InputStream.class);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line=bufferedReader.readLine())!=null){
                System.out.println(line);
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class TwitterProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Object obj = exchange.getIn().getBody();
            if(obj == null){
                return;
            }

            String name;
            String content;
            if(obj instanceof Status) {
                Status status = (Status)obj;
                name = status.getUser().getScreenName() + "-" + status.getCreatedAt().getTime() + ".txt";
                content = status.getText();
            } else if(obj instanceof List) {
                List<Status> ss = (List<Status>)obj;
                name = "List-"+ System.currentTimeMillis() + ".txt";
                content = ss.stream().map(o->o.getText()).collect(Collectors.joining("###"));
            } else if(obj instanceof DirectMessage){
                DirectMessage msg = (DirectMessage)obj;
                name = msg.getSender().getScreenName() + "-" + msg.getCreatedAt().getTime() + ".txt";
                content = msg.getText();
            } else {
                name = "Unknown-"+ System.currentTimeMillis() + ".txt";
                content = obj + "";
            }

            exchange.getIn().setHeader(Exchange.FILE_NAME, name);
            exchange.getIn().setBody(content);
        }
    }
}