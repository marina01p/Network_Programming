import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User{
    private String msg;
    private Link link;


    public String getMsg(){
        return this.msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public Link getLink(){
        return this.link;
    }
    public void setLink(Link link){
        this.link = link;
    }

}