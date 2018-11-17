package server.api;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
 

/**
 *
 * @author domatskiy
 */
public class Temperature {
    
    public Temperature() {}
    
    @GET
    @Path("/getgoods")
    @Produces("application/xml")
    public Goods getGoods() {
        Goods goods = new Goods();
        goods.setName("Some Goods name");
        goods.setId(1);
        return goods;
    }
 
    @GET
    @Path("/getdoc")
    @Produces("application/xml")
    public Document getDocument() {
        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(new Goods(1, "goods1"));
        goodsList.add(new Goods(2, "goods2"));
        goodsList.add(new Goods(3, "goods3"));
        return new Document(777, "firstDocument", goodsList);
    }
}
