package service.camel.processor;

import common.JsonHelper;
import domain.s3.S3Manager;
import domain.s3.S3ManagerBean;
import org.apache.camel.Exchange;

/***
 *Created by Lin Cheng
 */
public class UploadProcessor extends GetProcessor {
    @Override
    protected void setHeaders(Exchange exchange) {
    }

    @Override
    String handle() throws Exception {
        S3Manager mgr = new S3ManagerBean();
        String ret = mgr.upload(paraMap.get("path"));
        return JsonHelper.toJson(ret);
    }
}
