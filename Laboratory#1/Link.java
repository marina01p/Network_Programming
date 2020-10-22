import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Link{
    private String route_1;
    private String route_2;
    private String route_3;
    private String route_4;

    public String getRoute_1(){
        return this.route_1;
    }
    public void setRoute_1(String route_1){
        this.route_1 = route_1;
    }

    public String getRoute_2(){
        return this.route_2;
    }
    public void setRoute_2(String route_2){
        this.route_2 = route_2;
    }

    public String getRoute_3(){
        return this.route_3;
    }
    public void setRoute_3(String route_3){
        this.route_3 = route_3;
    }

    public String getRoute_4(){
        return this.route_4;
    }
    public void setRoute_4(String route_4){
        this.route_4 = route_4;
    }
}