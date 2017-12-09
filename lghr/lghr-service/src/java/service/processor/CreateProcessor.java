package service.processor;

import common.JsonHelper;
import dao.BaseDao;
import dao.CustomerImpl;
import dao.OrderImpl;
import dao.ProductImpl;
import model.Customer;
import model.Order;
import model.Product;

/***
 *Created by Lin Cheng
 */
public class CreateProcessor extends PostProcessor {
    @Override
    String handle()throws Exception  {

        BaseDao dao;

        switch (paramMap.get("type")){
            case "product":
                dao = new ProductImpl();
                return JsonHelper.toJson(dao.create(JsonHelper.fromJson(body, Product.class)));
            case "order":
                dao = new OrderImpl();
                return JsonHelper.toJson(dao.create(JsonHelper.fromJson(body, Order.class)));
            case "customer":
                dao = new CustomerImpl();
                return JsonHelper.toJson(dao.create(JsonHelper.fromJson(body, Customer.class)));
            default:
                return JsonHelper.toJson("Not supported.");
        }
    }
}
