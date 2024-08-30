/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.port;

import com.advantech.webservice.Factory;
import com.advantech.webservice.root.StandardWorkReasonQueryRoot;
import com.advantech.webservice.unmarshallclass.StandardWorkReason;
import com.advantech.webservice.unmarshallclass.StandardWorkReasons;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.advantech.model.db2.IWorktimeForWebService;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class StandardWorkReasonQueryPort extends BasicQueryPort {

    private static final Logger logger = LoggerFactory.getLogger(StandardWorkReasonQueryPort.class);

    @Override
    protected void initJaxb() {
        try {
            super.initJaxb(StandardWorkReasonQueryRoot.class, StandardWorkReasons.class);
        } catch (JAXBException e) {
            logger.error(e.toString());
        }
    }

    public List<StandardWorkReason> query() throws Exception {
        return this.query(new StandardWorkReasonQueryRoot());
    }

    // OK
    public List<StandardWorkReason> queryM(Factory f) throws Exception {
        return this.queryM(new StandardWorkReasonQueryRoot(), f);
    }

    @Override
    public Map<String, String> transformData(IWorktimeForWebService w) throws Exception {
        throw new UnsupportedOperationException();
    }

}
