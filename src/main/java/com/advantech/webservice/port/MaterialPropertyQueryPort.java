/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.port;

import com.advantech.webservice.Factory;
import com.advantech.webservice.root.MaterialPropertyQueryRoot;
import com.advantech.webservice.unmarshallclass.MaterialProperty;
import com.advantech.webservice.unmarshallclass.MaterialPropertys;
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
public class MaterialPropertyQueryPort extends BasicQueryPort {

    private static final Logger logger = LoggerFactory.getLogger(MaterialPropertyQueryPort.class);

    @Override
    protected void initJaxb() {
        try {
            super.initJaxb(MaterialPropertyQueryRoot.class, MaterialPropertys.class);
        } catch (JAXBException e) {
            logger.error(e.toString());
        }
    }

    public List<MaterialProperty> query(String matPropNo) throws Exception {
        return this.query(new MaterialPropertyQueryRoot(matPropNo));
    }

    // OK
    public List<MaterialProperty> queryM(String matPropNo, Factory f) throws Exception {
        return this.queryM(new MaterialPropertyQueryRoot(matPropNo), f);
    }

    @Override
    public Map<String, String> transformData(IWorktimeForWebService w) throws Exception {
        throw new UnsupportedOperationException();
    }
}
